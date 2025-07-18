package com.nttdata.customer.utils;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.nttdata.customer.config.ApplicationProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

/**
 * OpenApiConfig.
 *
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private final ApplicationProperties applicationProperties;
    /**
     * Configures the OpenAPI documentation for the application.
     *
     * @return OpenAPI object with the configuration.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Banking Customer - OpenAPI 3.0")
                        .description("This is the version of an API created with MongoDB, reactive programming, " +
                                "and functional programming for customer maintenance.\n"
                                + "\nSome useful links:\n"
                                + "\n- [The repository in GitHub](" + applicationProperties.getGithub().getUrl() + ")"
                                + "\n- [The source API definition for Banking Customer]("
                                + applicationProperties.getGithub().getDefinition() + ")"
                        )
                        .termsOfService(applicationProperties.getTerms())
                        .contact(new Contact()
                                .name(applicationProperties.getContact().getName())
                                .email(applicationProperties.getContact().getEmail())
                                .url(applicationProperties.getContact().getUrl())
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
                                new Server().url(applicationProperties.getServers().getUrl()).
                                    description(applicationProperties.getServers().getDescription())
                        )
                );
    }
}
