package com.jee.ebanking_backend.web;

import com.jee.ebanking_backend.dtos.*;
import com.jee.ebanking_backend.exceptions.BalanceNotSufficientException;
import com.jee.ebanking_backend.exceptions.BankAccountNotFoundException;
import com.jee.ebanking_backend.exceptions.CustomerNotFoundException;
import com.jee.ebanking_backend.services.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/accounts")
    public BankAccountDTO createAccount(@RequestBody CreateAccountDTO createAccountDTO) throws CustomerNotFoundException {

        if (createAccountDTO.getCustomerId() == null || createAccountDTO.getInitialBalance() == null) {
            throw new IllegalArgumentException("Customer ID and Initial Balance are required");
        }

        String status = createAccountDTO.getStatus() != null ? createAccountDTO.getStatus() : "CREATED";

        if ("SavingAccount".equals(createAccountDTO.getType())) {
            return bankAccountService.saveSavingBankAccount(
                    createAccountDTO.getInitialBalance(),
                    createAccountDTO.getInterestRate(),
                    createAccountDTO.getCustomerId(),
                    status
            );
        } else if ("CurrentAccount".equals(createAccountDTO.getType())) {
            return bankAccountService.saveCurrentBankAccount(
                    createAccountDTO.getInitialBalance(),
                    createAccountDTO.getOverDraft(),
                    createAccountDTO.getCustomerId(),
                    status
            );
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }
    }

    @PutMapping("/accounts/{id}")
    public BankAccountDTO updateAccount(
            @PathVariable String id,
            @RequestBody UpdateAccountDTO dto) throws BankAccountNotFoundException {

        System.out.println("Received PATCH request for account ID: " + id);
        System.out.println("Status: " + dto.getStatus());
        System.out.println("Interest Rate: " + dto.getInterestRate());
        System.out.println("Overdraft: " + dto.getOverDraft());

        return bankAccountService.updateAccount(id, dto);
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId)
            throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size)
            throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO)
            throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) throws BankAccountNotFoundException, CustomerNotFoundException {
        bankAccountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
