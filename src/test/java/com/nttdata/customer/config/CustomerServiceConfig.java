package com.nttdata.customer.config;

import com.nttdata.customer.service.CustomerService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class CustomerServiceConfig {

    @Bean
    public CustomerService customerService() {
        return mock(CustomerService.class);
    }
}
