package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

/**
 * {@link SQLException} 条件性异常转换器。
 * {@link SQLException} 需要根据 {@link SQLException#getSQLState()} 区分具体异常。
 *
 * @author peace
 */
@Slf4j
public abstract class SQLConditionalExceptionConverter
        extends BaseExceptionConverter<SQLException>
        implements ConditionalExceptionConverter {

    /**
     * 规范 SQL 异常下的响应结果编码，统一使用 "SQL_" + SQLState，例如：SQL_23000。
     *
     * @param sqlState sql 状态
     * @return SQL 响应结果编码
     */
    public static String resolveCode(String sqlState) {
        return "SQL_" + sqlState;
    }

    @Override
    public final boolean supports(Throwable exception) {
        return exception instanceof SQLException && supports(((SQLException) exception).getSQLState());
    }

    /**
     * 是否支持指定的 SQLState。
     *
     * @param sqlState SQLState
     * @return true 如果支持
     */
    protected abstract boolean supports(String sqlState);

    @Override
    public final Result convert(Throwable exception) {
        return super.standardConvert((SQLException) exception);
    }

    /**
     * 解析响应结果编码。
     *
     * @param exception 异常
     * @return 响应结果编码
     */
    protected String resolveCode(SQLException exception) {
        return resolveCode(exception.getSQLState());
    }

}
