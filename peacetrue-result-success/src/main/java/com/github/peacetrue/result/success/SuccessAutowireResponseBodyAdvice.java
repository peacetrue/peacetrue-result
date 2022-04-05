package com.github.peacetrue.result.success;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultCustomizer;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.ResultUtils;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import com.github.peacetrue.result.success.ResultSuccessAutoConfiguration.WebMvcSuccessAutowireAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 自动将不是响应结果的返回数据转换为成功响应结果。
 * <p>
 * 此类不应该被 Spring 扫描到，因为已经在配置 {@link WebMvcSuccessAutowireAutoConfiguration#successAutowireResponseBodyAdvice(ResultSuccessProperties) WebMvcSuccessAutowireAutoConfiguration} 中声明了。
 * 如果被扫描，会导致 {@link ResultSuccessProperties} 中配置失效。
 * <p>
 * 此类可能会导致 {@code JsonView} 失效，有待进一步测试。
 *
 * @author peace
 * @see SuccessAutowire
 * @see JsonViewResponseBodyAdvice
 */
@Slf4j
@ControllerAdvice
public class SuccessAutowireResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /** 遗留的系统，无法使用注解 {@link SuccessAutowire}，可通过配置禁用 */
    private final Set<String> disabledMethods;
    private final Set<Class<?>> resultTypes;
    private ResultCustomizer resultCustomizer;
    private ResultMessageBuilder resultMessageBuilder;

    public SuccessAutowireResponseBodyAdvice() {
        this(Collections.singleton(Result.class), Collections.emptySet());
    }

    public SuccessAutowireResponseBodyAdvice(Set<Class<?>> resultTypes, Set<String> disabledMethods) {
        this.resultTypes = Objects.requireNonNull(resultTypes);
        this.disabledMethods = Objects.requireNonNull(disabledMethods);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !isAlreadyResult(returnType)
                && !isDisabledAutowire(returnType)
                && canConvertResult(converterType)
                && !isDisabledMethod(returnType);
    }

    private boolean isAlreadyResult(MethodParameter returnType) {
        Class<?> parameterType = returnType.getParameterType();
        return resultTypes.stream().anyMatch(item -> item.isAssignableFrom(parameterType));
    }

    private boolean isDisabledAutowire(MethodParameter returnType) {
        return Optional.ofNullable(returnType.getMethodAnnotation(SuccessAutowire.class))
                .filter(SuccessAutowire::disabled)
                .isPresent();
    }

    private boolean canConvertResult(Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> resolveGeneric = ResolvableType
                .forClass(HttpMessageConverter.class, converterType)
                .resolveGeneric(0);
        return Objects.requireNonNull(resolveGeneric).isAssignableFrom(Result.class);
    }

    private boolean isDisabledMethod(MethodParameter returnType) {
        if (disabledMethods.isEmpty()) return false;
        Method method = returnType.getMethod();
        if (method == null) return false;
        String name = method.getDeclaringClass().getName() + "." + method.getName();
        return disabledMethods.stream().anyMatch(name::startsWith);
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        Result result = this.toSuccessResult(body);
        log.debug("convert response data which is not a Result to Result: '{}' -> '{}'", body, result);
        return resultCustomizer.customize(result);
    }

    /**
     * 转换为成功的响应结果
     *
     * @param body 响应数据
     * @return 响应结果
     */
    protected Result toSuccessResult(@Nullable Object body) {
        String code = ResultTypes.SUCCESS.getCode();
        String message = resultMessageBuilder.build(code, body);
        return ResultUtils.build(code, message, body);
    }

    @Autowired
    public void setResultCustomizer(ResultCustomizer resultCustomizer) {
        this.resultCustomizer = resultCustomizer;
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }
}
