package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * for {@link Exception#getCause()}
 *
 * @author xiayx
 */
public class ProxyConditionalExceptionConverter implements ConditionalExceptionConverter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private List<Class<? extends Throwable>> proxyClasses;
    private ExceptionConvertService exceptionConvertService;

    public ProxyConditionalExceptionConverter() {
    }

    public ProxyConditionalExceptionConverter(List<Class<? extends Throwable>> proxyClasses) {
        this.proxyClasses = new ArrayList<>(Objects.requireNonNull(proxyClasses));
    }

    @Override
    public boolean supports(Throwable exception) {
        Throwable cause = exception.getCause();
        if (cause == null || cause.equals(exception)) return false;
        for (Class<? extends Throwable> proxyClass : proxyClasses) {
            if (proxyClass.equals(exception.getClass())) {
                logger.info("the exception class '{}' proxy for '{}'", exception.getClass().getName(), cause.getClass().getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public Result convert(Throwable exception) {
        return exceptionConvertService.convert(exception.getCause());
    }

    public void setProxyClasses(List<Class<? extends Throwable>> proxyClasses) {
        this.proxyClasses = proxyClasses;
    }

    @Autowired
    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
        this.exceptionConvertService = exceptionConvertService;
    }
}
