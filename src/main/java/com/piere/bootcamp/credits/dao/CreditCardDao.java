package com.piere.bootcamp.credits.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.credits.model.document.CreditCard;

public interface CreditCardDao extends ReactiveMongoRepository<CreditCard, String> {
    
}
