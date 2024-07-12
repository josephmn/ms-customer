package com.nttdata.customer.pesistence.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ClientType {
    STAFF("STAFF", "Personal", 1),
    BUSINESS("BUSINESS", "Empresa", 1);

    private String value;
    private String description;
    private int code;

    @JsonValue
    public String getValue() {
        return value;
    }
}
