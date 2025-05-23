package com.nttdata.customer.service.Impl;

import org.springframework.stereotype.Service;
import com.nttdata.customer.exception.types.CustomerAlreadyExistsException;
import com.nttdata.customer.exception.types.NotFoundException;
import com.nttdata.customer.model.CustomerRequest;
import com.nttdata.customer.model.CustomerResponse;
import com.nttdata.customer.model.ResponseDTO;
import com.nttdata.customer.pesistence.repository.CustomerRepository;
import com.nttdata.customer.service.CustomerService;
import com.nttdata.customer.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Flux<CustomerResponse> getCustomer() {
        log.info("service customerRepository - ini");

        return this.customerRepository.findAll()
                .doOnError(error -> log.error("Error getAllCustomer: ", error))
                .map(AppUtils::entityToDto)
                .doOnTerminate(() -> log.info("service customerRepository - end"));
    }

    @Override
    public Mono<CustomerResponse> getCustomerById(String id) {
        log.info("service getCustomerById - ini");

        return this.customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Customer with ID %s does not exist", id)))
                .doOnNext(customerEntity -> log.info("Customer by id service: {}", customerEntity))
                .flatMap(customerEntity -> Mono.just(AppUtils.entityToDto(customerEntity)))
                .doOnTerminate(() -> log.info("service getCustomerById - end"));
    }

    @Override
    public Mono<CustomerResponse> createCustomer(CustomerRequest customerRequest) {
        log.info("service createCustomer - ini");

        final String documentNumber = customerRequest.getDocumentNumber();
        return this.customerRepository.findByDocumentNumber(documentNumber)
                .flatMap(existingCustomer -> Mono.error(new CustomerAlreadyExistsException(
                        "Customer exists with document number: %s", documentNumber)))
                .switchIfEmpty(Mono.defer(() -> {
                    final var customerEntity = AppUtils.dtoToEntity(customerRequest);
                    log.info("Customer before create: {}", customerEntity);
                    return this.customerRepository.insert(customerEntity)
                            .map(AppUtils::entityToDto)
                            .doOnNext(customerAfter -> log.info("Customer after create: {}", customerAfter));
                }))
                .cast(CustomerResponse.class)
                .doOnTerminate(() -> log.info("service createCustomer - end"));
    }

    @Override
    public Mono<CustomerResponse> updateCustomerById(String id, CustomerRequest customerRequest) {
        log.info("service updateCustomerById - ini");

        return this.customerRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        new NotFoundException("Customer not exist with document number: %s", id)
                )))
                .flatMap(existCustomer -> Mono.just(customerRequest)
                        .map(request -> {
                            final var customerEntity = AppUtils.dtoToEntity(request);
                            customerEntity.setId(id);
                            return customerEntity;
                        })
                        .flatMap(this.customerRepository::save)
                        .map(AppUtils::entityToDto)
                .doOnTerminate(() -> log.info("service updateCustomerById - end")));
    }

    @Override
    public Mono<ResponseDTO> deleteCustomerById(String id) {
        log.info("service deleteCustomerById - ini");

        return this.customerRepository.existsById(id)
                .flatMap(exist -> {
                    if (exist) {
                        return this.customerRepository.deleteById(id)
                                .then(Mono.just(new ResponseDTO().message("Customer deleted successfully")));
                    }
                    else {
                        return Mono.error(new CustomerAlreadyExistsException(
                                "Customer not exist with id: %s", id));
                    }
                });
    }
}
