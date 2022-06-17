package com.duitsev.savingsservice.api;

import com.duitsev.savingsservice.api.dto.BalanceChangeDto;
import com.duitsev.savingsservice.api.dto.BalanceDto;
import com.duitsev.savingsservice.domain.SavingsAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SavingsController {

    private final SavingsAccountService savingsAccountService;

    public SavingsController(SavingsAccountService savingsAccountService) {
        this.savingsAccountService = savingsAccountService;
    }

    @GetMapping("balance")
    public ResponseEntity<BalanceDto> getBalance() {
        var record = savingsAccountService.getLastBalance();
        return ResponseEntity.ok(new BalanceDto(record.getBalance()));
    }

    @PostMapping("balance")
    public ResponseEntity<BalanceDto> changeBalance(@RequestBody BalanceChangeDto balance) {
        var record = savingsAccountService.saveNewBalance(balance.getBalance());
        return ResponseEntity.ok(new BalanceDto(record.getBalance()));
    }
}
