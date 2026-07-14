package com.eventledger.eventgateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.*;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testIdempotency() {
        Event event = new Event();
        event.setEventId("evt-001");
        event.setAccountId("acc-001");
        event.setType("CREDIT");
        event.setAmount(100.0);
        event.setCurrency("USD");
        event.setEventTimestamp(Instant.now());

        ResponseEntity<Event> firstResponse = restTemplate.postForEntity("/events", event, Event.class);
        assertEquals(HttpStatus.CREATED, firstResponse.getStatusCode());

        ResponseEntity<Event> secondResponse = restTemplate.postForEntity("/events", event, Event.class);
        assertEquals(HttpStatus.CONFLICT, secondResponse.getStatusCode());
    }

    @Test
    void testValidationError() {
        Event event = new Event();
        event.setEventId("evt-002");
        event.setAccountId("acc-001");
        event.setType("CREDIT");
        event.setAmount(0); // invalid
        event.setCurrency("USD");
        event.setEventTimestamp(Instant.now());

        ResponseEntity<String> response = restTemplate.postForEntity("/events", event, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
