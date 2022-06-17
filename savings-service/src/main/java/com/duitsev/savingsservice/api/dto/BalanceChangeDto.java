package com.duitsev.savingsservice.api.dto;

import java.math.BigDecimal;

public class BalanceChangeDto {
    private BigDecimal balance;

    public BalanceChangeDto() {
    }

    public BalanceChangeDto(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}

