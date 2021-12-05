package com.example.server.enumerations;

public enum Status {

    SERVER_UP("SERVER_UP"),
    SERVER_DOWN("SERVER_DOWN");

    private final String status;

    Status(String status) {
        this.status = status.toString();
    }

    public String getStatus() {
        return this.status;
    }

}
