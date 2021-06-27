package org.james.reactivedemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient     // Important
public class FunctionalEndpoint {

    private static final String TEST_MESSAGE = "How about hello world?";

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void monoMessage() {
        Flux<String> msg$ = webTestClient.get()
                .uri("/functional/mono")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class).getResponseBody()
                .log();

        StepVerifier.create(msg$)
                .expectNext(TEST_MESSAGE)
                .verifyComplete();
    }

    @Test
    public void fluxMessage() {
        Flux<String> msg$ = webTestClient.get()
                .uri("/functional/flux")
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
