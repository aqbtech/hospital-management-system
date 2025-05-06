package com.se.apigateway.config;

import com.se.apigateway.filter.GlobalAuthenticationFilter;
import com.se.apigateway.filter.RoleBasedAuthorizationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    private final RoleBasedAuthorizationFilter roleBasedAuthorizationFilter;
    private final GlobalAuthenticationFilter globalAuthenticationFilter;

    public RouteConfig(RoleBasedAuthorizationFilter roleBasedAuthorizationFilter, 
                       GlobalAuthenticationFilter globalAuthenticationFilter) {
        this.roleBasedAuthorizationFilter = roleBasedAuthorizationFilter;
        this.globalAuthenticationFilter = globalAuthenticationFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Authentication routes (public)
                .route("auth-login", r -> r
                        .path("/api/v1/auth/login")
                        .filters(s -> s.stripPrefix(2))
                        .uri("lb://USER-SERVICE"))
                .route("auth-register", r -> r
                        .path("/api/v1/auth/register")
                        .filters(s -> s.stripPrefix(2))
                        .uri("lb://USER-SERVICE"))
                .route("auth-register", r -> r
                        .path("/api/v1/auth/signup")
                        .filters(s -> s.stripPrefix(2))
                        .uri("lb://USER-SERVICE"))
                .route("auth-refresh-token", r -> r
                        .path("/api/v1/auth/refresh-token")
                        .filters(s -> s.stripPrefix(2))
                        .uri("lb://USER-SERVICE"))
                .route("auth-logout", r -> r
                        .path("/api/v1/auth/logout")
                        .filters(s -> s.stripPrefix(2))
                        .uri("lb://USER-SERVICE"))

                // User routes
                .route("users", r -> r
                        .path("/api/v1/users/**")
                        .filters(f -> f
                                .filter(globalAuthenticationFilter.apply(new GlobalAuthenticationFilter.Config()))
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "DOCTOR", "NURSE", "PATIENT")))
                                .stripPrefix(2))
                        .uri("lb://USER-SERVICE"))

                // Patient Management routes
                .route("update-medical-record", r -> r
                        .path("/api/v1/update-medical-record")
                        .filters(f -> f
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "DOCTOR"))))
                        .uri("lb://MEDICAL-RECORD-SERVICE"))
                .route("register-patients", r -> r
                        .path("/api/v1/register-patients")
                        .filters(f -> f
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "NURSE"))))
                        .uri("lb://USER-SERVICE"))
                .route("view-medicine-history", r -> r
                        .path("/api/v1/view-medicine-hisory")
                        .filters(f -> f
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "DOCTOR", "NURSE", "PATIENT"))))
                        .uri("lb://MEDICAL-RECORD-SERVICE"))
                
                // Appointment routes
                .route("appointment-status", r -> r
                        .path("/appointment/appointment-status")
                        .filters(f -> f
                                .filter(globalAuthenticationFilter.apply(new GlobalAuthenticationFilter.Config()))
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "DOCTOR", "NURSE", "PATIENT"))))
                        .uri("lb://APPOINTMENT-SERVICE"))
                .route("appointment-doctor-status", r -> r
                        .path("/appointment/appointment-doctor-status")
                        .filters(f -> f
                                .filter(globalAuthenticationFilter.apply(new GlobalAuthenticationFilter.Config()))
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "DOCTOR", "NURSE", "PATIENT"))))
                        .uri("lb://APPOINTMENT-SERVICE"))
                .route("book-appointment", r -> r
                        .path("/appointment/book-appointment")
                        .filters(f -> f
                                .filter(globalAuthenticationFilter.apply(new GlobalAuthenticationFilter.Config()))
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "DOCTOR", "NURSE", "PATIENT"))))
                        .uri("lb://APPOINTMENT-SERVICE"))
                .route("update-appointment", r -> r
                        .path("/appointment/update-appointment")
                        .filters(f -> f
                                .filter(globalAuthenticationFilter.apply(new GlobalAuthenticationFilter.Config()))
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "DOCTOR", "NURSE"))))
                        .uri("lb://APPOINTMENT-SERVICE"))
                
                // Payment routes
                .route("generate-invoice", r -> r
                        .path("/api/v1/generate-invoice")
                        .filters(f -> f
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "ACCOUNTANT"))))
                        .uri("lb://BILLING-SERVICE"))
                .route("payments", r -> r
                        .path("/api/v1/payments")
                        .filters(f -> f
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "ACCOUNTANT", "PATIENT"))))
                        .uri("lb://BILLING-SERVICE"))
                .route("update-billing", r -> r
                        .path("/api/v1/update-billing")
                        .filters(f -> f
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "ACCOUNTANT"))))
                        .uri("lb://BILLING-SERVICE"))
                
                // Admin routes
                .route("admin-profile", r -> r
                        .path("/api/v1/admin/profile")
                        .filters(f -> f
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN"))))
                        .uri("lb://USER-SERVICE"))
                
                // Doctor routes
                .route("doctor-profile", r -> r
                        .path("/api/v1/doctor/profile")
                        .filters(f -> f
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "DOCTOR"))))
                        .uri("lb://USER-SERVICE"))
                
                // Patient routes
                .route("patient-profile", r -> r
                        .path("/api/v1/patient/profile")
                        .filters(f -> f
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "DOCTOR", "NURSE", "PATIENT"))))
                        .uri("lb://USER-SERVICE"))
                
                // Prescription routes
                .route("prescriptions", r -> r
                        .path("/api/v1/prescriptions/**")
                        .filters(f -> f
                                .filter(roleBasedAuthorizationFilter.apply(
                                        new RoleBasedAuthorizationFilter.Config("ADMIN", "DOCTOR", "NURSE", "PATIENT"))))
                        .uri("lb://PRESCRIPTION-SERVICE"))
                
                // Legacy/Compatibility routes - to ensure backward compatibility
                .route("legacy-routes", r -> r
                        .path("/api/v1/users/**")
                        .uri("lb://USER-SERVICE"))
                .route("legacy-medical-records", r -> r
                        .path("/api/v1/medical-records/**")
                        .uri("lb://MEDICAL-RECORD-SERVICE"))
                .route("legacy-appointments", r -> r
                        .path("/api/v1/appointments/**")
                        .uri("lb://APPOINTMENT-SERVICE"))
                .route("legacy-billing", r -> r
                        .path("/api/v1/billing/**")
                        .uri("lb://BILLING-SERVICE"))
                
                .build();
    }
}
