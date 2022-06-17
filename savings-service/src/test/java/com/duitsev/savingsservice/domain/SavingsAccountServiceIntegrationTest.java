package com.duitsev.savingsservice.domain;

import com.duitsev.savingsservice.api.SavingsAccountRecordNotFoundException;
import com.duitsev.savingsservice.repository.SavingsAccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static com.duitsev.savingsservice.TestData.savingsAccountRecord;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SavingsAccountServiceIntegrationTest {

    @Autowired
    private SavingsAccountRepository repo;

    @Autowired
    private SavingsAccountService underTest;

    @BeforeEach
    public void cleanUp() {
        repo.deleteAll();
    }

    @Test
    public void shouldGetLastBalanceRecord() {
        var rec1 = savingsAccountRecord(new BigDecimal(-25));
        var rec2 = savingsAccountRecord(new BigDecimal("33.3"));

        repo.save(rec1);
        repo.save(rec2);

        var last = underTest.getLastBalance();

        Assertions.assertThat(last).isEqualTo(rec2);
    }

    @Test
    public void shouldThrowNotFoundExceptionIfNoRecordsCreated() {
        Assertions.assertThatThrownBy(() -> {
            underTest.getLastBalance();
        }).isInstanceOf(SavingsAccountRecordNotFoundException.class);
    }

    @Test
    public void shouldSaveNewBalance() {
        var balance = BigDecimal.TEN;

        var saved = underTest.saveNewBalance(balance);

        Assertions.assertThat(saved.getBalance()).isEqualTo(balance);
    }
}

