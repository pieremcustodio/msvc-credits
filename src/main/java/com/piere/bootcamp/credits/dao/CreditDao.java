package com.piere.bootcamp.credits.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.credits.model.document.Credit;

public interface CreditDao extends ReactiveMongoRepository<Credit, String> {
    
}
