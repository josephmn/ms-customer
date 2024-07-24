package com.nttdata.customer.controller;

import com.banking.openapi.api.CustomerApi;
import com.banking.openapi.model.CustomerRequest;
import com.banking.openapi.model.CustomerResponse;
import com.banking.openapi.model.ResponseDTO;
import com.nttdata.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> getCustomer(ServerWebExchange exchange) {
        return this.customerService.getCustomer();
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(
            @PathVariable("id") String id, ServerWebExchange exchange) {
        return this.customerService.getCustomerById(id);
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(
            @RequestBody Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerRequest.flatMap(this.customerService::createCustomer);
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> updateCustomerById(
            @PathVariable("id") String id,
            @RequestBody Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerRequest.flatMap(dto -> this.customerService.updateCustomerById(id, dto));
    }

    @Override
    public Mono<ResponseEntity<ResponseDTO>> deleteCustomerById(
            @PathVariable("id") String id, ServerWebExchange exchange) {
        return this.customerService.deleteCustomerById(id);
    }
}
