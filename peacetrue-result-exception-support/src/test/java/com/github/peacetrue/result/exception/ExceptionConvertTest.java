package com.github.peacetrue.result.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.GenericDataResult;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import com.github.peacetrue.result.exception.jackson.JacksonResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.spring.SpringResultExceptionAutoConfiguration;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author peace
 */
@Slf4j
@SpringBootTest(
        classes = {
//                ResultErrorAttributesAutoConfiguration.class,
                ExceptionConvertTestController.class,
                ResultMessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ResultExceptionAutoConfiguration.class,
                ResultExceptionSupportAutoConfiguration.class,
                JacksonResultExceptionAutoConfiguration.class,
                SpringResultExceptionAutoConfiguration.class,
                DispatcherServletAutoConfiguration.class,
                WebMvcAutoConfiguration.class,
                ServletWebServerFactoryAutoConfiguration.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ExceptionConvertTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @Autowired
    private TestRestTemplate restTemplate;

    //    @Test
    public void ResourceNotFound() {
        GenericDataResult result = this.restTemplate.getForObject("/resource_not_found", GenericDataResult.class);
        Assertions.assertEquals(result.getCode(), ResultTypes.RESOURCE_NOT_FOUND.getCode());
    }

    @Test
    public void missingServletRequestParameter() {
        GenericDataResult result = this.restTemplate.getForObject("/missingServletRequestParameter", GenericDataResult.class);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_MISSING.getCode()));
    }

    @Test
    void missingPathVariable() {
        GenericDataResult result = this.restTemplate.getForObject("/missingPathVariable", GenericDataResult.class);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_MISSING.getCode()));
    }

    @Test
    void methodArgumentTypeMismatch() {
        GenericDataResult result = this.restTemplate.getForObject("/methodArgumentTypeMismatch?input=a", GenericDataResult.class);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_INVALID.getCode()));
    }

    @Test
    public void bindException() throws IOException {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("string", "string");
        params.add("bytes", "3");
        params.add("shorts", "3");
        params.add("integer", "3");
        params.add("longs", "3");
        params.add("floats", "3.3");
        params.add("doubles", "3.3");
        params.add("booleans", "1");
        params.add("date", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().plusDays(1)));
        GenericDataResult result = this.restTemplate.postForObject("/bindException", params, GenericDataResult.class);
        log.info("result: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result));
        Assertions.assertEquals(ResultTypes.ERRORS.getCode(), result.getCode());
    }

    @Test
    public void methodArgumentNotValid() throws IOException {
        TestBean testBean = EASY_RANDOM.nextObject(TestBean.class);
        HttpEntity<Object> requestEntity = new HttpEntity<>(testBean, headers(ImmutableMap.of(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
        )));
        GenericDataResult result = this.restTemplate.exchange("/methodArgumentNotValid", HttpMethod.POST, requestEntity, GenericDataResult.class).getBody();
        log.info("result: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result));
        Assertions.assertEquals(ResultTypes.ERRORS.getCode(), result.getCode());
    }

    @Test
    public void JsonProcessingException() throws IOException {
        HttpEntity<String> requestEntity = new HttpEntity<>("{x}", headers(ImmutableMap.of(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
        )));
        GenericDataResult result = this.restTemplate.exchange("/methodArgumentNotValid", HttpMethod.POST, requestEntity, GenericDataResult.class).getBody();
        log.info("result: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result));
        Assertions.assertEquals(ResultTypes.PARAMETER_INVALID.getCode(), result.getCode());
    }

    @Test
    public void InvalidFormatExceptionConverter() throws IOException {
        HttpEntity<String> requestEntity = new HttpEntity<>("{a:x}", headers(ImmutableMap.of(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE,
                HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5"
        )));
        GenericDataResult result = this.restTemplate.exchange("/methodArgumentNotValid", HttpMethod.POST, requestEntity, GenericDataResult.class).getBody();
        log.info("result: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result));
        Assertions.assertEquals(ResultTypes.PARAMETER_INVALID.getCode(), result.getCode());
    }

    public static HttpHeaders headers(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        return httpHeaders;
    }

}
