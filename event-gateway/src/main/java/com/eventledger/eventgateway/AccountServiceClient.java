package com.eventledger.eventgateway;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceClient.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @CircuitBreaker(name = "accountService", fallbackMethod = "fallbackTransaction")
    public void applyTransaction(Event event, String traceId) {
        String url = "http://localhost:8081/accounts/" + event.getAccountId() + "/transactions";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Trace-Id", traceId);
        HttpEntity<Event> requestEntity = new HttpEntity<>(event, headers);
        restTemplate.postForEntity(url, requestEntity, Void.class);
        logger.info("Transaction for event {} sent successfully traceId={}", event.getEventId(), traceId);
    }

    public void fallbackTransaction(Event event, String traceId, Throwable t) {
        logger.error("Circuit breaker triggered for event {} traceId={} error={}",
                event.getEventId(), traceId, t.getMessage());
    }
}
