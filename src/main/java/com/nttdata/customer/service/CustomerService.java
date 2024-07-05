package com.nttdata.customer.service;

import com.banking.openapi.model.CustomerRequest;
import com.banking.openapi.model.CustomerResponse;
import com.banking.openapi.model.ResponseDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<ResponseEntity<Flux<CustomerResponse>>> getCustomer();
    Mono<ResponseEntity<CustomerResponse>> getCustomerById(String id);
    Mono<ResponseEntity<CustomerResponse>> createCustomer(CustomerRequest customerDTO);
    Mono<ResponseEntity<CustomerResponse>> updateCustomerById(String id, CustomerRequest customerDTO);
    Mono<ResponseEntity<ResponseDTO>> deleteCustomerById(String id);
}
