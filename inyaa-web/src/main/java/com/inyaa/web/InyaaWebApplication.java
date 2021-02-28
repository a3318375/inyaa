package com.inyaa.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InyaaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(InyaaWebApplication.class, args);
    }

//    @Bean
//    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
//        return new JPAQueryFactory(entityManager);
//    }

}
