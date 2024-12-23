package com.piere.bootcamp.credits.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piere.bootcamp.credits.model.OperationDto;
import com.piere.bootcamp.credits.model.dto.CreditDto;
import com.piere.bootcamp.credits.service.CreditService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/api/credits")
public class CreditController {
    
    @Autowired
    private CreditService creditService;

    /**
     * POST /api/credits : Create a new credit
     * Create a new credit
     *
     * @param creditDto  (required)
     * @return Credit created (status code 201)
     *         or Bad request (status code 400)
     *         or already exists (status code 409)
     */
    @ApiOperation(value = "Create a new credit", nickname = "createCredit", notes = "Create a new credit", response = CreditDto.class, tags={ "credits", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Credit created", response = CreditDto.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 409, message = "already exists") })
    @PostMapping(
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<CreditDto>> createCredit(@ApiParam(value = "" ,required=true )  @Valid @RequestBody CreditDto creditDto) {
        return creditService.create(creditDto)
                .map(credit -> ResponseEntity.created(URI.create("/api/credits")).body(credit));
    }

    /**
     * DELETE /api/credits : Delete a credit
     * Delete a credit
     *
     * @param creditDto  (required)
     * @return Credit deleted (status code 204)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Delete a credit", nickname = "deleteCredit", notes = "Delete a credit", tags={ "credits", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Credit deleted"),
        @ApiResponse(code = 404, message = "Not found") })
    @DeleteMapping(
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<Void>> deleteCredit(@ApiParam(value = "" ,required=true )  @Valid @RequestBody CreditDto creditDto) {
        return creditService.delete(creditDto)
                .map(credit -> ResponseEntity.ok().build());
    }
    
    /**
     * GET /api/credits : Get all credits
     * Get all credits
     *
     * @return A list of credits (status code 200)
     */
    @ApiOperation(value = "Get all credits", nickname = "findAllCredits", notes = "Get all credits", response = CreditDto.class, responseContainer = "List", tags={ "credits", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "A list of credits", response = CreditDto.class, responseContainer = "List") })
    @GetMapping(
        produces = { "application/json" }
    )
    Mono<ResponseEntity<Flux<CreditDto>>> findAllCredits() {
        return Mono.just(ResponseEntity.ok(creditService.findAll()));
    }

    /**
     * POST /api/credits/payment/{id} : Make a payment
     * Make a payment
     *
     * @param id ID of the credit (required)
     * @param operationDto  (required)
     * @return Payment made (status code 200)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Make a payment", nickname = "makePaymentCredit", notes = "Make a payment", response = CreditDto.class, tags={ "credits", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Payment made", response = CreditDto.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @PostMapping(
        value = "/payment/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<CreditDto>> makePaymentCredit(@ApiParam(value = "ID of the credit",required=true) @PathVariable("id") String id,@ApiParam(value = "" ,required=true )  @Valid @RequestBody OperationDto operationDto) {
        return creditService.makePayment(id, operationDto.getAmount())
                .map(credit -> ResponseEntity.ok().body(credit));
    }

    /**
     * PUT /api/credits : Update a credit
     * Update a credit
     *
     * @param creditDto  (required)
     * @return Credit updated (status code 200)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Update a credit", nickname = "updateCredit", notes = "Update a credit", response = CreditDto.class, tags={ "credits", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Credit updated", response = CreditDto.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @PutMapping(
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    Mono<ResponseEntity<CreditDto>> updateCredit(@ApiParam(value = "" ,required=true )  @Valid @RequestBody CreditDto creditDto) {
        return creditService.update(creditDto)
                .map(credit -> ResponseEntity.ok().body(credit));
    }
}
