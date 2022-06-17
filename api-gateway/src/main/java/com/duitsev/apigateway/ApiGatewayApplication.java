package com.duitsev.apigateway;

import com.duitsev.apigateway.config.ApiGatewayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(ApiGatewayProperties.class)
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, ApiGatewayProperties apiGatewayProperties) {
        return builder.routes()
                .route(p -> p
                        .path("/savings/a/balance")
                        .filters(f -> f.setPath("/balance"))
                        .uri(apiGatewayProperties.getaUrl()))
                .route(p -> p
                        .path("/savings/b/balance")
                        .filters(f -> f.setPath("/balance"))
                        .uri(apiGatewayProperties.getbUrl()))
                .build();
    }

}

