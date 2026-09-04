package com.banking.service;

import com.banking.dto.BoeRequest;
import com.banking.response.BoeResponse;

public interface BoeService {

    BoeResponse receiveBoe(BoeRequest request);

    BoeResponse sendBoe(String boeNumber);
}
