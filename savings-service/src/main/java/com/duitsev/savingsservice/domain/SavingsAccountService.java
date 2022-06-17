package com.duitsev.savingsservice.domain;

import com.duitsev.savingsservice.api.SavingsAccountRecordNotFoundException;
import com.duitsev.savingsservice.repository.SavingsAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class SavingsAccountService {

    private final SavingsAccountRepository repo;

    public SavingsAccountService(SavingsAccountRepository repo) {
        this.repo = repo;
    }

    public SavingsAccountRecord getLastBalance() {
        return repo.findFirstByOrderByCreatedAtDesc().orElseThrow(SavingsAccountRecordNotFoundException::new);
    }

    public SavingsAccountRecord saveNewBalance(BigDecimal balance) {
        return repo.save(new SavingsAccountRecord(UUID.randomUUID(), balance, LocalDateTime.now()));
    }
}

