package com.arch.stock.util.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import static org.springframework.jdbc.support.JdbcUtils.extractDatabaseMetaData;

@Component("DbCleanerMySQL")
public class DbCleanerMySQL implements DbCleaner {

    private static List<String> TABLES_TO_IGNORE = Arrays.asList("databasechangelog",
                                                                 "databasechangeloglock",
                                                                 "sys_config",
                                                                 "supplier");

    public void truncateAllTables(JdbcTemplate jdbcTemplate) throws MetaDataAccessException {
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
}
