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
    private GenericExceptionConverter genericExceptionConverter;
    private ResultCodeClassifier resultCodeClassifier;

    @Override
    public Result convert(Throwable throwable) {
        ExceptionConverter<Throwable> exceptionConverter =
                exceptionConverters.getOrDefault(throwable.getClass(), genericExceptionConverter);
        Result result = exceptionConverter.convert(throwable);
        classifyResultCode(result);
        return result;
    }

    private void classifyResultCode(Result result) {
        if (resultCodeClassifier != null && result instanceof CodeAware) {
            String code = result.getCode();
            String superCode = resultCodeClassifier.classifyResultCode(code);
            ((CodeAware) result).setCode(superCode);
            log.debug("classify code '{}' to '{}'", code, superCode);
        }
    }

    @Autowired
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

    @Autowired
    public void setGenericExceptionConverter(GenericExceptionConverter genericExceptionConverter) {
        this.genericExceptionConverter = genericExceptionConverter;
    }

    @Autowired(required = false)
    public void setResultCodeClassifier(ResultCodeClassifier resultCodeClassifier) {
        this.resultCodeClassifier = resultCodeClassifier;
    }
}
