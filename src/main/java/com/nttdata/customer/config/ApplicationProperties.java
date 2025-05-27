package com.nttdata.customer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * ApplicationProperties.
 * This class holds the configuration properties for the application.
 * It is used to map properties defined in application.properties or application.yml.
 *
 * @author Joseph Magallanes
 * @since 2025-05-27
 */
@Component
@ConfigurationProperties(prefix = "openapi.info")
@Getter
@Setter
public class ApplicationProperties {

    private String githubUrl;
    private String terms;
    private Contact contact;
    private Servers servers;

    /**
     * Returns the contact information for the API.
     *
     * @return Contact object containing name, email, and URL.
     */
    @Getter
    @Setter
    public static class Contact {
        private String name;
        private String email;
        private String url;
    }

    /**
     * Returns the server information for the API.
     *
     * @return Servers object containing URL and description.
     */
    @Getter
    @Setter
    public static class Servers {
        private String url;
        private String description;
    }
}
