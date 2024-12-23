package com.piere.bootcamp.credits.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piere.bootcamp.credits.dao.CreditDao;
import com.piere.bootcamp.credits.model.dto.CreditDto;
import com.piere.bootcamp.credits.model.dto.MovementDto;
import com.piere.bootcamp.credits.service.CreditService;
import com.piere.bootcamp.credits.service.MovementService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditDao creditDao;

    @Autowired
    private MovementService movementService;

    @Override
    public Mono<CreditDto> create(CreditDto creditDto) {
        return creditDao.save(CreditDto.build().toEntity(creditDto))
            .map(CreditDto.build()::toDto);
    }

    @Override
    public Mono<CreditDto> update(CreditDto creditDto) {
        return creditDao.save(CreditDto.build().toEntity(creditDto))
            .map(CreditDto.build()::toDto);
    }

    @Override
    public Mono<Void> delete(CreditDto creditDto) {
        return creditDao.findById(creditDto.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Crédito no encontrado")))
                .flatMap(credit -> creditDao.delete(credit));
    }

    @Override
    public Flux<CreditDto> findAll() {
        return creditDao.findAll()
            .map(CreditDto.build()::toDto);
    }

    @Override
    public Mono<CreditDto> findById(String id) {
        return creditDao.findById(id)
            .map(CreditDto.build()::toDto);
    }

    @Override
    public Mono<CreditDto> makePayment(String id, Double amount) {
        if (amount == null || amount <= 0) {
            return Mono.error(new IllegalArgumentException("El monto debe ser mayor a cero"));
        }

        return creditDao.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Crédito no encontrado")))
            .flatMap(credit -> movementService.create(MovementDto.builder()
                .build())
                .flatMap(movement -> {
                    credit.setOutstandingBalance(credit.getBalance() - amount);
                    if (credit.getMovementIds() != null) {
                        credit.getMovementIds().add(movement.getId());
                    } else {
                        credit.setMovementIds(Arrays.asList(movement.getId()));
                    }
                    
                    return creditDao.save(credit);
                }))
            .map(CreditDto.build()::toDto);
    }
    
}
