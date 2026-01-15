package com.mycompany.questionsgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class QuestionsGeneratorApiApplication {

    static void main(String[] args) {
        SpringApplication.run(QuestionsGeneratorApiApplication.class, args);
    }

}
