package org.james.reactivedemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

// This test does not need Application up and running.
// Very powerful, no need application running!!!!!!     Someone might want to try @SprignJUnitConfig(AppConfig.class)  ????
@WebFluxTest
public class CorrectTestController {

    @Autowired
    private WebTestClient webTestClient;

    private final String TEST_MESSAGE = "Welcome to JstoBigdata.com";

    @Test
    public void testMonoEndpoint() {
        Flux<String> msg$ = webTestClient.get()
                .uri("/hello/mono")
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class).getResponseBody()
                .log();

        msg$.subscribe(System.out::println);

        StepVerifier.create(msg$)
                .expectNext(TEST_MESSAGE)
                .verifyComplete();
    }

    @Test
    public void testFluxEndpoint() {
        Flux<String> msg$ = webTestClient.get()
                .uri("/hello/flux")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class).getResponseBody()
                .log();

        StepVerifier.create(msg$)
                .expectNext(TEST_MESSAGE)
                .verifyComplete();

    }
}
