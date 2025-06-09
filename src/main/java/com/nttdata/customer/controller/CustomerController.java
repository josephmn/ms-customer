package com.nttdata.customer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import com.nttdata.customer.api.CustomerApi;
import com.nttdata.customer.model.CustomerRequest;
import com.nttdata.customer.model.CustomerResponse;
import com.nttdata.customer.model.ResponseDTO;
import com.nttdata.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CustomerController is a REST controller that handles customer-related API requests.
 * It implements the CustomerApi interface and uses the CustomerService to perform operations.
 *
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> getCustomer(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(this.customerService.getCustomer()))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(
        @PathVariable("clientId") String clientId, ServerWebExchange exchange) {
        return this.customerService.getCustomerById(clientId)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerByDocumentNumber(
        @PathVariable("number") String number, ServerWebExchange exchange) {
        return this.customerService.getCustomerByDocumentNumber(number)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(
        @RequestBody Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerRequest.flatMap(this.customerService::createCustomer)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> updateCustomerById(
        @PathVariable("clientId") String clientId,
        @RequestBody Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerRequest.flatMap(dto -> this.customerService.updateCustomerById(clientId, dto))
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<ResponseDTO>> deleteCustomerById(
        @PathVariable("clientId") String clientId, ServerWebExchange exchange) {
        return this.customerService.deleteCustomerById(clientId)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
