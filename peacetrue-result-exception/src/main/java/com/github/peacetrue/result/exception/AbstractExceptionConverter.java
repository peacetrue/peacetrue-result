package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.spring.expression.MessageFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;

/**
 * a abstract {@link ExceptionConverter}
 *
 * @author xiayx
 */
public abstract class AbstractExceptionConverter<E extends Throwable, D> implements ExceptionConverter<E> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageFormatter messageFormatter;

    @Override
    public Result convert(E exception) {
        logger.info("转换异常[{}]", exception.getClass().getName());
        return buildResult(
                resolveCode(exception),
                resolveMessage(exception),
                resolveData(exception)
        );
    }

    /**
     * get standard result code
     *
     * @param exception the exception
     * @return the standard result code
     */
    protected String resolveCode(E exception) {
        return exception.getClass().getSimpleName();
    }


    /**
     * get standard result code
     *
     * @param exception the exception
     * @return the standard result code
     */
    protected String resolveMessage(E exception) {
        return exception.getMessage();
    }

    /**
     * convert data
     *
     * @param exception the exception
     * @return the data
     */
    @Nullable
    protected D resolveData(E exception) {
        return null;
    }

    protected Result buildResult(String code, String message, D data) {
        if (data == null) return new ResultImpl(code, message);
        String formattedMessage = messageFormatter.format(message, data);
        return new DataResultImpl<>(code, formattedMessage, data);
    }

}
