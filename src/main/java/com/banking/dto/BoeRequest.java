package com.banking.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BoeRequest {

    private String requestId;
    private String boeNumber;
    private LocalDate boeDate;

    private String importerName;
    private String importerIec;

    private String product;
    private String hsCode;

    private Double quantity;
    private String unit;

    private String countryOfOrigin;

    // Generate Getters and Setters
}