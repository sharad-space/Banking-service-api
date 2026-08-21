package com.banking.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;
    private String accountType;
    private String currency;
    private String status;
    private String customerNumber;
    private BigDecimal minimumBalance;
}
