package com.eventledger.accountservice;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping("/{accountId}/transactions")
    public ResponseEntity<?> applyTransaction(@PathVariable String accountId, @RequestBody Event event) {
        Account account = accountRepository.findById(accountId).orElse(new Account(accountId, 0.0));
        double newBalance = account.getBalance();
        if (event.getType().equals("CREDIT")) newBalance += event.getAmount();
        else if (event.getType().equals("DEBIT")) newBalance -= event.getAmount();
        account.setBalance(newBalance);
        account.addTransaction(event);
        accountRepository.save(account);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<?> getBalance(@PathVariable String accountId) {
        return accountRepository.findById(accountId)
                .map(acc -> ResponseEntity.ok(acc.getBalance()))
                .orElse(ResponseEntity.notFound().build());
    }
}
