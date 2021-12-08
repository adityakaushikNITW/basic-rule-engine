package com.train.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
public class AWSConfig {

    @Bean
    public AmazonDynamoDB getAmazonDynamoDBClient() {
        AmazonDynamoDB client =
                AmazonDynamoDBClientBuilder.standard()
                        .withEndpointConfiguration(
                                new AwsClientBuilder
                                        .EndpointConfiguration("http://host.docker.internal:8000"
                                        , "us-west-2"))
                        .build();
        return client;
    }
}
