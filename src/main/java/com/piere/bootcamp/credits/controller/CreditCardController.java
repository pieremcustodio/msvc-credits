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
import com.piere.bootcamp.credits.model.dto.CreditCardDto;
import com.piere.bootcamp.credits.service.CreditCardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/api/creditcards")
public class CreditCardController {
    
    @Autowired
    private CreditCardService creditCardService;

    /**
     * GET /api/creditcards/checkbalance/{id} : Check balance of a credit card
     * Check balance of a credit card
     *
     * @param id ID of the credit card (required)
     * @return Balance checked (status code 200)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Check balance of a credit card", nickname = "checkBalance", notes = "Check balance of a credit card", response = CreditCardDto.class, tags = {
            "creditCards", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Balance checked", response = CreditCardDto.class),
            @ApiResponse(code = 404, message = "Not found") })
    @GetMapping(value = "/checkbalance/{id}", produces = { "application/json" })
    Mono<ResponseEntity<CreditCardDto>> checkBalance(
            @ApiParam(value = "ID of the credit card", required = true) @PathVariable("id") String id) {
        return creditCardService.checkBalance(id)
                .map(creditCard -> ResponseEntity.ok().body(creditCard));
    }

    /**
     * POST /api/creditcards : Create a new credit card
     * Create a new credit card
     *
     * @param creditCardDto (required)
     * @return Credit card created (status code 201)
     *         or Bad request (status code 400)
     *         or already exists (status code 409)
     */
    @ApiOperation(value = "Create a new credit card", nickname = "createCreditCard", notes = "Create a new credit card", response = CreditCardDto.class, tags = {
            "creditCards", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Credit card created", response = CreditCardDto.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "already exists") })
    @PostMapping(produces = { "application/json" }, consumes = { "application/json" })
    Mono<ResponseEntity<CreditCardDto>> createCreditCard(
            @ApiParam(value = "", required = true) @Valid @RequestBody CreditCardDto creditCardDto) {
        return creditCardService.create(creditCardDto)
                .map(creditCard -> ResponseEntity.created(URI.create("/api/creditcards")).body(creditCard));
    }

    /**
     * DELETE /api/creditcards : Delete a credit card
     * Delete a credit card
     *
     * @param creditCardDto (required)
     * @return Credit card deleted (status code 204)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Delete a credit card", nickname = "deleteCreditCard", notes = "Delete a credit card", tags = {
            "creditCards", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Credit card deleted"),
            @ApiResponse(code = 404, message = "Not found") })
    @DeleteMapping(produces = { "application/json" }, consumes = { "application/json" })
    Mono<ResponseEntity<Void>> deleteCreditCard(
            @ApiParam(value = "", required = true) @Valid @RequestBody CreditCardDto creditCardDto) {
        return creditCardService.delete(creditCardDto)
                .map(creditCard -> ResponseEntity.ok().build());
    }

    /**
     * GET /api/creditcards : Get all credit cards
     * Get all credit cards
     *
     * @return A list of credit cards (status code 200)
     */
    @ApiOperation(value = "Get all credit cards", nickname = "findAllCreditCards", notes = "Get all credit cards", response = CreditCardDto.class, responseContainer = "List", tags = {
            "creditCards", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list of credit cards", response = CreditCardDto.class, responseContainer = "List") })
    @GetMapping(produces = { "application/json" })
    Mono<ResponseEntity<Flux<CreditCardDto>>> findAllCreditCards() {
        return Mono.just(ResponseEntity.ok(creditCardService.findAll()));
    }

    /**
     * POST /api/creditcards/charge/{id} : Make a charge
     * Make a charge
     *
     * @param id           ID of the credit card (required)
     * @param operationDto (required)
     * @return Charge made (status code 200)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     *         or Insuficient funds (status code 409)
     */
    @ApiOperation(value = "Make a charge", nickname = "makeChargeCreditCard", notes = "Make a charge", response = CreditCardDto.class, tags = {
            "creditCards", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Charge made", response = CreditCardDto.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 409, message = "Insuficient funds") })
    @PostMapping(value = "/charge/{id}", produces = { "application/json" }, consumes = { "application/json" })
    Mono<ResponseEntity<CreditCardDto>> makeChargeCreditCard(
            @ApiParam(value = "ID of the credit card", required = true) @PathVariable("id") String id,
            @ApiParam(value = "", required = true) @Valid @RequestBody OperationDto operationDto) {
        return creditCardService.makeCharge(id, operationDto.getAmount())
                .map(creditCard -> ResponseEntity.ok().body(creditCard));
    }

    /**
     * POST /api/creditcards/payment/{id} : Make a payment
     * Make a payment
     *
     * @param id           ID of the credit card (required)
     * @param operationDto (required)
     * @return Payment made (status code 200)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Make a payment", nickname = "makePaymentCreditCard", notes = "Make a payment", response = CreditCardDto.class, tags = {
            "creditCards", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment made", response = CreditCardDto.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found") })
    @PostMapping(value = "/payment/{id}", produces = { "application/json" }, consumes = { "application/json" })
    Mono<ResponseEntity<CreditCardDto>> makePaymentCreditCard(
            @ApiParam(value = "ID of the credit card", required = true) @PathVariable("id") String id,
            @ApiParam(value = "", required = true) @Valid @RequestBody OperationDto operationDto) {
        return creditCardService.makePayment(id, operationDto.getAmount())
                .map(creditCard -> ResponseEntity.ok().body(creditCard));
    }

    /**
     * PUT /api/creditcards : Update a credit card
     * Update a credit card
     *
     * @param creditCardDto (required)
     * @return Credit card updated (status code 200)
     *         or Bad request (status code 400)
     *         or Not found (status code 404)
     */
    @ApiOperation(value = "Update a credit card", nickname = "updateCreditCard", notes = "Update a credit card", response = CreditCardDto.class, tags = {
            "creditCards", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Credit card updated", response = CreditCardDto.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found") })
    @PutMapping(produces = { "application/json" }, consumes = { "application/json" })
    Mono<ResponseEntity<CreditCardDto>> updateCreditCard(
            @ApiParam(value = "", required = true) @Valid @RequestBody CreditCardDto creditCardDto) {
        return creditCardService.update(creditCardDto)
                .map(creditCard -> ResponseEntity.ok().body(creditCard));
    }

    @GetMapping("/has-credit-card/{clientId}")
    Mono<ResponseEntity<Boolean>> hasCreditCard(@PathVariable String clientId) {
        return creditCardService.hasCreditCard(clientId)
                .map(hasCreditCard -> ResponseEntity.ok().body(hasCreditCard));
    }
}
