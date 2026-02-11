package com.daniel.portscanner.controller;

import com.daniel.portscanner.mongo.document.ScanResult;
import com.daniel.portscanner.mongo.repository.ScanResultRepository;
import com.daniel.portscanner.postgres.entity.Host;
import com.daniel.portscanner.postgres.repository.HostRepository;
import com.daniel.portscanner.service.ScanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.daniel.portscanner.dto.DiffSummary;
import com.daniel.portscanner.service.DiffService;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hosts/{hostId}/scans")
public class ScanController {

    private final HostRepository hostRepository;
    private final ScanResultRepository scanResultRepository;
    private final ScanService scanService;
    private final DiffService diffService;

    public ScanController(HostRepository hostRepository,
            ScanResultRepository scanResultRepository,
            ScanService scanService,
            DiffService diffService) {
        this.hostRepository = hostRepository;
        this.scanResultRepository = scanResultRepository;
        this.scanService = scanService;
        this.diffService = diffService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScanResult createScan(@PathVariable UUID hostId) {
        Host host = hostRepository.findById(hostId)
                .orElseThrow(() -> new RuntimeException("Host no encontrado"));

        ScanResult scan = scanService.runScan(host);
        return scanResultRepository.save(scan);
    }

    @GetMapping
    public List<ScanResult> listScans(@PathVariable UUID hostId) {
        return scanResultRepository.findByHostIdOrderByStartedAtDesc(hostId.toString());
    }

    @GetMapping("/last/diff")
    public DiffSummary lastDiff(@PathVariable UUID hostId) {
        var scans = scanResultRepository.findByHostIdOrderByStartedAtDesc(
                hostId.toString(),
                PageRequest.of(0, 2));

        if (scans.size() < 2) {
            throw new RuntimeException("Necesitas al menos 2 escaneos para comparar");
        }

        return diffService.diff(scans.get(0), scans.get(1));
    }

}
