package com.james.examples;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

public class FluxTest {

    @Test
    public void justFlux() {
        // Create a Flux
        Flux<String> simpleFlux$ = Flux.just("Hello", "there").log();

        //Simple Subscriber
        simpleFlux$.subscribe(val-> System.out.println(val));

        StepVerifier.create(simpleFlux$)
                .expectNext("Hello")
                .expectNext("there")
                .verifyComplete();
    }

    @Test
    public void subscribeFlux() {
        Flux<String> message$ = Flux.just("Welcome", "to", "JstoBigdata").log();

        message$.subscribe(new Subscriber<String>() {
           @Override
           public void onSubscribe(Subscription s) {
               s.request(Long.MAX_VALUE);
           }

            @Override
            public void onNext(String s) {
               System.out.println("onNext: " + s);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("===== Executio Completed =======");
            }
        });

        StepVerifier.create(message$)
                .expectNext("Welcome")
                .expectNext("to")
                .expectNext("JstoBigdata")
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("John", "john@gmail.com", "12345678"));
        list.add(new Person("Jack", "jack@gmail.com", "12345678"));
        Flux<Person> people$ = Flux.fromIterable(list)
                .flatMap(person -> {
                    return asyncCapitalize(person);          // you can call function, which can be very complicated.
                })
                .log();
        people$.subscribe(System.out::println);
        StepVerifier.create(people$)
                .expectNext(new Person("JOHN", "JOHN@GMAIL.COM", "12345678"))
                .expectNext(new Person("JACK", "JACK@GMAIL.COM", "12345678"))
                .verifyComplete();
    }
    //Used in asynchronously process the Person
    Mono<Person> asyncCapitalize(Person person) {
        Person p = new Person(person.getName().toUpperCase(),
                person.getEmail().toUpperCase(), person.getPhone().toUpperCase());
        Mono<Person> person$ = Mono.just(p);
        return person$;
    }
}
