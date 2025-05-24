package com.nttdata.customer.persistence.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.nttdata.customer.persistence.entity.CustomerEntity;
import reactor.core.publisher.Mono;

/**
 * CustomerRepository.
 *
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@Repository
public interface CustomerRepository extends ReactiveMongoRepository<CustomerEntity, String> {
    Mono<CustomerEntity> findByDocumentNumber(String documentNumber);
}
