package com.github.peacetrue.result.exception;

import com.github.peacetrue.beans.properties.code.CodeAware;
import com.github.peacetrue.result.DataResult;
import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.util.CollectionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * 异常转换器服务实现。
 *
 * @author peace
 */
@Slf4j
public class ExceptionConvertServiceImpl implements ExceptionConvertService {

    private Map<Class<Throwable>, ExceptionConverter<Throwable>> exceptionConverters = Collections.emptyMap();
    private List<ConditionalExceptionConverter> conditionalExceptionConverters = Collections.emptyList();
    private FallbackExceptionConverter fallbackExceptionConverter;
    private ResultCodeClassifier resultCodeClassifier = ResultCodeClassifier.DEFAULT;
    private boolean includeStackTrace;
    private boolean retainResultData;

    @Override
    public Result convert(Throwable throwable) {
        log.info("convert '{}'", throwable.getClass().getName());
        Result result = mapConvert(throwable)
                .orElseGet(() -> conditionalConvert(throwable)
                        .orElseGet(() -> fallbackConvert(throwable)));
        classifyResultCode(result);
        if (includeStackTrace) return new DataResultImpl<>(result, getStackTrace(throwable));
        if (!retainResultData && result instanceof DataResult) return new ResultImpl(result);
        return result;
    }

    @SuppressWarnings("java:S3864")
    private Optional<Result> mapConvert(Throwable throwable) {
        //优先使用子类处理，子类不支持再使用父类处理
        return exceptionConverters.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(throwable.getClass()))
                .peek(entry -> log.debug("converted by {}", entry.getValue()))
                .findFirst().map(item -> item.getValue().convert(throwable));
    }

    @SuppressWarnings("java:S3864")
    private Optional<Result> conditionalConvert(Throwable throwable) {
        return conditionalExceptionConverters
                .stream().filter(item -> item.supports(throwable))
                .peek(converter -> log.debug("converted by {}", converter))
                .findFirst().map(item -> item.convert(throwable));
    }

    private Result fallbackConvert(Throwable throwable) {
        return fallbackExceptionConverter.convert(throwable);
    }

    private void classifyResultCode(Result result) {
        if (result instanceof CodeAware) {
            resultCodeClassifier.classifyResultCode(result.getCode())
                    .ifPresent(((CodeAware) result)::setCode);
        }
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    @Autowired(required = false)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setExceptionConverters(List<ExceptionConverter> exceptionConverters) {
        if (CollectionUtils.isEmpty(exceptionConverters)) return;
        log.debug("register '{}' ExceptionConverters", exceptionConverters.size());
        //子类排在最前面，查找是优先匹配子类
        this.exceptionConverters = new TreeMap<>((class1, class2) -> {
            if (class1.isAssignableFrom(class2)) return 1;
            else if (class2.isAssignableFrom(class1)) return -1;
            else return class1.getName().compareTo(class2.getName());
        });
        exceptionConverters.forEach(exceptionConverter -> {
            ResolvableType resolvableType = ResolvableType.forClass(
                    ExceptionConverter.class, exceptionConverter.getClass()
            );
            Class<?> exceptionClass = Objects.requireNonNull(resolvableType.resolveGeneric(0));
            if (Throwable.class.equals(exceptionClass)) return;//排除通用的异常转换器
            log.debug("register ExceptionConverter: '{}' -> '{}'", exceptionConverter, exceptionClass.getName());
            this.exceptionConverters.put((Class<Throwable>) exceptionClass, exceptionConverter);
        });
    }

    @Autowired(required = false)
    public void setConditionalExceptionConverters(List<ConditionalExceptionConverter> conditionalExceptionConverters) {
        this.conditionalExceptionConverters = conditionalExceptionConverters;
    }

    @Autowired
    public void setFallbackExceptionConverter(FallbackExceptionConverter fallbackExceptionConverter) {
        this.fallbackExceptionConverter = fallbackExceptionConverter;
    }

    @Autowired(required = false)
    public void setResultCodeClassifier(ResultCodeClassifier resultCodeClassifier) {
        this.resultCodeClassifier = resultCodeClassifier;
    }

    public void setIncludeStackTrace(boolean includeStackTrace) {
        this.includeStackTrace = includeStackTrace;
    }

    public void setRetainResultData(boolean retainResultData) {
        this.retainResultData = retainResultData;
    }
}
