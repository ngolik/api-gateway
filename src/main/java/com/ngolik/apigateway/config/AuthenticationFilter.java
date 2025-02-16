package com.ngolik.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .flatMap(authentication -> {
                if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
                    String userId = jwt.getClaimAsString("sub");
                    String roles = jwt.getClaimAsStringList("roles") != null ? String.join(",", jwt.getClaimAsStringList("roles"))
                                                                             : null;

                    ServerHttpRequest request = exchange.getRequest().mutate()
                        .header("X-User-Id", userId)
                        .header("X-User-Roles", roles)
                        .build();

                    request.getHeaders().forEach((key, value) -> {
                        System.out.println("Header: " + key + " Value: " + value);
                    });

                    return chain.filter(exchange.mutate().request(request).build());
                }
                return chain.filter(exchange);
            });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}