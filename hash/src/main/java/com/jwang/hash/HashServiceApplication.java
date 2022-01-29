package com.jwang.hash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableDiscoveryClient
//@EnableEurekaClient
public class HashServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HashServiceApplication.class, args);
    }
}
