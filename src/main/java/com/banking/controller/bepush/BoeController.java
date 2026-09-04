package com.banking.controller.bepush;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banking.dto.BoeRequest;
import com.banking.response.BoeResponse;
import com.banking.service.BoeService;

@RestController
@RequestMapping("/api/v1/boe")
public class BoeController {

    private final BoeService boeService;

    public BoeController(BoeService boeService) {
        this.boeService = boeService;
    }

    @PostMapping("/receive")
    public ResponseEntity<BoeResponse> receiveBoe(
            @RequestBody BoeRequest request) {

        BoeResponse response = boeService.receiveBoe(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/send")
    public ResponseEntity<BoeResponse> sendBoe(@RequestParam String boeNumber) {
        BoeResponse response = boeService.sendBoe(boeNumber);
        if ("NOT_FOUND".equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send/{boeNumber}")
    public ResponseEntity<BoeResponse> sendBoeByPath(@PathVariable String boeNumber) {
        BoeResponse response = boeService.sendBoe(boeNumber);
        if ("NOT_FOUND".equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }
}