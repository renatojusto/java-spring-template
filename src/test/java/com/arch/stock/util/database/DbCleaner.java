package com.arch.stock.util.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.MetaDataAccessException;

public interface DbCleaner {

    void truncateAllTables(JdbcTemplate jdbcTemplate) throws MetaDataAccessException;

}
