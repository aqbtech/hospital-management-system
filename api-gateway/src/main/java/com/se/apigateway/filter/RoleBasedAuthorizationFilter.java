package com.se.apigateway.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class RoleBasedAuthorizationFilter extends AbstractGatewayFilterFactory<RoleBasedAuthorizationFilter.Config> {

    public RoleBasedAuthorizationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Check for user role in request header (set by AuthorizationFilter)
            String userRole = exchange.getRequest().getHeaders().getFirst("X-User-Role");
            
            if (userRole == null) {
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Missing role information");
            }
            
            // Check if the user's role is allowed
            if (!isRoleAuthorized(userRole, config.getAllowedRoles())) {
                return onError(exchange, HttpStatus.FORBIDDEN, "Insufficient permissions");
            }
            
            return chain.filter(exchange);
        };
    }

    private boolean isRoleAuthorized(String userRole, List<String> allowedRoles) {
        return allowedRoles.contains(userRole);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public static class Config {
        private List<String> allowedRoles;

        public Config() {
        }

        public Config(String... roles) {
            this.allowedRoles = Arrays.asList(roles);
        }

        public List<String> getAllowedRoles() {
            return allowedRoles;
        }

        public void setAllowedRoles(List<String> allowedRoles) {
            this.allowedRoles = allowedRoles;
        }
    }
}
