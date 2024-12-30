package com.piere.bootcamp.credits.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piere.bootcamp.credits.dao.CreditCardDao;
import com.piere.bootcamp.credits.dao.CreditDao;
import com.piere.bootcamp.credits.dao.MovementDao;
import com.piere.bootcamp.credits.model.document.Credit;
import com.piere.bootcamp.credits.model.document.CreditCard;
import com.piere.bootcamp.credits.model.document.Movement;
import com.piere.bootcamp.credits.model.dto.CreditCardDto;
import com.piere.bootcamp.credits.model.dto.CreditDto;
import com.piere.bootcamp.credits.model.dto.projection.ReportProjection;
import com.piere.bootcamp.credits.service.ReportService;

import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CreditDao creditDao;

    @Autowired
    private CreditCardDao creditCardDao;

    @Autowired
    private MovementDao movementDao;

    @Override
    public Mono<List<ReportProjection>> calculateAverageDailyBalanceForCredits(String clientId, LocalDate startDate,
            LocalDate endDate) {
        Mono<List<ReportProjection>> creditReport = creditDao.findByClientId(clientId)
                .flatMap(credit -> movementDao.findByIdInAndCreateAtBetween(
                        credit.getMovementIds(),
                        startDate,
                        endDate)
                        .collectList()
                        .map(movements -> calculateDailyBalanceForCredit(credit, movements, startDate, endDate)))
                .collectList();

        Mono<List<ReportProjection>> creditCardReport = creditCardDao.findByClientId(clientId)
                .flatMap(creditCard -> movementDao.findByIdInAndCreateAtBetween(
                        creditCard.getMovementIds(),
                        startDate,
                        endDate)
                        .collectList()
                        .map(movements -> calculateDailyBalanceForCreditCard(creditCard, movements, startDate,
                                endDate)))
                .collectList();

        return Mono.zip(creditReport, creditCardReport, (creditReports, creditCardReports) -> {
            List<ReportProjection> allReports = new ArrayList<>();
            allReports.addAll(creditReports);
            allReports.addAll(creditCardReports);
            return allReports;
        });
    }

    private ReportProjection calculateDailyBalanceForCredit(Credit credit, List<Movement> movements,
            LocalDate startDate, LocalDate endDate) {
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        double totalBalance = movements.stream()
                .mapToDouble(Movement::getAmount)
                .sum();

        double averageBalance = totalBalance / totalDays;

        return new ReportProjection(
                CreditDto.build().toDto(credit),
                averageBalance);
    }

    private ReportProjection calculateDailyBalanceForCreditCard(CreditCard creditCard, List<Movement> movements,
            LocalDate startDate, LocalDate endDate) {
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        double totalBalance = movements.stream()
                .mapToDouble(Movement::getAmount)
                .sum();

        double averageBalance = totalBalance / totalDays;

        return new ReportProjection(
                CreditCardDto.build().toDto(creditCard),
                averageBalance);
    }
    
}
