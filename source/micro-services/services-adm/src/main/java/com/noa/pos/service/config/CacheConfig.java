package com.noa.pos.service.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.LoggingCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

    public static final String CACHE_DOMAIN = "domain";
    public static final String CACHE_DOMAIN_GROUP = "domainGroup";
    public static final String CACHE_DOMAIN_GROUP_LIST = "domainGroupList";
    public static final String CACHE_DOMAIN_GROUP_USER_LIST = "domainGroupUserList";
    public static final String CACHE_COMPANY = "company";
    public static final String CACHE_USER = "user";
    public static final String CACHE_SUCURSAL = "sucursal";

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisProperties.getRedisHost()); // Set the Redis server host
        redisConfig.setPassword(redisProperties.getRedisPassword()); // Set the Redis password
        redisConfig.setPort(redisProperties.getRedisPort()); // Set the Redis server port
        redisConfig.setUsername(redisProperties.getRedisUserName()); // Set the Redis UserName

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(ClientOptions.builder()
                        .socketOptions(SocketOptions.builder()
                                .connectTimeout(Duration.ofSeconds(10)) // Set the connection timeout here
                                .build())
                        .build())
                .build();

        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put(CACHE_DOMAIN, createConfig(10, ChronoUnit.MINUTES));
        redisCacheConfigurationMap.put(CACHE_COMPANY, createConfig(10, ChronoUnit.MINUTES));
        redisCacheConfigurationMap.put(CACHE_USER, createConfig(10, ChronoUnit.MINUTES));
        redisCacheConfigurationMap.put(CACHE_SUCURSAL, createConfig(10, ChronoUnit.MINUTES));

        return RedisCacheManager.builder(redisConnectionFactory)
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
    }

    private static RedisCacheConfiguration createConfig(long time, ChronoUnit temporalUnit) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.of(time, temporalUnit));
    }

    @Override //<Avoiding Errors With Non-redis available>
    public CacheErrorHandler errorHandler() {
        return new LoggingCacheErrorHandler();
    }
}
