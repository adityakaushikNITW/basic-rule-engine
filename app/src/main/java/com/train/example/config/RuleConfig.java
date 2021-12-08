package com.train.example.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.BeanResolver;

@Configuration
public class RuleConfig {

    @Bean
    public BeanResolver getBeanResolver(ApplicationContext ctx) {
        ConfigurableListableBeanFactory beanFactory = ((AnnotationConfigServletWebServerApplicationContext) ctx).getBeanFactory();
        return new BeanFactoryResolver(ctx);
    }
}
