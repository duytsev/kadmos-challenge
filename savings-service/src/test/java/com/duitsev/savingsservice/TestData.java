package com.duitsev.savingsservice;

import com.duitsev.savingsservice.domain.SavingsAccountRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestData {

    public static SavingsAccountRecord savingsAccountRecord(BigDecimal balance) {
        return new SavingsAccountRecord(UUID.randomUUID(), balance, LocalDateTime.now());
    }
}

