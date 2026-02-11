package com.daniel.portscanner.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "scan_results")
public class ScanResult {

    @Id
    private String id;

    private String hostId; // id del Host de PostgreSQL
    private String target; // IP
    private Instant startedAt; // cuándo se lanzó el escaneo

    private List<PortInfo> ports; // array anidado

    public ScanResult() {
    }

    public ScanResult(String hostId, String target, Instant startedAt, List<PortInfo> ports) {
        this.hostId = hostId;
        this.target = target;
        this.startedAt = startedAt;
        this.ports = ports;
    }

    public String getId() {
        return id;
    }

    public String getHostId() {
        return hostId;
    }

    public String getTarget() {
        return target;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public List<PortInfo> getPorts() {
        return ports;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public void setPorts(List<PortInfo> ports) {
        this.ports = ports;
    }
}
