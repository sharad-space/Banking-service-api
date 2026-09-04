package com.banking.response;

public class BoeResponse {

    private String requestId;
    private String boeNumber;
    private String status;
    private String message;

    public BoeResponse() {
    }

    public BoeResponse(
            String requestId,
            String boeNumber,
            String status,
            String message) {

        this.requestId = requestId;
        this.boeNumber = boeNumber;
        this.status = status;
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getBoeNumber() {
        return boeNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}