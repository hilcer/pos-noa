package com.noa.pos.service.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Optional;

@Configuration
@Data
public class RedisProperties {
    private final String redisPassword;
    private final Integer redisPort;
    private final String redisHost;
    private final String redisUserName;

    public RedisProperties(Environment env) {
        String host = env.getProperty("spring.data.redis.host", String.class),
                password = env.getProperty("spring.data.redis.password"),
                userName = env.getProperty("spring.data.redis.username", String.class);
        Integer port = env.getProperty("spring.data.redis.port", Integer.class);

        this.redisHost = Optional.ofNullable(host).orElse("localhost");
        this.redisPassword = Optional.ofNullable(password).orElse("my-password");
        this.redisPort = Optional.ofNullable(port).orElse(6379);
        this.redisUserName = Optional.ofNullable(userName).orElse("default");
    }
}