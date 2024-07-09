package com.nttdata.customer.service.Impl;

import com.banking.openapi.model.CustomerRequest;
import com.banking.openapi.model.CustomerResponse;
import com.banking.openapi.model.ResponseDTO;
import com.nttdata.customer.exception.types.CustomerAlreadyExistsException;
import com.nttdata.customer.exception.types.NotFoundException;
import com.nttdata.customer.pesistence.repository.CustomerRepository;
import com.nttdata.customer.service.CustomerService;
import com.nttdata.customer.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> getCustomer() {
        log.info("service customerRepository - ini");

        Flux<CustomerResponse> customerDTOFlux = this.customerRepository.findAll()
                .doOnError(error -> log.error("Error getAllCustomer: ", error))
                .map(AppUtils::entityToDto)
                .doOnTerminate(() -> log.info("service customerRepository - end"));

        return Mono.just(ResponseEntity.ok(customerDTOFlux));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(String id) {
        log.info("service getCustomerById - ini");

        return this.customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Customer with ID %s does not exist", id)))
                .doOnNext(customerEntity -> log.info("Customer by id service: {}", customerEntity))
                .flatMap(customerEntity -> Mono.just(ResponseEntity.ok(AppUtils.entityToDto(customerEntity))))
                .doOnTerminate(() -> log.info("service getCustomerById - end"));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(CustomerRequest customerRequest) {
        log.info("service createCustomer - ini");

        String documentNumber = customerRequest.getDocumentNumber();
        return this.customerRepository.findByDocumentNumber(documentNumber)
                .flatMap(existingCustomer -> {
                    return Mono.error(new CustomerAlreadyExistsException(
                            "Customer exist with document number: %s", customerRequest.getDocumentNumber()));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    return Mono.just(customerRequest)
                            .map(AppUtils::dtoToEntity)
                            .doOnNext(customerBefore -> log.info("Customer before create: {}", customerBefore))
                            .flatMap(this.customerRepository::insert)
                            .map(AppUtils::entityToDto)
                            .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                            .doOnNext(customerAfter -> log.info("Customer after create: {}", customerAfter));
                }))
                .cast(ResponseEntity.class)
                .map(responseEntity -> (ResponseEntity<CustomerResponse>) responseEntity)
                .doOnTerminate(() -> log.info("service createCustomer - end"));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> updateCustomerById(String id, CustomerRequest customerRequest) {
        log.info("service updateCustomerById - ini");

        return this.customerRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    return Mono.error(new NotFoundException("Customer not exist with document number: %s", id));
                }))
                .flatMap(existCustomer -> Mono.just(customerRequest)
                        .map(request -> {
                            var customerEntity = AppUtils.dtoToEntity(request);
                            customerEntity.setId(id);
                            return customerEntity;
                        })
                        .flatMap(this.customerRepository::save)
                        .map(AppUtils::entityToDto)
                        .map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto)))
                .doOnTerminate(() -> log.info("service updateCustomerById - end"));
    }

    @Override
    public Mono<ResponseEntity<ResponseDTO>> deleteCustomerById(String id) {
        log.info("service deleteCustomerById - ini");

        return this.customerRepository.existsById(id)
                .flatMap(exist -> {
                    if (exist) {
                        return this.customerRepository.deleteById(id)
                                .then(Mono.just(ResponseEntity.status(HttpStatus.OK)
                                        .body(new ResponseDTO().message("Customer deleted successfully"))));
                    } else {
                        return Mono.error(new CustomerAlreadyExistsException(
                                "Customer not exist with id: %s", id));
                    }
                });
    }
}
