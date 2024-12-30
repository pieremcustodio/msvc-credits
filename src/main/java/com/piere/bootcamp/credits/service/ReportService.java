package com.piere.bootcamp.credits.service;

import java.time.LocalDate;
import java.util.List;

import com.piere.bootcamp.credits.model.dto.projection.ReportProjection;

import reactor.core.publisher.Mono;

public interface ReportService {
    
    Mono<List<ReportProjection>> calculateAverageDailyBalanceForCredits(String clientId,  LocalDate startDate, LocalDate endDate);
}
