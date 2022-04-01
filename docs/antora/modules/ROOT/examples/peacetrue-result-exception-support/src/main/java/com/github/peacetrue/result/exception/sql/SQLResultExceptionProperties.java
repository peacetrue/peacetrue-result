package com.github.peacetrue.result.exception.sql;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peace
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.result.exception.sql")
public class SQLResultExceptionProperties {

    private Map<String, String> messagePatterns = getDefaultMessagePatterns();

    private static Map<String, String> getDefaultMessagePatterns() {
        HashMap<String, String> messagePatterns = new HashMap<>(2);
        //com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException
        //Duplicate entry 'name' for key 'test_entity.UK_tkjut8jy3hkxamr8t5wkt91gt'
        messagePatterns.put("23000", "Duplicate entry '([^']+)' for key '([^']+)'");
        //org.h2.jdbc.JdbcSQLException
        //Unique index or primary key violation: "UK_TKJUT8JY3HKXAMR8T5WKT91GT_INDEX_F ON PUBLIC.TEST_ENTITY(NAME) VALUES ('name', 1)"
        messagePatterns.put("23505", "Unique index or primary key violation: \"([^ ]+) ON ([^.]+).([^(]+)\\(([^)]+)\\)");
        return messagePatterns;
    }

}
