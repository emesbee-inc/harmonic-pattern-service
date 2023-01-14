package com.emesbee.capm.harmonicpatternservice.security;


import com.emesbee.capm.harmonicpatternservice.entity.RequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.delegate.DatabaseDelegate;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@ExtendWith(SpringExtension.class)
public class SecurityTest {
    private static final String baseUrl = "/api/v1/harmonic-pattern";
    private static final String queueName = "test_queue.fifo";
    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13.7")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");
    private static final DatabaseDelegate containerDelegate = new JdbcDatabaseDelegate(postgreSQLContainer, "");
    private static UUID testUUID;
    private final ObjectMapper objectMapper = new ObjectMapper();
    static RequestModel requestModel = new RequestModel();
    @Value("${emesbee.app.jwtSecret}")
    public String jwtSecret;
    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


    @Test
    void expiredToken() throws Exception {

        final String url = baseUrl + "/post-harmonic-data";

        final var token = generateExpiredToken(testUUID);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStringFromFile("src/test/resources/requests/PostMessage.json", RequestModel.class))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void invalidToken() throws Exception {

        final var url = baseUrl + "/post-harmonic-data";

        final var token = generateInvalidToken(testUUID);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStringFromFile("src/test/resources/requests/PostMessage.json", RequestModel.class))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void noBearerInHeader() throws Exception {

        final var url = baseUrl + "/post-harmonic-data";

        final var token = generateInvalidToken(testUUID);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStringFromFile("src/test/resources/requests/PostMessage.json", RequestModel.class))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void malformedJwt() throws Exception {

        final var url = baseUrl + "/post-harmonic-data";

        final var token = generateMalformedToken().replace('.', 'z');

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStringFromFile("src/test/resources/requests/PostMessage.json", RequestModel.class))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void unsignedJwt() throws Exception {

        final var url = baseUrl + "/post-harmonic-data";

        final var token = generateUnsignedToken(testUUID) + "." + "123" + ".";

        mockMvc.perform(MockMvcRequestBuilders.put(url)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    private String generateExpiredToken(UUID userId) {
        return Jwts.builder()
                .setClaims(Map.of(
                        "Authorities", List.of("ROLE_SCOUT"),
                        "sub", String.valueOf(userId),
                        "iat", Instant.now().getEpochSecond(),
                        "exp", Instant.now().minusMillis(10000000).getEpochSecond(),
                        "username", "testuser"))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private String generateInvalidToken(UUID userId) {
        return Jwts.builder()
                .setClaims(Map.of(
                        "Authorities", List.of("ROLE_SCOUT"),
                        "sub", String.valueOf(userId),
                        "iat", Instant.now().getEpochSecond(),
                        "exp", Instant.now().plusMillis(10000000).getEpochSecond(),
                        "username", "testuser"))
                .signWith(SignatureAlgorithm.HS512, "ThisIsAFakeToken")
                .compact();
    }

    private String generateMalformedToken() {
        return Jwts.builder()
                .setPayload("Ope")
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private String generateUnsignedToken(UUID userId) {
        return Jwts.builder()
                .setClaims(Map.of(
                        "Authorities", List.of("ROLE_SCOUT"),
                        "sub", String.valueOf(userId),
                        "iat", Instant.now().getEpochSecond(),
                        "exp", Instant.now().plusMillis(10000000).getEpochSecond(),
                        "username", "testuser"))
                .compact();
    }

    private String jsonStringFromFile(String path, Class serialization) throws IOException {

        var objectMapper = new ObjectMapper();

        var mapped = objectMapper.readValue(new File(path), serialization);

        return objectMapper.writeValueAsString(mapped);
    }


    private Object jsonToClass(String path, Class serialization) throws IOException {

        return objectMapper.readValue(new File(path), serialization);
    }
}
