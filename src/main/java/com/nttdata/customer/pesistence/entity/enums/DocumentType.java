package com.nttdata.customer.pesistence.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enum representing different types of documents.
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DocumentType {
    DNI("DNI", "Documento Nacional de Identidad", 1),
    RUC("RUC", "Registro Unico de Contribuyentes", 2),
    CE("CE", "Carnet de Extranjeria", 3),
    PASAPORTE("PASAPORTE", "Pasaporte", 4);

    private String value;
    private String description;
    private int code;

    /**
     * Get the enum value as a string.
     * @param value the string representation of the enum value
     * @return the string representation of the enum value
     * @throws IllegalArgumentException if the value does not match any enum
     */
    @JsonCreator
    public static DocumentType fromValue(String value) {
        for (DocumentType type : DocumentType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    /**
     * Get the enum value as a string.
     *
     * @return the string representation of the enum value
     */
    @JsonValue
    public String getValue() {
        return value;
    }
}
