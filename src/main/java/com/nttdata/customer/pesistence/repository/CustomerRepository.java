package com.nttdata.customer.pesistence.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.nttdata.customer.pesistence.entity.CustomerEntity;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<CustomerEntity, String> {
    Mono<CustomerEntity> findByDocumentNumber(String documentNumber);
}
