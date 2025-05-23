package com.jee.ebanking_backend.services;


import com.jee.ebanking_backend.dtos.*;
import com.jee.ebanking_backend.exceptions.BalanceNotSufficientException;
import com.jee.ebanking_backend.exceptions.BankAccountNotFoundException;
import com.jee.ebanking_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    // In BankAccountService.java
    CurrentBankAccountDTO saveCurrentBankAccount(Double initialBalance, Double overDraft, Long customerId, String status) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(Double initialBalance, Double interestRate, Long customerId, String status) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    BankAccountDTO updateAccount(String accountId, UpdateAccountDTO dto)
            throws BankAccountNotFoundException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    void deleteAccount(String accountId) throws CustomerNotFoundException, BankAccountNotFoundException;

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}
