package com.daniel.portscanner.dto;

import java.util.List;

public class DiffSummary {
    private String hostId;
    private String latestScanId;
    private String previousScanId;

    private List<Integer> newlyOpenPorts;
    private List<Integer> closedPorts;

    public DiffSummary() {
    }

    public DiffSummary(String hostId, String latestScanId, String previousScanId,
            List<Integer> newlyOpenPorts, List<Integer> closedPorts) {
        this.hostId = hostId;
        this.latestScanId = latestScanId;
        this.previousScanId = previousScanId;
        this.newlyOpenPorts = newlyOpenPorts;
        this.closedPorts = closedPorts;
    }

    public String getHostId() {
        return hostId;
    }

    public String getLatestScanId() {
        return latestScanId;
    }

    public String getPreviousScanId() {
        return previousScanId;
    }

    public List<Integer> getNewlyOpenPorts() {
        return newlyOpenPorts;
    }

    public List<Integer> getClosedPorts() {
        return closedPorts;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setLatestScanId(String latestScanId) {
        this.latestScanId = latestScanId;
    }

    public void setPreviousScanId(String previousScanId) {
        this.previousScanId = previousScanId;
    }

    public void setNewlyOpenPorts(List<Integer> newlyOpenPorts) {
        this.newlyOpenPorts = newlyOpenPorts;
    }

    public void setClosedPorts(List<Integer> closedPorts) {
        this.closedPorts = closedPorts;
    }
}
