package com.eventledger.eventgateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventRepository repository;
    private final AccountServiceClient accountServiceClient;

    public EventController(EventRepository repository, AccountServiceClient accountServiceClient) {
        this.repository = repository;
        this.accountServiceClient = accountServiceClient;
    }

    // POST /events
    @PostMapping
    public ResponseEntity<Event> submitEvent(@RequestBody Event event) {
        // Validation
        if (event.getAmount() == null || event.getAmount() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (!"CREDIT".equals(event.getType()) && !"DEBIT".equals(event.getType())) {
            return ResponseEntity.badRequest().build();
        }

        // Idempotency
        Optional<Event> existing = repository.findById(event.getEventId());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(existing.get());
        }

        // Save locally
        Event saved = repository.save(event);

        // Call Account Service
        accountServiceClient.applyTransaction(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /events/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /events?account={accountId}
    @GetMapping(params = "account")
    public List<Event> getEventsForAccount(@RequestParam String accountId) {
        return repository.findByAccountIdOrderByEventTimestampAsc(accountId);
    }

    // GET /events/health
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
