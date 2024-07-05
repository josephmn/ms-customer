package com.nttdata.customer.pesistence.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @JsonCreator
    public static DocumentType fromValue(String value) {
        for (DocumentType type : DocumentType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
