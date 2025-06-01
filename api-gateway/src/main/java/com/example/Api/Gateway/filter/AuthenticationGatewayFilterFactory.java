package com.example.Api.Gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    public AuthenticationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            List<String> authHeaders = exchange.getRequest().getHeaders().get("Authorization");

            if (authHeaders == null || authHeaders.isEmpty()) {
                log.error("Authorization header is missing");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authorizedHeader = authHeaders.get(0);
            String[] parts = authorizedHeader.split("Bearer");
            if (parts.length < 2) {
                log.error("Invalid Authorization header format");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = parts[1].trim();

            return chain.filter(exchange);
        };
    }


    public static class Config{
        private boolean isEnabled;
    }
}
