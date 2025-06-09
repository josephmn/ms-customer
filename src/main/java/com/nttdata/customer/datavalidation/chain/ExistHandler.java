package com.nttdata.customer.datavalidation.chain;

import com.nttdata.customer.persistence.entity.PersonEntity;
import com.nttdata.customer.persistence.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Clase que maneja la validación de existencia de datos.
 * Si el campo exits es falso, devuelve un mensaje para traer los datos.
 * Si es verdadero, pasa al siguiente manejador en la cadena.
 * @author Joseph Magallanes
 * @since 2025-06-09
 */
@RequiredArgsConstructor
public class ExistHandler extends DataHandler {

    private final PersonRepository personRepository;

    @Override
    public Mono<String> handle(PersonEntity person) {

        return personRepository.existsByDocument(person.getDocument())
            .flatMap(exist -> {
                if (!exist) {
                    return Mono.just("Traer Data");
                }
                return next != null ? next.handle(person) : null;
            });
    }
}
