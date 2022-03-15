package com.github.peacetrue.result.exception;

import com.github.peacetrue.beans.properties.code.CodeAware;
import com.github.peacetrue.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 异常转换器服务实现
 *
 * @author peace
 */
@Slf4j
public class ExceptionConvertServiceImpl implements ExceptionConvertService {

    private Map<Class<Throwable>, ExceptionConverter<Throwable>> exceptionConverters = Collections.emptyMap();
    private List<ConditionalExceptionConverter> conditionalExceptionConverters = Collections.emptyList();
    private FallbackExceptionConverter fallbackExceptionConverter;
    private ResultCodeClassifier resultCodeClassifier = ResultCodeClassifier.DEFAULT;

    @Override
    public Result convert(Throwable throwable) {
        Result result = mapConvert(throwable)
                .orElseGet(() -> conditionalConvert(throwable)
                        .orElseGet(() -> fallbackConvert(throwable)));
        classifyResultCode(result);
        return result;
    }

    private Optional<Result> mapConvert(Throwable throwable) {
        return Optional.ofNullable(exceptionConverters.get(throwable.getClass()))
                .map(item -> item.convert(throwable));
    }

    private Optional<Result> conditionalConvert(Throwable throwable) {
        return conditionalExceptionConverters
                .stream().filter(item -> item.supports(throwable))
                .findFirst().map(item -> item.convert(throwable));
    }

    private Result fallbackConvert(Throwable throwable) {
        return fallbackExceptionConverter.convert(throwable);
    }

    private void classifyResultCode(Result result) {
        if (resultCodeClassifier != null && result instanceof CodeAware) {
            String code = result.getCode();
            String superCode = resultCodeClassifier.classifyResultCode(code);
            ((CodeAware) result).setCode(superCode);
            log.debug("classify code '{}' to '{}'", code, superCode);
        }
    }

    @Autowired(required = false)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setExceptionConverters(List<ExceptionConverter> exceptionConverters) {
        if (CollectionUtils.isEmpty(exceptionConverters)) return;
        log.debug("register '{}' ExceptionConverters", exceptionConverters.size());
        this.exceptionConverters = new LinkedHashMap<>(exceptionConverters.size());
        exceptionConverters.forEach(exceptionConverter -> {
            ResolvableType resolvableType = ResolvableType.forClass(
                    ExceptionConverter.class, exceptionConverter.getClass()
            );
            Class<?> exceptionClass = Objects.requireNonNull(resolvableType.resolveGeneric(0));
            if (Throwable.class.equals(exceptionClass)) return;//排除通用的异常转换器
            log.debug("register ExceptionConverter '{}' to support convert '{}'", exceptionConverter, exceptionClass.getName());
            this.exceptionConverters.put((Class<Throwable>) exceptionClass, exceptionConverter);
        });
    }

    @Autowired(required = false)
    public void setConditionalExceptionConverters(List<ConditionalExceptionConverter> conditionalExceptionConverters) {
        this.conditionalExceptionConverters = conditionalExceptionConverters;
    }

    @Autowired(required = false)
    public void setResultCodeClassifier(ResultCodeClassifier resultCodeClassifier) {
        this.resultCodeClassifier = resultCodeClassifier;
    }

    @Autowired
    public void setFallbackExceptionConverter(FallbackExceptionConverter fallbackExceptionConverter) {
        this.fallbackExceptionConverter = fallbackExceptionConverter;
    }
}
