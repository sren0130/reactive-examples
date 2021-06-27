package org.james.webfluxmongo.respository;

import org.james.webfluxmongo.model.Contact;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ContactRepository extends ReactiveMongoRepository<Contact, String> {
    Mono<Contact> findFirstByEmail(String email);
    Mono<Contact> findAllByPhoneOrName(String phoneOrName);
}
