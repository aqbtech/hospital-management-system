package com.se.apigateway.filter;

import com.se.apigateway.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
@Component
public class AppointmentFilter extends AbstractGatewayFilterFactory<AppointmentFilter.Config> {
	private final JwtUtil jwtUtil;
	public AppointmentFilter(JwtUtil jwtUtil) {
		super(AppointmentFilter.Config.class);
		this.jwtUtil = jwtUtil;
	}

	@Override
	public GatewayFilter apply(AppointmentFilter.Config config) {
		return (exchange, chain) -> {
			// Check for user role in request header (set by AuthorizationFilter)
			String userid = exchange.getRequest().getHeaders().getFirst("X-User-Id");

			ServerHttpRequest request = exchange.getRequest();
			// Add user name to request param
			ServerHttpRequest modifiedRequest = request.mutate()
					.uri(addQueryParam(request.getURI(), "patientId", userid))
					.build();

			// Continue with modified request
			return chain.filter(exchange.mutate().request(modifiedRequest).build());
		};
	}
	private URI addQueryParam(URI uri, String key, String value) {
		try {
			String query = uri.getQuery();
			String newQuery = (query == null || query.isEmpty())
					? key + "=" + value
					: query + "&" + key + "=" + value;

			return new URI(
					uri.getScheme(),
					uri.getAuthority(),
					uri.getPath(),
					newQuery,
					uri.getFragment()
			);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Invalid URI while adding query param", e);
		}
	}

	private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status, String message) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}


	public static class Config {
		// Configuration properties if needed
	}
}
