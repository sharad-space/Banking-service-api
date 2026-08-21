package com.banking.service;

import com.banking.dto.CustomerBankingDetailDto;

public interface CustomerBankingService {

    CustomerBankingDetailDto getCustomerBankingDetails(Long customerId);
}
