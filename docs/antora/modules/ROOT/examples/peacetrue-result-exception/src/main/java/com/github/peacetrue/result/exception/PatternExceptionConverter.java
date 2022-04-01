package com.github.peacetrue.result.exception;

import com.github.peacetrue.util.RegexUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 从 {@link Exception#getMessage()} 中提取描述模板参数。
 *
 * @author peace
 **/
public class PatternExceptionConverter<T extends Throwable> extends AbstractExceptionConverter<T> {

    /** 正则表达式规则 */
    private List<String> patterns;

    public PatternExceptionConverter() {
        this(Collections.emptyList());
    }

    public PatternExceptionConverter(List<String> patterns) {
        this.patterns = Objects.requireNonNull(patterns);
    }

    @Nullable
    @Override
    protected Object resolveArgs(T exception) {
        return patterns.stream()
                .map(item -> RegexUtils.extractValue(item, exception.getMessage()))
                .filter(item -> !ObjectUtils.isEmpty(item))
                .findFirst().orElse(null);
    }
}
