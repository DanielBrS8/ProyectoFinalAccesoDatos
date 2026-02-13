package com.daniel.portscanner.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class NmapService {

    public String scanXml(String target) {
        try {
            // Ejecutamos nmap en WSL mediante ProcessBuilder
            List<String> cmd = List.of(
                    "wsl", "sudo", "nmap",
                    "-sV",
                    "--top-ports", "100",
                    "-oX", "-",
                    target);
                
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true); // mezcla stdout+stderr para ver errores juntos

            Process p = pb.start();
            String output = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            int exit = p.waitFor();

            if (exit != 0) {
                throw new RuntimeException("nmap falló (exit=" + exit + "): " + output);
            }
            return output;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error ejecutando nmap: " + e.getMessage(), e);
        }
    }
}

/*
 * List.of("wsl","nmap",...): ejecuta el comando nmap dentro de WSL.
 * 
 * -sV: intenta detectar el servicio (http, ssh, etc.)
 * 
 * --top-ports 100: para que sea rápido (escanea los 100 puertos más comunes).
 * 
 * -oX -: imprime XML por pantalla (stdout). Ese XML lo vamos a parsear en Java.
 * 
 * exit != 0: si nmap falla, lanzamos error con el texto para depurar.
 */
