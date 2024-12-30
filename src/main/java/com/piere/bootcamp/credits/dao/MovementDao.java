package com.piere.bootcamp.credits.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.piere.bootcamp.credits.model.document.Movement;

import reactor.core.publisher.Flux;

public interface MovementDao extends ReactiveMongoRepository<Movement, String> {
    
    Flux<Movement> findByIdInAndCreateAtBetween(List<String> movementIds, LocalDate startDate, LocalDate endDate);
}
