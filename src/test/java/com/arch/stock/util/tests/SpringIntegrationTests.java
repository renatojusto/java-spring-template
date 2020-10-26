package com.arch.stock.util.tests;

import com.arch.stock.util.container.StockContainerFactory;
import com.arch.stock.util.database.StockDbCleanerExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({StockDbCleanerExtension.class, SpringExtension.class})
@Testcontainers
public class SpringIntegrationTests {

    @Container
    static JdbcDatabaseContainer container = StockContainerFactory.getInstance();

    @DynamicPropertySource
    static void setContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

}
