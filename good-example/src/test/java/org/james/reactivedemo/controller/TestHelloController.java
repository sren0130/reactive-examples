package org.james.reactivedemo.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class TestHelloController {
    // Webclient is for Synchronous processing RESTful API.
    private static WebClient webClient;

    @BeforeAll
    private static void setWebClient() {
        webClient = WebClient.create("http://localhost:8080");
    }

    @Test
    public void testMonoEndpoint() {
        // Test, first get Mono<String>, then use StepVerifier to check the string value
        Mono<String> msg$ = webClient.get()
                .uri("/hello/mono")
                .retrieve()
                .bodyToMono(String.class).log();

        StepVerifier.create(msg$)
                .expectNext("Welcome to JstoBigdata.com")
                .expectComplete();
    }

    @Test
    public void testFluxEndpoint() {
        Flux<String> msg$ = webClient.get()
                .uri("/hello/flux")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(String.class).log();

        StepVerifier.create(msg$)
                .expectNext("Welcome to JstoBigdata.com")
                .verifyComplete();
    }
}
