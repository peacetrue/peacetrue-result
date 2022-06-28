package com.github.peacetrue.result.exception.persistence;

import com.github.peacetrue.lang.UncheckedException;
import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;
import com.github.peacetrue.util.RegexUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.regex.Pattern;

/**
 * {@link EntityNotFoundException} 异常转换器。
 * <p>
 * 调用 {@link EntityManager#getReference(Class, Object)} 方法时，
 * 如果指定记录不存在抛出此异常。
 *
 * @author peace
 **/
public class EntityNotFoundExceptionConverter
        extends AbstractExceptionConverter<EntityNotFoundException>
        implements ClassifiedResultCode {

    private static final Pattern PATTERN = Pattern.compile("Unable to find (.+)? with id (.+)");

    static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new UncheckedException(e);
        }
    }

    @Nullable
    @Override
    protected Parameter<Class<?>, String> resolveArgs(EntityNotFoundException exception) {
        String message = exception.getMessage();
        String[] values = RegexUtils.extractValues(PATTERN, message);
        if (ObjectUtils.isEmpty(values)) {
            throw new IllegalStateException("Can't extract values from message '" + message + "'");
        }
        Class<?> clazz = forName(values[0]);
        return new Parameter<>(clazz.getSimpleName(), clazz, values[1]);
    }

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_ILLEGAL.getCode();
    }
}
