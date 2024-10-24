package com.noa.pos.imp.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class SecurityProperties {
    private final String TOKEN_PREFIX;
    private final String[] ALLOWED_METHODS, ALLOWED_HEADERS, ALLOWED_ORIGINS, EXPOSED_HEADERS;

    @Autowired
    public SecurityProperties(Environment env) {
        var allowedMethods = env.getProperty("security.allowed.methods");
        this.ALLOWED_METHODS = Objects.requireNonNullElse(allowedMethods, "GET;POST;PUT;DELETE;OPTIONS").split(";");
        var allowedHeaders = env.getProperty("security.allowed.headers");
        this.ALLOWED_HEADERS = Objects.requireNonNullElse(allowedHeaders, "Authorization;Requestor-Type;Content-Type").split(";");
        var allowedOrigins = env.getProperty("security.allowed.origins");
        this.ALLOWED_ORIGINS = Objects.requireNonNullElse(allowedOrigins, "*").split(";");
        var exposedHeaders = env.getProperty("security.exposed.headers");
        this.EXPOSED_HEADERS = Objects.requireNonNullElse(exposedHeaders, "TokenR").split(";");
        var tokenPrefix = env.getProperty("security.token.prefix");
        this.TOKEN_PREFIX = Objects.requireNonNullElse(tokenPrefix, "Bearer");
    }
}
