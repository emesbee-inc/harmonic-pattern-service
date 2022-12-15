package com.emesbee.capm.harmonicpatternservice.service;

import com.emesbee.capm.harmonicpatternservice.entity.PatternNotification;
import com.emesbee.capm.harmonicpatternservice.entity.RequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.delegate.DatabaseDelegate;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
@Testcontainers
@AutoConfigureDataMongo
public class ServiceTest {
    private static final String baseUrl = "/api/v1/harmonic-pattern";
    private static final String queueName = "test_queue.fifo";
    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13.7")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");
    private static final DatabaseDelegate containerDelegate = new JdbcDatabaseDelegate(postgreSQLContainer, "");
    static RequestModel requestModel = new RequestModel();
    static PatternNotification patternNotification = new PatternNotification();
    private static UUID testUUID;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${emesbee.app.jwtSecret}")
    public String jwtSecret;

    @Autowired
    private HarmonicPatternService harmonicPatternService;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    public static void beforeAll() {
        testUUID = UUID.randomUUID();
        patternNotification.setId(UUID.randomUUID());
        patternNotification.setPatternname("ajay");
        List<PatternNotification> list = new ArrayList<>();
        requestModel.setMsgType("pattern.notification");
        requestModel.setData(list);

    }

    @Test
    void getMessage() {
        var data = harmonicPatternService.postMessage(requestModel);
        Assertions.assertEquals("200", String.valueOf(data.getStatusCodeValue()));
    }

    @Test
    void getMessage_Multi_Objects() {
        List<PatternNotification> list = new ArrayList<>();
        list.add(patternNotification);
        list.add(null);
        list.add(null);
        requestModel.setData(list);
        System.out.println(requestModel.toString());
        var data = harmonicPatternService.postMessage(requestModel);
        Assertions.assertEquals("200", String.valueOf(data.getStatusCodeValue()));
    }

    @Test
    void getMessage_Exception() {
        Exception e1 = new Exception();
        try {
            harmonicPatternService.postMessage(requestModel);
        } catch (Exception e) {
            Assertions.assertEquals(e.getMessage(), e1.getMessage());
        }

    }

}
