package com.arch.stock.resource;

import com.arch.stock.util.StockMySQLContainer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ContextConfiguration(initializers = {SpringIntegrationTests.Initializer.class})
@Testcontainers
public class SpringIntegrationTests {

    @Container
    public static MySQLContainer mysqlContainer = StockMySQLContainer.getInstance();

    static class Initializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=" + mysqlContainer.getJdbcUrl(),
                "spring.datasource.username=" + mysqlContainer.getUsername(),
                "spring.datasource.password=" + mysqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
