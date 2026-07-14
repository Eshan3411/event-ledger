package com.eventledger.accountservice;

import com.eventledger.accountservice.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
