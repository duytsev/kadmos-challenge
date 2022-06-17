package com.duitsev.savingsservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SavingsAccountRecordNotFoundException extends RuntimeException {

    public SavingsAccountRecordNotFoundException() {
        super("Savings account record was not found");
    }
}

