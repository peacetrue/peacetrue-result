package com.github.peacetrue.result.success;

import com.github.peacetrue.result.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(SuccessResultProperties.class)
public class SuccessResultAutoConfiguration {

    private SuccessResultProperties properties;

    public SuccessResultAutoConfiguration(SuccessResultProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(ResultAutoSuccess.class)
    public ResultAutoSuccess resultAutoSuccess() {
        Map<Class, Set<String>> methods = properties.getDisabledMethods().stream().collect(
                Collectors.groupingBy(
                        method -> forName(method.substring(0, method.lastIndexOf('.'))),
                        Collectors.mapping(method -> method.substring(method.lastIndexOf('.') + 1), Collectors.toSet()))
        );
        return new ExcludeMethodResultAutoSuccess(methods);
    }

    private static Class forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("'" + className + "'不是有效类名", e);
        }
    }

    /**
     * 控制器返回一个字符串，会被一组消息转换器处理，
     * 一组消息转换器中呢，字符串消息转换器排在Jackson的消息转换器之前，
     * 所以会优先处理这个字符串；
     * 我们要把这个字符串转换成{@link Result}，但是字符串转换器又不支持转换{@link Result}；
     * 所以呢，我们不能使用字符串转换器来转换字符串，应该优先使用Jackson转换器来处理。
     *
     * @see RequestResponseBodyMethodProcessor
     */
    @Bean
    @Order//最低优先级
    public WebMvcConfigurer jacksonAtFirstWebMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.sort((o1, o2) -> {
                    if (o1 instanceof AbstractJackson2HttpMessageConverter) return -1;
                    if (o2 instanceof AbstractJackson2HttpMessageConverter) return 1;
                    return 0;
                });
            }
        };
    }
}
