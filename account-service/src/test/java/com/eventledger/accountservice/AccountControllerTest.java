package com.eventledger.accountservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testApplyTransaction() {
        Event event = new Event();
        event.setEventId("evt-101");
        event.setAccountId("acc-001");
        event.setType("CREDIT");
        event.setAmount(200.0);
        event.setCurrency("USD");
        event.setEventTimestamp(Instant.now());

        ResponseEntity<Account> response = restTemplate.postForEntity("/accounts/acc-001/transactions", event, Account.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200.0, response.getBody().getBalance());
    }
}
