package com.daniel.portscanner.mongo.document;

public class PortInfo {

    private int port;
    private String protocol;
    private String state;
    private String service;

    public PortInfo() {
    }

    public PortInfo(int port, String protocol, String state, String service) {
        this.port = port;
        this.protocol = protocol;
        this.state = state;
        this.service = service;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getState() {
        return state;
    }

    public String getService() {
        return service;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setService(String service) {
        this.service = service;
    }
}
