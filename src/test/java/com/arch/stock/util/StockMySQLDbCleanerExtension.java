package com.arch.stock.util;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.MetaDataAccessException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import static org.springframework.jdbc.support.JdbcUtils.extractDatabaseMetaData;
import static org.springframework.test.context.junit.jupiter.SpringExtension.getApplicationContext;

public class StockMySQLDbCleanerExtension implements BeforeEachCallback {

    private static List<String> TABLES_TO_IGNORE = Arrays.asList("databasechangelog",
                                                                 "databasechangeloglock",
                                                                 "sys_config",
                                                                 "supplier");

    private static void truncateAllTables(JdbcTemplate jdbcTemplate) throws MetaDataAccessException {
        DataSource dataSource = jdbcTemplate.getDataSource();

        extractDatabaseMetaData(dataSource, (databaseMetaData) -> {
            ResultSet resultSet = databaseMetaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (resultSet.next()) {
                String tableName = resultSet.getString(3);
                if (!TABLES_TO_IGNORE.contains(tableName)) {
                    jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
                    jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
                    jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
                }
            }

            return null;
        });
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws MetaDataAccessException {
        JdbcTemplate jdbcTemplate = getApplicationContext(extensionContext).getBean(JdbcTemplate.class);
        truncateAllTables(jdbcTemplate);
    }

}
