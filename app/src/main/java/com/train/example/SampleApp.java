package com.train.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.expression.BeanResolver;

import java.util.Arrays;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@Slf4j
@ComponentScan
@EntityScan
@EnableMongoRepositories
public class SampleApp {
    public static void main(String[] args) {
        SpringApplication.run(SampleApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            log.debug("Inspection of beans provided by Spring Boot");
            String[] beans = ctx.getBeanDefinitionNames();
            Arrays.sort(beans);
            Arrays.stream(beans).forEach(log::debug);
        };
    }
}
