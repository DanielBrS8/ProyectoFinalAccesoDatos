package com.daniel.portscanner.service;

import com.daniel.portscanner.dto.DiffSummary;
import com.daniel.portscanner.mongo.document.PortInfo;
import com.daniel.portscanner.mongo.document.ScanResult;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiffService {

    public DiffSummary diff(ScanResult latest, ScanResult previous) {

        Set<Integer> latestPorts = toPortSet(latest.getPorts());
        Set<Integer> previousPorts = toPortSet(previous.getPorts());

        List<Integer> newlyOpen = latestPorts.stream()
                .filter(p -> !previousPorts.contains(p))
                .sorted()
                .collect(Collectors.toList());

        List<Integer> closed = previousPorts.stream()
                .filter(p -> !latestPorts.contains(p))
                .sorted()
                .collect(Collectors.toList());

        return new DiffSummary(
                latest.getHostId(),
                latest.getId(),
                previous.getId(),
                newlyOpen,
                closed);
    }

    private Set<Integer> toPortSet(List<PortInfo> ports) {
        if (ports == null)
            return Set.of();
        Set<Integer> set = new HashSet<>();
        for (PortInfo p : ports)
            set.add(p.getPort());
        return set;
    }
}
