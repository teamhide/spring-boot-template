package com.teamhide.template.support;

import groovy.util.logging.Slf4j;
import io.micrometer.common.lang.NonNull;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

@Slf4j
public class IntegrationTestExecutionListener extends AbstractTestExecutionListener {
    private static final Set<String> excludedTableNames =
            new HashSet<>(
                    List.of(
                            "flyway_schema_history",
                            "BATCH_JOB_EXECUTION",
                            "BATCH_JOB_EXECUTION_CONTEXT",
                            "BATCH_JOB_EXECUTION_PARAMS",
                            "BATCH_JOB_EXECUTION_SEQ",
                            "BATCH_JOB_INSTANCE",
                            "BATCH_JOB_SEQ",
                            "BATCH_STEP_EXECUTION",
                            "BATCH_STEP_EXECUTION_CONTEXT",
                            "BATCH_STEP_EXECUTION_SEQ"));

    @Override
    public void afterTestMethod(final TestContext testContext) throws Exception {
        JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
        List<String> tableNames = getAllTableNames(jdbcTemplate);
        truncateAllTables(tableNames, jdbcTemplate);
    }

    private void truncateAllTables(
            @NonNull final List<String> tableNames, @NonNull final JdbcTemplate jdbcTemplate) {
        tableNames.forEach(
                table -> {
                    jdbcTemplate.execute("TRUNCATE TABLE " + table);
                });
    }

    private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
        return testContext.getApplicationContext().getBean(JdbcTemplate.class);
    }

    private List<String> getAllTableNames(final JdbcTemplate jdbcTemplate) throws SQLException {
        List<String> tableNames = new ArrayList<>();

        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[] {"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                if (excludedTableNames.contains(tableName)) {
                    continue;
                }
                tableNames.add(tableName);
            }
        } catch (NullPointerException e) {
            throw e;
        }
        return tableNames;
    }
}
