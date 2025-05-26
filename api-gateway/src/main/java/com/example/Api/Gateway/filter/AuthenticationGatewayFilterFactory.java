package com.example.Api.Gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    public AuthenticationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->{
            String authorizedHeader = exchange.getRequest().getHeaders().get("Authorization")
                    .stream().findFirst().orElse(null);
            String token = authorizedHeader.split("Bearer")[1];
            return chain.filter(exchange);
        };
    }

    public static class Config{
        private boolean isEnabled;
    }
}
