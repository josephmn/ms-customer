package com.nttdata.customer.persistence.entity;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.nttdata.customer.persistence.entity.enums.ClientType;
import com.nttdata.customer.persistence.entity.enums.DocumentType;
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
    private String address;
    private String email;
    private List<String> phones;

    @Override
    public String toString() {
        return "class CustomerEntity {\n" +
            "    id: " + id + "\n" +
            "    name: " + name + "\n" +
            "    lastName: " + lastName + "\n" +
            "    reason: " + reason + "\n" +
            "    documentType: " + documentType + "\n" +
            "    documentNumber: " + documentNumber + "\n" +
            "    clientType: " + clientType + "\n" +
            "    address: " + address + "\n" +
            "    email: " + email + "\n" +
            "    phones: " + phones +
            "\n}";
    }
}
