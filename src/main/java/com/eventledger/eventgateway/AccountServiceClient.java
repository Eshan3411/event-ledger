package com.eventledger.eventgateway;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountServiceClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public void applyTransaction(Event event) {
        String url = "http://localhost:8081/accounts/" + event.getAccountId() + "/transactions";
        try {
            restTemplate.postForEntity(url, event, Void.class);
        } catch (Exception e) {
            // Graceful degradation: log and continue
            System.err.println("Account Service unavailable: " + e.getMessage());
        }
    }
}
