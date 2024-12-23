package com.piere.bootcamp.credits.service;

import com.piere.bootcamp.credits.model.dto.CreditDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
    
    Mono<CreditDto> create(CreditDto creditDto);

    Mono<CreditDto> update(CreditDto creditDto);

    Mono<Void> delete(CreditDto creditDto);

    Flux<CreditDto> findAll();

    Mono<CreditDto> findById(String id);

    Mono<CreditDto> makePayment(String id, Double amount);
}
