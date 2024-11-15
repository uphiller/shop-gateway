package com.shop.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/public/**").permitAll()  // 인증 없이 접근 가능한 경로
                        .anyExchange().authenticated()           // 그 외 경로는 인증 필요
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt()
                );
        return http.build();
    }
}
