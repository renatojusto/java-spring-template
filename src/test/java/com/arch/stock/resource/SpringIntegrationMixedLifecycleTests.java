package com.arch.stock.resource;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class SpringIntegrationMixedLifecycleTests extends SpringIntegrationTests {

    @Test
    void test() {
        assertTrue(mysqlContainer.isRunning());
    }

}
