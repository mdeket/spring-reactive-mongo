package com.example.movieservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MovieServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MovieServiceApplication.class);
        app.run(args);
    }

}