package com.piere.bootcamp.credits.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piere.bootcamp.credits.model.dto.projection.ReportProjection;
import com.piere.bootcamp.credits.model.dto.search.SearchReportDto;
import com.piere.bootcamp.credits.service.ReportService;

import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/api/credits")
public class ReportController {
    
    @Autowired
    private ReportService reportService;


     @PostMapping(
        path = "/calculateAverageDailyBalance",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public Mono<List<ReportProjection>> calculateAverageDailyBalance(@Valid @RequestBody SearchReportDto search) {
        return reportService.calculateAverageDailyBalanceForCredits(search.getClientId(), search.getStartDate(), search.getEndDate())
                .flatMap(Mono::just);
    }
}
