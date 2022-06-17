package com.duitsev.savingsservice.repository;

import com.duitsev.savingsservice.domain.SavingsAccountRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SavingsAccountRepository extends CrudRepository<SavingsAccountRecord, UUID> {

    Optional<SavingsAccountRecord> findFirstByOrderByCreatedAtDesc();
}

