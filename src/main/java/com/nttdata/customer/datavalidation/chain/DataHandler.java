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
public abstract class DataHandler {
    protected DataHandler next;

    public DataHandler setNext(DataHandler next) {
        this.next = next;
        return next;
    }

    /**
     * Método abstracto que debe ser implementado por las subclases para manejar la validación de datos.
     * @param person Entidad de persona a validar.
     * @return Mono que contiene un Optional con el mensaje de error o vacío si no hay errores.
     */
    public abstract Mono<String> handle(PersonEntity person);
}
