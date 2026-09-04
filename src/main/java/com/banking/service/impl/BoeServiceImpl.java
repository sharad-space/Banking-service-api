package com.banking.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banking.dto.BoeRequest;
import com.banking.entities.bepush.Boe;
import com.banking.repostories.bepush.BoeRepository;
import com.banking.response.BoeResponse;
import com.banking.service.BoeService;

@Service
public class BoeServiceImpl implements BoeService {

    private final BoeRepository boeRepository;

    public BoeServiceImpl(BoeRepository boeRepository) {
        this.boeRepository = boeRepository;
    }

    @Override
    public BoeResponse receiveBoe(BoeRequest request) {

        // Check duplicate BOE
        if (boeRepository.existsByBoeNumber(request.getBoeNumber())) {

            return new BoeResponse(
                    request.getRequestId(),
                    request.getBoeNumber(),
                    "DUPLICATE",
                    "BOE already exists"
            );
        }

        // Convert DTO to Entity
        Boe boe = new Boe();

        boe.setRequestId(request.getRequestId());
        boe.setBoeNumber(request.getBoeNumber());
        boe.setBoeDate(request.getBoeDate());
        boe.setImporterName(request.getImporterName());
        boe.setImporterIec(request.getImporterIec());
        boe.setProduct(request.getProduct());
        boe.setHsCode(request.getHsCode());
        boe.setQuantity(request.getQuantity());
        boe.setUnit(request.getUnit());
        boe.setCountryOfOrigin(request.getCountryOfOrigin());

        // Initial status
        boe.setStatus("RECEIVED");

        // Save into DB
        boeRepository.save(boe);

        return new BoeResponse(
                request.getRequestId(),
                request.getBoeNumber(),
                "RECEIVED",
                "BOE successfully received and saved"
        );
    }

    @Override
    public BoeResponse sendBoe(String boeNumber) {
        Optional<Boe> optionalBoe = boeRepository.findByBoeNumber(boeNumber);

        if (optionalBoe.isEmpty()) {
            return new BoeResponse(
                    null,
                    boeNumber,
                    "NOT_FOUND",
                    "BOE not found for push"
            );
        }

        Boe boe = optionalBoe.get();
        boe.setStatus("SENT");
        boeRepository.save(boe);

        return new BoeResponse(
                boe.getRequestId(),
                boe.getBoeNumber(),
                "SENT",
                "BOE data pushed successfully"
        );
    }
}