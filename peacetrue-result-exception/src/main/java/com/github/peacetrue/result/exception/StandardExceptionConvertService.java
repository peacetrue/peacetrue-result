package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultType;
import com.github.peacetrue.spring.util.GenericParameterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * the standard {@link ExceptionConvertService}
 *
 * @author xiayx
 */
public class StandardExceptionConvertService implements ExceptionConvertService {

    /** Well-known name for the fallback ExceptionConverter object in the bean factory for this namespace. */
    public static final String FALLBACK_EXCEPTION_CONVERTER_BEAN_NAME = "fallbackExceptionConverter";

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired(required = false)
    private List<ExceptionConverter> exceptionConverters;
    private Map<Class, ExceptionConverter> exceptionConverterMap;
    @Autowired(required = false)
    private List<ConditionalExceptionConverter> conditionalExceptionConverters;
    @Autowired
    @Qualifier(FALLBACK_EXCEPTION_CONVERTER_BEAN_NAME)
    private ExceptionConverter<Throwable> fallbackExceptionConverter;

    @PostConstruct
    public void init() {
        exceptionConverterMap = GenericParameterUtils.map(exceptionConverters, ExceptionConverter.class, 0);
        //remove FallbackExceptionConverter if exists
        exceptionConverterMap.remove(Throwable.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result convert(Throwable exception) {
        try {
            Optional<ExceptionConverter> exceptionConverter = GenericParameterUtils.find(exceptionConverterMap, exception.getClass());
            if (exceptionConverter.isPresent()) return exceptionConverter.get().convert(exception);

            for (ConditionalExceptionConverter conditionalExceptionConverter : conditionalExceptionConverters) {
                if (conditionalExceptionConverter.supports(exception)) {
                    return conditionalExceptionConverter.convert(exception);
                }
            }

            return fallbackExceptionConverter.convert(exception);
        } catch (Throwable e) {
            //Make sure there are no exceptions
            logger.warn("exception occurs when convert exception[{}] to result", exception.getClass().getName(), e);
            String message = "错误！！！异常转换系统出现故障，请立刻修复！";
            return new DataResultImpl<>(ResultType.failure.name(), message, exception.getMessage());
        }
    }

}
