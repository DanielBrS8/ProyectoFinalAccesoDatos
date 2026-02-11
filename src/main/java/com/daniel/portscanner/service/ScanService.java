package com.daniel.portscanner.service;

import com.daniel.portscanner.mongo.document.PortInfo;
import com.daniel.portscanner.mongo.document.ScanResult;
import com.daniel.portscanner.postgres.entity.Host;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScanService {

    private final NmapService nmapService;

    public ScanService(NmapService nmapService) {
        this.nmapService = nmapService;
    }

    public ScanResult runScan(Host host) {
        String xml = nmapService.scanXml(host.getAddress());
        List<PortInfo> ports = parsePortsFromNmapXml(xml);

        ScanResult scan = new ScanResult();
        scan.setHostId(host.getId().toString());
        scan.setTarget(host.getAddress());
        scan.setStartedAt(Instant.now());
        scan.setPorts(ports);

        return scan;
    }

    private List<PortInfo> parsePortsFromNmapXml(String xml) {
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            NodeList portNodes = doc.getElementsByTagName("port");
            List<PortInfo> result = new ArrayList<>();

            for (int i = 0; i < portNodes.getLength(); i++) {
                Element portEl = (Element) portNodes.item(i);

                int port = Integer.parseInt(portEl.getAttribute("portid"));
                String protocol = portEl.getAttribute("protocol");

                // <state state="open" .../>
                NodeList stateNodes = portEl.getElementsByTagName("state");
                String state = stateNodes.getLength() > 0
                        ? ((Element) stateNodes.item(0)).getAttribute("state")
                        : "unknown";

                // <service name="http" .../>
                NodeList serviceNodes = portEl.getElementsByTagName("service");
                String service = serviceNodes.getLength() > 0
                        ? ((Element) serviceNodes.item(0)).getAttribute("name")
                        : "";

                // Nos quedamos solo con los abiertos (lo típico en un port scanner)
                if ("open".equalsIgnoreCase(state)) {
                    result.add(new PortInfo(port, protocol, state, service));
                }
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Error parseando XML de nmap: " + e.getMessage(), e);
        }
    }
}
