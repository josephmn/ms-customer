package com.nttdata.customer.utils;

import java.util.HashMap;
import java.util.Map;
import com.nttdata.customer.exception.types.ValidationException;
import com.nttdata.customer.model.CustomerRequest;
import com.nttdata.customer.model.CustomerResponse;
import com.nttdata.customer.pesistence.entity.CustomerEntity;
import com.nttdata.customer.pesistence.entity.enums.ClientType;
import com.nttdata.customer.pesistence.entity.enums.DocumentType;
import com.nttdata.customer.utils.constans.ConstansConfig;

public class AppUtils {

    private AppUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Converts a CustomerEntity to a CustomerResponse DTO.
     *
     * @param customer the CustomerEntity to convert
     * @return the converted CustomerResponse DTO
     */
    public static CustomerResponse entityToDto(CustomerEntity customer) {
        final CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setName(customer.getName());
        customerResponse.setLastName(customer.getLastName());
        customerResponse.setReason(customer.getReason());
        customerResponse.setDocumentType(CustomerResponse.DocumentTypeEnum.fromValue(
                customer.getDocumentType().getValue()));
        customerResponse.setDocumentNumber(customer.getDocumentNumber());
        customerResponse.setClientType(CustomerResponse.ClientTypeEnum.fromValue(customer.getClientType().getValue()));
        return customerResponse;
    }

    /**
     * Converts a CustomerRequest DTO to a CustomerEntity.
     *
     * @param customerRequest the CustomerRequest DTO to convert
     * @return the converted CustomerEntity
     * @throws ValidationException if validation fails
     */
    public static CustomerEntity dtoToEntity(CustomerRequest customerRequest) {
        final Map<Integer, String> errors = new HashMap<>();
        final CustomerEntity customerEntity = new CustomerEntity();

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

    private static void validateName(
            CustomerRequest customerRequest,
            Map<Integer, String> errors,
            CustomerEntity customerEntity) {
        if (!customerRequest.getDocumentType().getValue().equals("DNI")) {
            customerEntity.setName("");
        }
        else {
            final String name = customerRequest.getName();
            if (name == null || name.isEmpty()) {
                errors.put(1, "Name is not null or empty");
            }
            else if (!name.matches("^[A-Za-z\\s]+$")) {
                errors.put(1, "Name can only contain letters");
            }
            else {
                customerEntity.setName(name);
            }
        }
    }

    private static void validateLastName(
            CustomerRequest customerRequest,
            Map<Integer, String> errors,
            CustomerEntity customerEntity) {
        if (!customerRequest.getDocumentType().getValue().equals("DNI")) {
            customerEntity.setLastName("");
        }
        else {
            final String lastName = customerRequest.getLastName();
            if (lastName == null || lastName.isEmpty()) {
                errors.put(2, "Last name is not null or empty");
            }
            else if (!lastName.matches("^[A-Za-z\\s]+$")) {
                errors.put(2, "Last name can only contain letters");
            }
            else {
                customerEntity.setLastName(lastName);
            }
        }
    }

    private static void validateReason(
            CustomerRequest customerRequest,
            Map<Integer, String> errors,
            CustomerEntity customerEntity) {
        if (!customerRequest.getDocumentType().getValue().equals("DNI")) {
            if (customerRequest.getReason() == null || customerRequest.getReason().isEmpty()) {
                errors.put(ConstansConfig.ID_REASON, "Reason is not null or empty");
            }
            else if (!customerRequest.getReason().matches("^[a-zA-Z0-9 &.,/\\-()°]+$")) {
                errors.put(ConstansConfig.ID_REASON, "Reason can only contain letters, numbers, " +
                    "and special characters (&.,/\\-()°)");
            }
            else {
                customerEntity.setReason(customerRequest.getReason());
            }
        }
        else {
            customerEntity.setReason("");
        }
    }

    private static void validateDocumentType(
            CustomerRequest customerRequest,
            Map<Integer, String> errors,
            CustomerEntity customerEntity) {
        try {
            final DocumentType documentType = DocumentType.fromValue(customerRequest.getDocumentType().getValue());
            customerEntity.setDocumentType(documentType);
        }
        catch (IllegalArgumentException e) {
            errors.put(ConstansConfig.ID_DOCUMENT_TYPE, "Invalid document type: "
                + customerRequest.getDocumentType().getValue());
        }
    }

    private static void validateDocumentNumber(
            CustomerRequest customerRequest,
            Map<Integer, String> errors,
            CustomerEntity customerEntity) {
        final String documentNumber = customerRequest.getDocumentNumber();
        if (customerRequest.getDocumentType().getValue().equals("DNI")) {
            if (documentNumber == null || !documentNumber.matches("\\d{8}")) {
                errors.put(ConstansConfig.ID_DOCUMENT_NUMBER, "Document number must be exactly 8 " +
                    "digits for DNI and contain only numbers");
            }
            else {
                customerEntity.setDocumentNumber(documentNumber);
            }
        }
        else {
            if (documentNumber == null || !documentNumber.matches("\\d{11}")) {
                errors.put(ConstansConfig.ID_DOCUMENT_NUMBER, "Document number must be exactly 11 digits " +
                    "for RUC and contain only numbers");
            }
            else {
                customerEntity.setDocumentNumber(documentNumber);
            }
        }
    }

    private static void validateClientType(
            CustomerRequest customerRequest,
            Map<Integer, String> errors,
            CustomerEntity customerEntity) {
        if (customerRequest.getDocumentType().getValue().equals("DNI")) {
            customerEntity.setClientType(ClientType.STAFF);
        }
        else {
            customerEntity.setClientType(ClientType.BUSINESS);
        }

        if (!customerEntity.getClientType().getValue().equals(customerRequest.getClientType().getValue())) {
            errors.put(ConstansConfig.ID_CLIENT_TYPE, "Invalid client type for the given document type. Expected: "
                    + customerEntity.getClientType().getValue());
        }
    }
}
