package com.nttdata.customer.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.nttdata.customer.persistence.entity.PersonEntity;
import reactor.core.publisher.Mono;

/**
 * PersonRepository.
 *
 * @author Joseph Magallanes
 * @since 2025-06-09
 */
@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Integer> {
    Mono<PersonEntity> findByDocument(String document);
    Mono<Boolean> existsByDocument(String document);
}
