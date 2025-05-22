package com.nttdata.customer.pesistence.entity;

import com.nttdata.customer.pesistence.entity.enums.ClientType;
import com.nttdata.customer.pesistence.entity.enums.DocumentType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
