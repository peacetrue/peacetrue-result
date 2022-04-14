package com.github.peacetrue.result.exception;

import com.github.peacetrue.beans.properties.code.CodeAware;
import com.github.peacetrue.result.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.util.CollectionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.IntStream;

import static com.github.peacetrue.result.ResultTypes.ERRORS;

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
    private ResultCodeClassifier resultCodeClassifier;
    private boolean includeStackTrace;
    private boolean includeMessageTemplateArgs;

    private static String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    @Override
    public Result convert(Throwable throwable) {
        log.info("convert '{}'", throwable.getClass().getName());
        Result result = mapConvert(throwable)
                .orElseGet(() -> conditionalConvert(throwable)
                        .orElseGet(() -> fallbackConvert(throwable)));
        classifyResultCode(result);
        if (ERRORS.getCode().equals(result.getCode())) return clearErrorsResultData(result);
        if (includeStackTrace) return new DataResultImpl<>(result, getStackTrace(throwable));
        if (clearMessageTemplateArgs(result)) return new ResultImpl(result);
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

    private Result clearErrorsResultData(Result result) {
        Object data = ResultUtils.getData(result);
        if (!(data instanceof List)) {
            log.warn("Result.data must be List where Result.code is errors, currently {}", data);
            return result;
        }
        @SuppressWarnings("unchecked")
        List<Result> results = (List<Result>) data;
        IntStream.range(0, results.size()).forEach(index -> {
            Result childResult = results.get(index);
            if (clearMessageTemplateArgs(childResult)) results.set(index, new ResultImpl(childResult));
        });
        return result;
    }

    private boolean clearMessageTemplateArgs(Object result) {
        return !includeMessageTemplateArgs && result instanceof DataResult;
    }

    @SuppressWarnings("all")
    public void setExceptionConverters(List<ExceptionConverter> exceptionConverters) {
        if (CollectionUtils.isEmpty(exceptionConverters)) return;
        log.debug("register '{}' ExceptionConverter", exceptionConverters.size());
        //子类排在最前面，查找时优先匹配子类
        this.exceptionConverters = new TreeMap<>((parent, child) -> parent.isAssignableFrom(child) ? 1 : -1);
        exceptionConverters.forEach(exceptionConverter -> {
            ResolvableType resolvableType = ResolvableType.forClass(
                    ExceptionConverter.class, exceptionConverter.getClass()
            );
            Class<?> exceptionClass = Objects.requireNonNull(resolvableType.resolveGeneric(0));
            if (Throwable.class.equals(exceptionClass)) {
                //排除通用的异常转换器
                log.debug("ignore generic ExceptionConverter which supports Throwable: '{}'", exceptionConverter);
                return;
            }
            log.debug("register ExceptionConverter: '{}' -> '{}'", exceptionConverter, exceptionClass.getName());
            this.exceptionConverters.put((Class<Throwable>) exceptionClass, exceptionConverter);
        });
    }

    public void setConditionalExceptionConverters(List<ConditionalExceptionConverter> conditionalExceptionConverters) {
        if (CollectionUtils.isEmpty(conditionalExceptionConverters)) return;
        log.debug("register '{}' ConditionalExceptionConverter: {}",
                conditionalExceptionConverters.size(), conditionalExceptionConverters);
        this.conditionalExceptionConverters = conditionalExceptionConverters;
    }

    @Autowired
    public void setFallbackExceptionConverter(FallbackExceptionConverter fallbackExceptionConverter) {
        this.fallbackExceptionConverter = fallbackExceptionConverter;
    }

    @Autowired
    public void setResultCodeClassifier(ResultCodeClassifier resultCodeClassifier) {
        this.resultCodeClassifier = resultCodeClassifier;
    }

    public void setIncludeStackTrace(boolean includeStackTrace) {
        this.includeStackTrace = includeStackTrace;
    }

    public void setIncludeMessageTemplateArgs(boolean includeMessageTemplateArgs) {
        this.includeMessageTemplateArgs = includeMessageTemplateArgs;
    }
}
