package com.nttdata.customer.service.impl;

import org.springframework.stereotype.Service;
import com.nttdata.customer.datavalidation.chain.DataEmail;
import com.nttdata.customer.datavalidation.chain.DataHandler;
import com.nttdata.customer.datavalidation.chain.ExistHandler;
import com.nttdata.customer.datavalidation.chain.FinalHandler;
import com.nttdata.customer.datavalidation.chain.StatusHandler;
import com.nttdata.customer.exception.types.CustomerAlreadyExistsException;
import com.nttdata.customer.exception.types.NotFoundException;
import com.nttdata.customer.model.CustomerRequest;
import com.nttdata.customer.model.CustomerResponse;
import com.nttdata.customer.model.ResponseDTO;
import com.nttdata.customer.persistence.repository.CustomerRepository;
import com.nttdata.customer.persistence.repository.PersonRepository;
import com.nttdata.customer.service.CustomerService;
import com.nttdata.customer.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CustomerServiceImpl.
 *
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;

    @Override
    public Flux<CustomerResponse> getCustomer() {
        log.info("service customerRepository - ini");

        return this.customerRepository.findAll()
            .doOnError(error -> log.error("Error getAllCustomer: ", error))
            .map(AppUtils::entityToDto)
            .doOnTerminate(() -> log.info("service customerRepository - end"));
    }

    @Override
    public Mono<CustomerResponse> getCustomerById(String clientId) {
        log.info("service getCustomerById - ini");

        return this.customerRepository.findById(clientId)
            .switchIfEmpty(Mono.error(new NotFoundException("Customer with ID %s does not exist", clientId)))
            .doOnNext(customerEntity -> log.info("Customer by id service: {}", customerEntity))
            .flatMap(customerEntity -> Mono.just(AppUtils.entityToDto(customerEntity)))
            .doOnTerminate(() -> log.info("service getCustomerById - end"));
    }

    @Override
    public Mono<CustomerResponse> getCustomerByDocumentNumber(String number) {
        log.info("service getCustomerByDocumentNumber - ini");

        return personRepository.findByDocument(number)
            .flatMap(person -> {
                final DataHandler handlerChain = new ExistHandler(personRepository);
                handlerChain
                    .setNext(new DataEmail())
                    .setNext(new StatusHandler())
                    .setNext(new FinalHandler());

                final Mono<String> result = handlerChain.handle(person);

                return result.flatMap(msg -> {
                    if (msg != null) {
                        return customerRepository.findByDocumentNumber(number);
                    }
                    // Puedes lanzar un error si el handler devuelve algo que indica problema
                    return Mono.error(new RuntimeException(msg));
                });
            });
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
    public Mono<CustomerResponse> updateCustomerById(String clientId, CustomerRequest customerRequest) {
        log.info("service updateCustomerById - ini");

        return this.customerRepository.findById(clientId)
            .switchIfEmpty(Mono.defer(() -> Mono.error(
                new NotFoundException("Customer not exist with document number: %s", clientId)
            )))
            .flatMap(existCustomer -> Mono.just(customerRequest)
                .map(request -> {
                    final var customerEntity = AppUtils.dtoToEntity(request);
                    customerEntity.setId(clientId);
                    return customerEntity;
                })
                .flatMap(this.customerRepository::save)
                .map(AppUtils::entityToDto)
                .doOnTerminate(() -> log.info("service updateCustomerById - end")));
    }

    @Override
    public Mono<ResponseDTO> deleteCustomerById(String clientId) {
        log.info("service deleteCustomerById - ini");

        return this.customerRepository.existsById(clientId)
            .flatMap(exist -> {
                if (exist) {
                    return this.customerRepository.deleteById(clientId)
                        .then(Mono.just(new ResponseDTO().message("Customer deleted successfully")));
                }
                else {
                    return Mono.error(new CustomerAlreadyExistsException(
                        "Customer not exist with id: %s", clientId));
                }
            });
    }
}
