package com.nttdata.customer.persistence.entity;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PersonEntity.
 *
 * @author Joseph Magallanes
 * @since 2025-06-09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "person")
public class PersonEntity {

    @Id
    private Integer id;
    private String document;
    private String name;
    private String lastName;
    private Integer age;
    private LocalDate dateBirthday;
    private String email;
    private Integer status;
}
