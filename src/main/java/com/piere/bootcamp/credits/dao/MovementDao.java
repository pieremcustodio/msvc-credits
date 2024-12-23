package com.piere.bootcamp.credits.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.credits.model.document.Movement;

public interface MovementDao extends ReactiveMongoRepository<Movement, String> {
    
}
