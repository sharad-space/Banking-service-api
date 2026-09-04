package com.banking.entities.bepush;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "boe")
public class Boe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestId;

    @Column(unique = true)
    private String boeNumber;

    private LocalDate boeDate;

    private String importerName;

    private String importerIec;

    private String product;

    private String hsCode;

    private Double quantity;

    private String unit;

    private String countryOfOrigin;

    private String status;

    public Long getId() {
        return id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBoeNumber() {
        return boeNumber;
    }

    public void setBoeNumber(String boeNumber) {
        this.boeNumber = boeNumber;
    }

    public LocalDate getBoeDate() {
        return boeDate;
    }

    public void setBoeDate(LocalDate boeDate) {
        this.boeDate = boeDate;
    }

    public String getImporterName() {
        return importerName;
    }

    public void setImporterName(String importerName) {
        this.importerName = importerName;
    }

    public String getImporterIec() {
        return importerIec;
    }

    public void setImporterIec(String importerIec) {
        this.importerIec = importerIec;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}