package com.nttdata.customer.utils;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class OpenApiConfig {

    @Value("${openapi.info.github.url}")
    private String githubUrl;

    @Value("${openapi.info.terms}")
    private String terms;

    @Value("${openapi.info.contact.name}")
    private String contactName;

    @Value("${openapi.info.contact.email}")
    private String contactEmail;

    @Value("${openapi.info.contact.email}")
    private String contactUrl;

    @Value("${openapi.info.servers.url}")
    private String serversUrl;

    @Value("${openapi.info.servers.description}")
    private String serversDescription;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Banking Customer - OpenAPI 3.0")
                        .description("This is the version of an API created with MongoDB, reactive programming, and functional programming for customer maintenance.\n"
                                + "\nSome useful links:\n"
                                + "\nThe repository in GitHub: " + githubUrl)
                        .termsOfService(terms)
                        .contact(new Contact()
                                .name(contactName)
                                .email(contactEmail)
                                .url(contactUrl)
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Find out more about Swagger")
                        .url("http://swagger.io"))
                .servers(
                        Collections.singletonList(
                                new Server().url(serversUrl).description(serversDescription)
                        )
                );
    }
}
