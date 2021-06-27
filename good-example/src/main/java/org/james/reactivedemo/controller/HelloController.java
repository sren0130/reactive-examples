package org.james.reactivedemo.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

// There is a Controller implementation, also there is functional router/handler, these two might conflict with each other.
//  So I comment the annotation out for testing functional endpoint.
//        Webclient and WebTestClient test cases still work or not, not sure!

//@RequestMapping("/hello")
//@RestController
public class HelloController {

    @GetMapping("/mono")
    public Mono<String> getMono() {
        return Mono.just("Welcome to JstoBigdata.com");
    }

    @GetMapping(path="/flux", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> getFlux() {
        Flux<String> msg$ = Flux.just("Welcome ", "to ", "JstoBigdata.com")
                .delayElements(Duration.ofSeconds(1)).log();

        return msg$;
    }
}
