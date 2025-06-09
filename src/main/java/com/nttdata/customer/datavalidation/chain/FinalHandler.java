package com.nttdata.customer.datavalidation.chain;

import com.nttdata.customer.persistence.entity.PersonEntity;
import reactor.core.publisher.Mono;

/**
 * Clase que maneja la validación de datos de correo electrónico.
 * Si el campo dataEmail es falso, devuelve un mensaje para traer los datos.
 * Si es verdadero, pasa al siguiente manejador en la cadena.
 * @author Joseph Magallanes
 * @since 2025-06-09
 */
public class FinalHandler extends DataHandler {
    @Override
    public Mono<String> handle(PersonEntity person) {
        return Mono.just("Traer Data");
    }
}
