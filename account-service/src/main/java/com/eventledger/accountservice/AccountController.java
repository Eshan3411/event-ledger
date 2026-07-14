package com.eventledger.accountservice;

import com.eventledger.accountservice.Account;
import com.eventledger.accountservice.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Account> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Account create(@RequestBody Account account) {
        return repository.save(account);
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }
}
