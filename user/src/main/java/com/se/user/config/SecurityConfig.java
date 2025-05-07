package com.se.user.config;

import static com.se.user.entity.Role.ADMIN;
import static com.se.user.entity.Role.DOCTOR;
import static com.se.user.entity.Role.NURSE;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.se.user.security.JwtAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
//    @Value("${security.cors.allowed-origins}")
//    private String allowedOrigin;
//    @Value("${security.cors.allowed-headers}")
//    private String allowedHeader;
//    @Value("${security.cors.allowed-methods}")
//    private String allowedMethod;
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of(allowedOrigin));
//        configuration.setAllowedMethods(Arrays.asList(allowedMethod.split(",")));
//        configuration.setAllowedHeaders(Arrays.asList(allowedHeader.split(",")));
//        configuration.setExposedHeaders(List.of("x-auth-token"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final String[] publicUrls = {
                "/auth/**",
                "/api/v1/auth/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html",
                "/error",
                "/"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(publicUrls).permitAll()
                        .requestMatchers("/api/v1/users/**").authenticated()
                        .requestMatchers(GET, "/api/v1/users/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), NURSE.name())
                        .requestMatchers(POST, "/api/v1/users/**").hasAnyRole(ADMIN.name())
                        .requestMatchers(PUT, "/api/v1/users/**").hasAnyRole(ADMIN.name())
                        .requestMatchers(DELETE, "/api/v1/users/**").hasAnyRole(ADMIN.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .headers(headers -> headers.frameOptions(frameOption -> frameOption.disable()));
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())
//               ;);
        return http.build();
    }
}