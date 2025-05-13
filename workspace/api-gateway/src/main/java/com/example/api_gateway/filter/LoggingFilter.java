package com.example.api_gateway.filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class LoggingFilter implements org.springframework.cloud.gateway.filter.GlobalFilter {
public class LoggingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Path requested: " + exchange.getRequest().getPath());

        // Ajouter un header personnalisé à la requête envoyée au service cible
        return chain.filter(
                exchange.mutate()
                        .request(
                                exchange.getRequest()
                                        .mutate()
                                        .header("X-Request-Time", LocalDateTime.now().toString())
                                        .build()
                        )
                        .build()
        );
    }
}
