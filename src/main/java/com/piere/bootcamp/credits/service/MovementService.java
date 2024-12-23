package com.piere.bootcamp.credits.service;

import java.util.List;

import com.piere.bootcamp.credits.model.dto.MovementDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    
    Mono<MovementDto> create(MovementDto movementDto);
    
    Mono<MovementDto> update(MovementDto movementDto);

    Mono<Void> delete(MovementDto movementDto);
    
    Flux<MovementDto> findAll();

    Flux<MovementDto> findAllByIdList(List<String> idList);
}
