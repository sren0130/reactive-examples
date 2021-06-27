package org.james.reactivedemo;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class HelloHandler {

    /**
     * Serves a plain_text
     */
    public Mono<ServerResponse> monoMessage(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(
                        Mono.just("How about hello world?"), String.class
                );
    }

    /**
     * Serves a plain_text
     */
    public Mono<ServerResponse> fluxMessage(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(
                        Flux.just("How ", "about ", "hello ", "world?")
                            .delayElements(Duration.ofSeconds(1)).log(), String.class
                );
    }
}

