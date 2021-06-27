package com.james.examples;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoSimple {
    public static void main(String[] args) {
        simpleMono();

        simpleFlux();
    }

    public static void simpleMono() {
        Mono<String> message = Mono.just("Hi there!");
        message.subscribe(System.out::println);

        message.log();
        // subscribe(onNextConsumer, onErrorConsumer, onCompleteConsumer
        message.subscribe(System.out::println,
                error -> System.out.println(error.getMessage())
        );
    }

    // subscribe contains three different channels, onNext, onError, onComplete.
    // every log() output the stream element, which could be new stream just created by map.
    public static void simpleFlux() {
        Flux<Integer> $num = Flux.range(1, 10).log()
                .map(val -> val * 2).log()
                .map(val -> val + 1).log();
        $num.subscribe(
                value -> {
                    System.out.println(value);
                },
                error -> {
                    System.out.println(error);
                },
                () -> {
                    System.out.println("======== Completed ============");
                }
        );
    }
}
