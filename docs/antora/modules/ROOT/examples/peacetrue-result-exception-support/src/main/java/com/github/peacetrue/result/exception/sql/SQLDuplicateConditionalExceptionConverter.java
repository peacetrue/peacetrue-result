package com.github.peacetrue.result.exception.sql;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.exception.SQLConditionalExceptionConverter;
import com.github.peacetrue.util.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 数据库字段重复异常，对应 {@link SQLException#getSQLState()} = 23000（MySQL）。
 *
 * @author peace
 **/
@Slf4j
public class SQLDuplicateConditionalExceptionConverter
        extends SQLConditionalExceptionConverter {

    /** 属性值重复 */
    public static final String SQL_STATE_DUPLICATE = "23000";
    private IndexParameterNameResolver indexParameterNameResolver;

    /** 获取重复响应结果编码 */
    public static String getDuplicateResultCode() {
        return resolveCode(SQL_STATE_DUPLICATE);
    }

    @Override
    public boolean supports(String sqlState) {
        return SQL_STATE_DUPLICATE.equals(sqlState);
    }

    @Nullable
    @Override
    protected Object resolveArgs(SQLException exception) {
        //com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException
        //Duplicate entry 'name' for key 'test_entity.UK_tkjut8jy3hkxamr8t5wkt91gt'
        String messagePattern = "Duplicate entry '(.+)' for key '(.+)\\.(.+)'";
        String[] values = RegexUtils.extractValue(exception.getMessage(), messagePattern);
        log.debug("got Result.messageTemplateArgs: {} -> {}", exception.getMessage(), Arrays.toString(values));
        String value = values[0], table = values[1], index = values[2];
        return new Parameter<>(indexParameterNameResolver.resolveParameterName(table, index), null, value);
    }

    @Autowired
    public void setIndexParameterNameResolver(IndexParameterNameResolver indexParameterNameResolver) {
        this.indexParameterNameResolver = indexParameterNameResolver;
    }
}
