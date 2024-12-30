package com.piere.bootcamp.credits.service;

import com.piere.bootcamp.credits.model.dto.CreditCardDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {
    
    Mono<CreditCardDto> create(CreditCardDto creditCardDto);

    Mono<CreditCardDto> update(CreditCardDto creditCardDto);

    Mono<Void> delete(CreditCardDto creditCardDto);

    Flux<CreditCardDto> findAll();
    
    Mono<CreditCardDto> findById(String id);

    Mono<CreditCardDto> makePayment(String id, Double amount);

    Mono<CreditCardDto> makeCharge(String id, Double amount);

    Mono<CreditCardDto> checkBalance(String id);

    Mono<Boolean> hasCreditCard(String clientId);

}
