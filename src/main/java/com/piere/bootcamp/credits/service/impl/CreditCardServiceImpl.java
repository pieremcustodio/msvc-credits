package com.piere.bootcamp.credits.service.impl;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piere.bootcamp.credits.dao.CreditCardDao;
import com.piere.bootcamp.credits.model.dto.CreditCardDto;
import com.piere.bootcamp.credits.model.dto.MovementDto;
import com.piere.bootcamp.credits.model.enums.MovementTypeEnum;
import com.piere.bootcamp.credits.service.CreditCardService;
import com.piere.bootcamp.credits.service.MovementService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardDao creditCardDao;

    @Autowired
    private MovementService movementService;

    @Override
    public Mono<CreditCardDto> create(CreditCardDto creditCardDto) {
        return creditCardDao.save(CreditCardDto.build().toEntity(creditCardDto))
            .map(CreditCardDto.build()::toDto);
    }

    @Override
    public Mono<CreditCardDto> update(CreditCardDto creditCardDto) {
        return creditCardDao.save(CreditCardDto.build().toEntity(creditCardDto))
            .map(CreditCardDto.build()::toDto);
    }

    @Override
    public Mono<Void> delete(CreditCardDto creditCardDto) {
        return creditCardDao.findById(creditCardDto.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Tarjeta de crédito no encontrada")))
                .flatMap(creditCard -> creditCardDao.delete(creditCard));
    }

    @Override
    public Flux<CreditCardDto> findAll() {
        return creditCardDao.findAll()
            .map(CreditCardDto.build()::toDto);
    }

    @Override
    public Mono<CreditCardDto> findById(String id) {
        return creditCardDao.findById(id)
            .map(CreditCardDto.build()::toDto);
    }

    @Override
    public Mono<CreditCardDto> makePayment(String id, Double amount) {
        if (amount == null || amount <= 0) {
            return Mono.error(new IllegalArgumentException("El monto debe ser mayor a cero"));
        }

        return creditCardDao.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Tarjeta de crédito no encontrada")))
                .flatMap(creditCard -> movementService.create(MovementDto.builder()
                        .amount(amount)
                        .movementType(MovementTypeEnum.PAGO)
                        .createAt(LocalDate.now())
                        .description("Pago de tarjeta de crédito")
                        .build())
                        .flatMap(movement -> {
                            creditCard.setAvailableBalance(creditCard.getAvailableBalance() + amount);
                            if (creditCard.getMovementIds() != null) {
                                creditCard.getMovementIds().add(movement.getId());
                            } else {
                                creditCard.setMovementIds(Arrays.asList(movement.getId()));
                            }

                            return creditCardDao.save(creditCard);
                        }))
                .map(CreditCardDto.build()::toDto);
    }

    @Override
    public Mono<CreditCardDto> makeCharge(String id, Double amount) {
        if (amount == null || amount <= 0) {
            return Mono.error(new IllegalArgumentException("El monto debe ser mayor a cero"));
        }

        return creditCardDao.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Tarjeta de crédito no encontrada")))
                .flatMap(creditCard -> movementService.create(MovementDto.builder()
                        .amount(amount)
                        .movementType(MovementTypeEnum.CARGO)
                        .createAt(LocalDate.now())
                        .description("Cargo a tarjeta de crédito")
                        .build())
                        .flatMap(movement -> {
                            creditCard.setAvailableBalance(creditCard.getAvailableBalance() - amount);
                            if (creditCard.getMovementIds() != null) {
                                creditCard.getMovementIds().add(movement.getId());
                            } else {
                                creditCard.setMovementIds(Arrays.asList(movement.getId()));
                            }

                            return creditCardDao.save(creditCard);
                        }))
                .map(CreditCardDto.build()::toDto);
    }

    @Override
    public Mono<CreditCardDto> checkBalance(String id) {
        return creditCardDao.findById(id)
            .flatMap(creditCard -> {
                return movementService.findAllByIdList(creditCard.getMovementIds())
                    .collectList()
                    .map(movements -> {
                        CreditCardDto dto = CreditCardDto.build().toDto(creditCard);
                        dto.setMovements(movements);
                        return dto;
                    });
            });
    }
    
}
