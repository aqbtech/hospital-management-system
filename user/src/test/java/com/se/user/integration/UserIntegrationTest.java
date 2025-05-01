package com.se.user.integration;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Optional;

import com.se.user.repository.DoctorProfileRepository;
import com.se.user.repository.PatientProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.user.dto.AuthRequest;
import com.se.user.dto.AuthResponse;
import com.se.user.dto.RegisterRequest;
import com.se.user.entity.PatientProfile;
import com.se.user.entity.Role;
import com.se.user.entity.User;
import com.se.user.repository.BlacklistedTokenRepository;
import com.se.user.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test") // Use test configuration
public class UserIntegrationTest {

    @Container
    @SuppressWarnings("resource")
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;
    @Autowired
    private DoctorProfileRepository doctorProfileRepository;
    @Autowired
    private PatientProfileRepository patientProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "Test@1234";
    private static final String TEST_PHONE = "+84912345678";
    private static final String TEST_FULLNAME = "Test User";
    private static final String ADMIN_EMAIL = "admin@example.com";
    private static final String ADMIN_PASSWORD = "Admin@1234";

    private String accessToken;
    private String refreshToken;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        // Clean databases
        blacklistedTokenRepository.deleteAll();
        doctorProfileRepository.deleteAll();
        patientProfileRepository.deleteAll();
        userRepository.deleteAll();

        // Create an admin user for tests that require admin privileges
        User adminUser = User.builder()
                .username("admin")
                .email(ADMIN_EMAIL)
                .passwordHash(passwordEncoder.encode(ADMIN_PASSWORD))
                .phone("+84987654321")
                .role(Role.ADMIN)
                .active(true)
                .createdAt(new Date())
                .build();
        userRepository.save(adminUser);
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test
//        blacklistedTokenRepository.deleteAll();
//        doctorProfileRepository.deleteAll();
//        patientProfileRepository.deleteAll();
//        userRepository.deleteAll();
    }

    @Test
    void testRegisterUser() throws Exception {
        // Create registration request
        RegisterRequest registerRequest = createRegisterRequest();

        // Send registration request
        MvcResult result = mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andExpect(jsonPath("$.refreshToken", notNullValue()))
                .andExpect(jsonPath("$.user.username", is(TEST_USERNAME)))
                .andExpect(jsonPath("$.user.email", is(TEST_EMAIL)))
                .andExpect(jsonPath("$.user.role", is("PATIENT")))
                .andReturn();

        // Extract tokens for future tests
        AuthResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), AuthResponse.class);
        accessToken = response.getAccessToken();
        refreshToken = response.getRefreshToken();

        // Verify user was added to the database
        Optional<User> user = userRepository.findByEmail(TEST_EMAIL);
        assertTrue(user.isPresent());
        assertEquals(TEST_EMAIL, user.get().getEmail());
        assertEquals(TEST_USERNAME, user.get().getUsername());
        assertTrue(passwordEncoder.matches(TEST_PASSWORD, user.get().getPasswordHash()));
        assertEquals(Role.PATIENT, user.get().getRole());
    }

    @Test
    void testRegisterUserWithInvalidPassword() throws Exception {
        // Create registration request with weak password
        RegisterRequest registerRequest = createRegisterRequest();
        registerRequest.setPassword("weak");

        // Send registration request (should fail validation)
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUserLogin() throws Exception {
        // First register a user
        registerTestUser();

        // Create login request
        AuthRequest loginRequest = AuthRequest.builder()
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .build();

        // Send login request
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andExpect(jsonPath("$.refreshToken", notNullValue()))
                .andExpect(jsonPath("$.user.username", is(TEST_USERNAME)))
                .andExpect(jsonPath("$.user.email", is(TEST_EMAIL)))
                .andReturn();

        AuthResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), AuthResponse.class);
        accessToken = response.getAccessToken();

        // Verify user's lastLogin was updated
        Optional<User> user = userRepository.findByEmail(TEST_EMAIL);
        assertNotNull(user.get().getLastLogin());
    }

    @Test
    void testLoginWithInvalidCredentials() throws Exception {
        // First register a user
        registerTestUser();

        // Create login request with wrong password
        AuthRequest loginRequest = AuthRequest.builder()
                .username(TEST_USERNAME)
                .password("WrongPassword123!")
                .build();

        // Send login request (should fail)
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetCurrentUser() throws Exception {
        // First register a user and login
        registerAndLoginTestUser();

        // Get current user with token
        mockMvc.perform(get("/users/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(TEST_USERNAME)))
                .andExpect(jsonPath("$.email", is(TEST_EMAIL)));
    }

    @Test
    void testRefreshToken() throws Exception {
        // First register a user and login to get tokens
        registerAndLoginTestUser();

        // Send refresh token request
        MvcResult result = mockMvc.perform(post("/auth/refresh-token")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken))
                .andExpect(status().isOk())
                .andReturn();

        // Verify new tokens are issued
        AuthResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), AuthResponse.class);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
        assertNotEquals(accessToken, response.getAccessToken());
        assertNotEquals(refreshToken, response.getRefreshToken());

        // Verify old refresh token is blacklisted
        assertTrue(blacklistedTokenRepository.existsByToken(refreshToken));
    }

    @Test
    void testLogout() throws Exception {
        // First register a user and login to get token
        registerAndLoginTestUser();

        // Send logout request
        mockMvc.perform(post("/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk());

        // Verify token is blacklisted
        assertTrue(blacklistedTokenRepository.existsByToken(accessToken));

        // Try to use the token again (should fail)
        mockMvc.perform(get("/users/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAllUsers() throws Exception {
        // First register a test user
        registerTestUser();

        // Admin should be able to see all users
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[*].email", hasItem(TEST_EMAIL)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetUserById() throws Exception {
        // First register a test user
        User testUser = registerTestUser();

        // Get user by ID
        mockMvc.perform(get("/users/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(TEST_EMAIL)))
                .andExpect(jsonPath("$.username", is(TEST_USERNAME)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateUserStatus() throws Exception {
        // First register a test user
        User testUser = registerTestUser();

        // Deactivate user
        mockMvc.perform(put("/users/" + testUser.getId() + "/status")
                        .param("active", "false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(false)));

        // Verify user is deactivated in the database
        Optional<User> updatedUser = userRepository.findById(testUser.getId());
        assertFalse(updatedUser.get().isEnabled());
    }

    @Test
    void testTokenBlacklisting() throws Exception {
        // First register a user and login to get token
        registerAndLoginTestUser();

        // Logout to blacklist the token
        mockMvc.perform(post("/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk());

        // Verify token is blacklisted
        assertTrue(blacklistedTokenRepository.existsByToken(accessToken));

        // Try to use the blacklisted token
        mockMvc.perform(get("/users/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isUnauthorized());

        // Verify a new token works
        AuthRequest loginRequest = AuthRequest.builder()
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .build();

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        AuthResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), AuthResponse.class);
        String newToken = response.getAccessToken();

        // Using new token should work
        mockMvc.perform(get("/users/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + newToken))
                .andExpect(status().isOk());
    }

    // Helper methods

    private RegisterRequest createRegisterRequest() {
        return RegisterRequest.builder()
                .username(TEST_USERNAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .phone(TEST_PHONE)
                .fullName(TEST_FULLNAME)
                .role(Role.PATIENT)
                .gender(PatientProfile.Gender.MALE)
                .dob(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 365 * 30)) // 30 years ago
                .address("123 Test Street")
                .insuranceNumber("INS12345")
                .emergencyContact("Emergency Contact")
                .build();
    }

    private User registerTestUser() throws Exception {
        RegisterRequest registerRequest = createRegisterRequest();
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());

        return userRepository.findByEmail(TEST_EMAIL).orElseThrow();
    }

    private void registerAndLoginTestUser() throws Exception {
        // Register user
        RegisterRequest registerRequest = createRegisterRequest();
        MvcResult registerResult = mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        AuthResponse registerResponse = objectMapper.readValue(
                registerResult.getResponse().getContentAsString(), AuthResponse.class);
        accessToken = registerResponse.getAccessToken();
        refreshToken = registerResponse.getRefreshToken();
    }
} 