package com.nttdata.customer.persistence.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enum representing different types of clients.
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ClientType {
    STAFF("STAFF", "Personal", 1),
    BUSINESS("BUSINESS", "Empresa", 1);

    private String value;
    private String description;
    private int code;

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
