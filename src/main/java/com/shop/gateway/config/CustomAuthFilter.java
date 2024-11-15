package com.shop.gateway.config;

import com.shop.gateway.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public CustomAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->
                exchange.getPrincipal()
                .flatMap(principal -> {
                    if (principal instanceof JwtAuthenticationToken) {
                        JwtAuthenticationToken auth = (JwtAuthenticationToken) principal;
                        Jwt jwt = (Jwt) auth.getCredentials();
                        String id = jwt.getClaim("preferred_username");
                        if (id != null) {
                            System.out.println(id);
                            kafkaProducerService.sendMessage("id", id);
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                            return exchange.getResponse().setComplete();
                        }
                    }
                    return chain.filter(exchange);
                });
    }


    public static class Config {
    }
}
