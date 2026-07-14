package com.eventledger.eventgateway;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventRepository eventRepository;
    private final AccountServiceClient accountServiceClient;

    public EventController(EventRepository eventRepository, AccountServiceClient accountServiceClient) {
        this.eventRepository = eventRepository;
        this.accountServiceClient = accountServiceClient;
    }

    @PostMapping
    public ResponseEntity<?> submitEvent(@RequestBody Event event) {
        Optional<Event> existing = eventRepository.findById(event.getEventId());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(existing.get());
        }
        if (event.getAmount() <= 0 ||
                (!event.getType().equals("CREDIT") && !event.getType().equals("DEBIT"))) {
            return ResponseEntity.badRequest().body("Invalid event payload");
        }
        Event saved = eventRepository.save(event);
        try {
            accountServiceClient.applyTransaction(saved, UUID.randomUUID().toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Account Service unavailable, event stored locally");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable String id) {
        return eventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> listEvents(@RequestParam String account) {
        return ResponseEntity.ok(eventRepository.findByAccountIdOrderByEventTimestampAsc(account));
    }
}
