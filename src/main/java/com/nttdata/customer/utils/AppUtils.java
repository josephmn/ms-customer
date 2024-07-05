package com.nttdata.customer.utils;

import com.banking.openapi.model.CustomerRequest;
import com.banking.openapi.model.CustomerResponse;
import com.nttdata.customer.exception.types.ValidationException;
import com.nttdata.customer.pesistence.entity.CustomerEntity;
import com.nttdata.customer.pesistence.entity.enums.ClientType;
import com.nttdata.customer.pesistence.entity.enums.DocumentType;

import java.util.HashMap;
import java.util.Map;

public class AppUtils {

    private AppUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static CustomerResponse entityToDto(CustomerEntity customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setName(customer.getName());
        customerResponse.setLastName(customer.getLastName());
        customerResponse.setReason(customer.getReason());
        customerResponse.setDocumentType(CustomerResponse.DocumentTypeEnum.fromValue(customer.getDocumentType().getValue()));
        customerResponse.setDocumentNumber(customer.getDocumentNumber());
        customerResponse.setClientType(CustomerResponse.ClientTypeEnum.fromValue(customer.getClientType().getValue()));
        return customerResponse;
    }

    public static CustomerEntity dtoToEntity(CustomerRequest customerRequest) {
        Map<Integer, String> errors = new HashMap<>();
        CustomerEntity customerEntity = new CustomerEntity();

        validateName(customerRequest, errors, customerEntity);
        validateLastName(customerRequest, errors, customerEntity);
        validateReason(customerRequest, errors, customerEntity);
        validateDocumentType(customerRequest, errors, customerEntity);
        validateDocumentNumber(customerRequest, errors, customerEntity);
        validateClientType(customerRequest, errors, customerEntity);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        return customerEntity;
    }

    private static void validateName(CustomerRequest customerRequest, Map<Integer, String> errors, CustomerEntity customerEntity) {
        if (!customerRequest.getDocumentType().getValue().equals("DNI")) {
            customerEntity.setName("");
        } else {
            String name = customerRequest.getName();
            if (name == null || name.isEmpty()) {
                errors.put(1, "Name is not null or empty");
            } else if (!name.matches("^[A-Za-z\\s]+$")) {
                errors.put(1, "Name can only contain letters");
            } else {
                customerEntity.setName(name);
            }
        }
    }

    private static void validateLastName(CustomerRequest customerRequest, Map<Integer, String> errors, CustomerEntity customerEntity) {
        if (!customerRequest.getDocumentType().getValue().equals("DNI")) {
            customerEntity.setLastName("");
        } else {
            String lastName = customerRequest.getLastName();
            if (lastName == null || lastName.isEmpty()) {
                errors.put(2, "Last name is not null or empty");
            } else if (!lastName.matches("^[A-Za-z\\s]+$")) {
                errors.put(2, "Last name can only contain letters");
            } else {
                customerEntity.setLastName(lastName);
            }
        }
    }

    private static void validateReason(CustomerRequest customerRequest, Map<Integer, String> errors, CustomerEntity customerEntity) {
        if (!customerRequest.getDocumentType().getValue().equals("DNI")) {
            if (customerRequest.getReason() == null || customerRequest.getReason().isEmpty()) {
                errors.put(3, "Reason is not null or empty");
            } else if (!customerRequest.getReason().matches("^[a-zA-Z0-9 &.,/\\-()°]+$")) {
                errors.put(3, "Reason can only contain letters, numbers, and special characters (&.,/\\-()°)");
            } else {
                customerEntity.setReason(customerRequest.getReason());
            }
        } else {
            customerEntity.setReason("");
        }
    }

    private static void validateDocumentType(CustomerRequest customerRequest, Map<Integer, String> errors, CustomerEntity customerEntity) {
        try {
            DocumentType documentType = DocumentType.fromValue(customerRequest.getDocumentType().getValue());
            customerEntity.setDocumentType(documentType);
        } catch (IllegalArgumentException e) {
            errors.put(4, "Invalid document type: " + customerRequest.getDocumentType().getValue());
        }
    }

    private static void validateDocumentNumber(CustomerRequest customerRequest, Map<Integer, String> errors, CustomerEntity customerEntity) {
        String documentNumber = customerRequest.getDocumentNumber();
        if (customerRequest.getDocumentType().getValue().equals("DNI")) {
            if (documentNumber == null || !documentNumber.matches("\\d{8}")) {
                errors.put(5, "Document number must be exactly 8 digits for DNI and contain only numbers");
            } else {
                customerEntity.setDocumentNumber(documentNumber);
            }
        } else {
            if (documentNumber == null || !documentNumber.matches("\\d{11}")) {
                errors.put(5, "Document number must be exactly 11 digits for RUC and contain only numbers");
            } else {
                customerEntity.setDocumentNumber(documentNumber);
            }
        }
    }

    private static void validateClientType(CustomerRequest customerRequest, Map<Integer, String> errors, CustomerEntity customerEntity) {
        if (customerRequest.getDocumentType().getValue().equals("DNI")) {
            customerEntity.setClientType(ClientType.STAFF);
        } else {
            customerEntity.setClientType(ClientType.BUSINESS);
        }

        if (!customerEntity.getClientType().getValue().equals(customerRequest.getClientType().getValue())) {
            errors.put(6, "Invalid client type for the given document type. Expected: " + customerEntity.getClientType().getValue());
        }
    }
}
