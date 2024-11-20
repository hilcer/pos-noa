package com.noa.pos.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = {"com.noa.pos.model.repository"})
@EntityScan(basePackages = {"com.noa.pos.model.entity"})
@SpringBootApplication(scanBasePackages = {"com.noa.pos"})
public class ApiAdmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAdmApplication.class, args);
    }

}