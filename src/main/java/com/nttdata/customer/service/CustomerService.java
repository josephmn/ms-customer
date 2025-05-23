package com.nttdata.customer.service;

import com.nttdata.customer.model.CustomerRequest;
import com.nttdata.customer.model.CustomerResponse;
import com.nttdata.customer.model.ResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerResponse> getCustomer();
    Mono<CustomerResponse> getCustomerById(String id);
    Mono<CustomerResponse> createCustomer(CustomerRequest customerDTO);
    Mono<CustomerResponse> updateCustomerById(String id, CustomerRequest customerDTO);
    Mono<ResponseDTO> deleteCustomerById(String id);
}
