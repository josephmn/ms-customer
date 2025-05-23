package com.nttdata.customer.pesistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.nttdata.customer.pesistence.entity.enums.ClientType;
import com.nttdata.customer.pesistence.entity.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a customer in the database.
 * This class is mapped to the "customer" collection in MongoDB.
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@Document(collection = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String reason;
    private DocumentType documentType;
    private String documentNumber;
    private ClientType clientType;
}
