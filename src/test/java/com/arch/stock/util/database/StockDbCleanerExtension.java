package com.arch.stock.util.database;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.test.context.junit.jupiter.SpringExtension.getApplicationContext;

@Component
public class StockDbCleanerExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext extensionContext) throws Exception {
        DbCleaner dbCleaner = getApplicationContext(extensionContext).getBean(DbCleanerPostgreSQL.class);
        JdbcTemplate jdbcTemplate = getApplicationContext(extensionContext).getBean(JdbcTemplate.class);
        dbCleaner.truncateAllTables(jdbcTemplate);
    }
}
