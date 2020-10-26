package com.arch.stock.util.container;

import org.testcontainers.containers.JdbcDatabaseContainer;

public class StockContainerFactory {

    public static JdbcDatabaseContainer getInstance() {
        return StockPostgreSQLContainer.getInstance();
    }

}
