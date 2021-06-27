package com.james.examples;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class TransformationTest {

    @Test
    public void transformMap() {
        List<String> names = Arrays.asList("google", "abc", "fb", "stackoverflow");
        Flux<String> names$ = Flux.fromIterable(names)
                .filter(name -> name.length() > 5).log()   // add log() here,
                .map(name -> name.toUpperCase()).log()      // one more log() on new stream.
                .repeat(1)
                .log();

        StepVerifier.create(names$)
                .expectNext("GOOGLE", "STACKOVERFLOW", "GOOGLE", "STACKOVERFLOW")
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap() {
        List<String> names = Arrays.asList("google", "abc", "fb", "stackoverflow");
        Flux<String> names$ = Flux.fromIterable(names)
                .filter(name -> name.length() > 5)
                .flatMap(name -> {
                    return Mono.just(name.toUpperCase());
                })
                .repeat(1)
                .log();

        StepVerifier.create(names$)
                .expectNext("GOOGLE", "STACKOVERFLOW", "GOOGLE", "STACKOVERFLOW")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge() {
        Flux<String> name1$ = Flux.just("Blenders", "Old", "Johnnie");
        Flux<String> name2$ = Flux.just("Pride", "Monk", "Walker");

        Flux<String> names$ = Flux.merge(name1$, name2$).log();         // any flux can call log()

        StepVerifier.create(names$)
                .expectSubscription()
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker")
                .verifyComplete();
    }

    @Test
    public  void concatWithDelay(){
        Flux<String> names1$ = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> names2$ = Flux.just("Pride", "Monk", "Walker")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> names$ = Flux.concat(names1$, names2$)
                .log();
        StepVerifier.create(names$)
                .expectSubscription()
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker")
                .verifyComplete();
    }

    @Test
    public void combineWithZip(){
        Flux<String> names1$ = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> names2$ = Flux.just("Pride", "Monk", "Walker")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> names$ = Flux.zip(names1$, names2$, (n1, n2) -> {
            return n1.concat(" ").concat(n2);
        }).log();
        StepVerifier.create(names$)
                .expectNext("Blenders Pride", "Old Monk", "Johnnie Walker")
                .verifyComplete();
    }
}
