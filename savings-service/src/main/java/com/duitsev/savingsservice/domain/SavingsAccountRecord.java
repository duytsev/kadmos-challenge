package com.duitsev.savingsservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "savings_account")
public class SavingsAccountRecord {

    @Id
    private UUID id;

    private BigDecimal balance;

    private LocalDateTime createdAt;

    public SavingsAccountRecord(UUID id, BigDecimal balance, LocalDateTime createdAt) {
        this.id = id;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public SavingsAccountRecord() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreated_at() {
        return createdAt;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.createdAt = created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavingsAccountRecord)) return false;
        SavingsAccountRecord that = (SavingsAccountRecord) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getBalance(), that.getBalance()) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBalance(), createdAt);
    }
}

