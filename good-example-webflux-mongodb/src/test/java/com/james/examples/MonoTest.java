package com.james.examples;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

public class MonoTest {

    @Test
    public void simpleMonoTest() {
        // Create a Mono with a message
        // .map is an operator to manipulate the string
        // .log trace the details
        Mono<String> message = Mono.just("Welcome to Mono")
                .map(msg -> msg.concat(".com")).log();

        message.subscribe(System.out::println);   // Mono emits data (event)

        // Test the message mono
        StepVerifier.create(message)
                .expectNext("Welcome to Mono.com")
                .verifyComplete();   // Invoking the Mondo
    }

    @Test
    public void subscribeMono() {
        Mono<String> message = Mono.just("Welcome to Mono")
                .map(msg -> msg.concat(".com")).log();

        // Once subscribe method is called, provider emits data
        message.subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError: " + t);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete ----- YES ------------ ");
            }
        });

        // Test also like 'rerun' message stream.
        StepVerifier.create(message)
                .expectNext("Welcome to Mono.com")
                .verifyComplete();
    }

    @Test
    public void emptyMono() {
        Mono empty$ = Mono.empty().log();

        empty$.subscribe(val -> {
                    System.out.println("======== value ==========" + val);
                },
                err -> {
                },
                () -> {
                    System.out.println(" ==== On Complete Invoked ===========");
                });
    }

    @Test
    public void noSignalMono() {
        // Mono that never returns a value
        Mono<String> noSignal$ = Mono.never();
        // Can not use - Mono.never().log()

        noSignal$.subscribe(val -> {
                    System.out.println("==== Value =======" + val);
                }
        );

        StepVerifier.create(noSignal$)
                .expectTimeout(Duration.ofSeconds(3))
                .verify();
    }

    @Test
    public void subscribeMonoWithException() {
        Mono<String> message$ = Mono.error(new RuntimeException("Check error mono"));

        message$.subscribe(
                value ->{ System.out.println(value);},
                err->{ err.printStackTrace();},
                ()-> {
                    System.out.println(" === execution completed ====");
                } );

        StepVerifier.create(message$)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void fromSupplier() {
        Supplier<String> stringSupplier = () -> "Sample Message";
        Mono<String> sMono$ = Mono.fromSupplier(stringSupplier).log();

        sMono$.subscribe(System.out::println);

        StepVerifier.create(sMono$)
                .expectNext("Sample Message")
                .verifyComplete();
    }

    @Test
    public void filterMono() {
        Supplier<String> stringSupplier = ()-> "Hello World";
        Mono<String> filteredMono$ = Mono.fromSupplier(stringSupplier)
                .filter(str ->str.length()>5)
                .log();
        filteredMono$.subscribe(System.out::println);

        StepVerifier.create(filteredMono$)    // nothing left in Mono stream.
                .expectComplete();
    }
}
