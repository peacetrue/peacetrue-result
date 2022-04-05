package com.github.peacetrue.result.success;

import com.github.peacetrue.result.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;
import java.util.function.Predicate;

/**
 * 将 Jackson 消息转换器排在最前面。
 *
 * @author peace
 */
@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class JacksonAtFirstAutoConfiguration {

    /**
     * 将符合条件的 {@link HttpMessageConverter} 排在最前面。
     *
     * @param predicate 判断条件
     * @return mvc 配置
     */
    public static WebMvcConfigurer getSortedWebMvcConfigurer(Predicate<HttpMessageConverter<?>> predicate) {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.sort((o1, o2) -> {
                    if (predicate.test(o1)) return -1;
                    if (predicate.test(o2)) return 1;
                    return 0;
                });
            }
        };
    }

    /**
     * 控制器返回一个字符串，会被一组消息转换器处理，
     * 一组消息转换器中，{@link StringHttpMessageConverter} 排在 {@link MappingJackson2HttpMessageConverter} 之前，所以会优先处理这个字符串；
     * 我们要把这个字符串转换成 {@link Result}，但是 {@link StringHttpMessageConverter} 又不支持转换 {@link Result}，导致自动转换逻辑不会被执行。
     * 所以我们不能使用 {@link StringHttpMessageConverter} 转换字符串，应该优先使用 {@link MappingJackson2HttpMessageConverter}。
     *
     * @return 自定义的配置
     * @see RequestResponseBodyMethodProcessor
     */
    @Bean
    public WebMvcConfigurer jacksonAtFirstWebMvcConfigurer() {
        return getSortedWebMvcConfigurer(AbstractJackson2HttpMessageConverter.class::isInstance);
    }
}
