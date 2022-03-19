package com.github.peacetrue.result.exception.sql;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultUtils;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import com.github.peacetrue.result.exception.ConditionalExceptionConverter;
import com.github.peacetrue.util.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.*;

/**
 * {@link SQLException} 转换器。
 *
 * @author peace
 */
@Slf4j
public class SQLExceptionConverter implements ConditionalExceptionConverter {

    /** SQL 状态对应的描述模板 */
    private final Map<String, String> messagePatterns;
    private ResultMessageBuilder resultMessageBuilder;

    public SQLExceptionConverter() {
        this(Collections.emptyMap());
    }

    public SQLExceptionConverter(Map<String, String> messagePatterns) {
        this.messagePatterns = new HashMap<>(Objects.requireNonNull(messagePatterns));
    }

    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof SQLException
                && messagePatterns.containsKey(((SQLException) throwable).getSQLState());
    }

    @Override
    public Result convert(Throwable throwable) {
        return convert((SQLException) throwable);
    }

    private Result convert(SQLException exception) {
        Object[] args = resolveArgs(exception);
        String state = exception.getSQLState();
        String code = "SQL_" + state;
        log.debug("got Result.code: {}", code);
        String message = resultMessageBuilder.build(code, args);
        log.debug("got Result.message: {}", message);
        //目前提示的是列名而不是参数名，这里需要有一个转换处理
        if (args != null) code += "." + ("23000".equals(state) ? args[0] : args[3]);
        return ResultUtils.build(code, message, args);
    }

    @Nullable
    private Object[] resolveArgs(SQLException exception) {
        String messagePattern = messagePatterns.get(exception.getSQLState());
        log.debug("got message pattern for SQLState: {} -> {}", exception.getSQLState(), messagePattern);
        if (messagePattern == null) return null;
        String[] values = RegexUtils.extractValue(messagePattern, exception.getMessage());
        log.debug("got Result.messageTemplateArgs: {} -> {}", exception.getMessage(), Arrays.toString(values));
        return values;
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }

    public void addMessagePattern(String sqlState, String messagePattern) {
        this.messagePatterns.put(sqlState, messagePattern);
    }
}
