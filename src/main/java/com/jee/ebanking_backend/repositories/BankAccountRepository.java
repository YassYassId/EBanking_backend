package com.jee.ebanking_backend.repositories;

import com.jee.ebanking_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
