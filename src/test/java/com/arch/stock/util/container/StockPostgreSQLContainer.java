package com.arch.stock.util.container;

import org.testcontainers.containers.PostgreSQLContainer;

public class StockPostgreSQLContainer extends PostgreSQLContainer<StockPostgreSQLContainer> {

    private static final String IMAGE_VERSION = "postgres:12.3-alpine";
    private static final String DATABASE_NAME = "stock-it-tests";
    private static final String POSTGRES_USER = "archstock";
    private static final String POSTGRES_PASSWORD = "archstock";

    private static StockPostgreSQLContainer container;

    private StockPostgreSQLContainer() {
        super(IMAGE_VERSION);
    }

    public static StockPostgreSQLContainer getInstance() {
        if (container == null) {
            container = new StockPostgreSQLContainer()
                .withDatabaseName(DATABASE_NAME)
                .withUsername(POSTGRES_USER)
                .withPassword(POSTGRES_PASSWORD)
                .withEnv("TZ", "GMT");
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
