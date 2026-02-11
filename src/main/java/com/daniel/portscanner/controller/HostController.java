package com.daniel.portscanner.controller;

import com.daniel.portscanner.postgres.entity.Host;
import com.daniel.portscanner.postgres.repository.HostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hosts")
public class HostController {

    private final HostRepository hostRepository;

    // inyeccion de dependencias
    public HostController(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Host createHost(@RequestBody Host host) {
        return hostRepository.save(host);
    }

    @GetMapping
    public Iterable<Host> listHosts() {
        return hostRepository.findAll();
    }

}
