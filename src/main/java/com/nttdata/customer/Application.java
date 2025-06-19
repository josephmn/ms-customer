package com.nttdata.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the MsCustomer application.
 * This class serves as the entry point for the Spring Boot application.
 * It loads environment variables from a .env file using the Dotenv library.
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
