package com.piere.bootcamp.credits.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.credits.model.document.Credit;

import reactor.core.publisher.Flux;

public interface CreditDao extends ReactiveMongoRepository<Credit, String> {
    
    Flux<Credit> findByClientId(String clientId);
}
