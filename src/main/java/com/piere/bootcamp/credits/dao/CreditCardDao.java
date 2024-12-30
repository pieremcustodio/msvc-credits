package com.piere.bootcamp.credits.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.credits.model.document.CreditCard;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardDao extends ReactiveMongoRepository<CreditCard, String> {
    
    Flux<CreditCard> findByClientId(String clientId);

    Mono<Boolean> existsByClientId(String clientId);
}
