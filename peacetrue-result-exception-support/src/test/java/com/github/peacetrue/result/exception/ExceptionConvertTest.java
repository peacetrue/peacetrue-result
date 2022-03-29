package com.github.peacetrue.result.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import com.github.peacetrue.result.exception.jackson.JacksonResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.persistence.PersistenceResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.spring.SpringExceptionResultAutoConfiguration;
import com.github.peacetrue.result.exception.sql.SQLResultExceptionAutoConfiguration;
import com.github.peacetrue.test.SourcePathUtils;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author peace
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = {
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                TransactionAutoConfiguration.class,
                TestEntityManagerAutoConfiguration.class,

                HttpMessageConvertersAutoConfiguration.class,
                DispatcherServletAutoConfiguration.class,
                WebMvcAutoConfiguration.class,
                ServletWebServerFactoryAutoConfiguration.class,

                ResultMessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ExceptionResultAutoConfiguration.class,
                ResultExceptionSupportAutoConfiguration.class,
                JacksonResultExceptionAutoConfiguration.class,
                SpringExceptionResultAutoConfiguration.class,
                SQLResultExceptionAutoConfiguration.class,
                PersistenceResultExceptionAutoConfiguration.class,

                ExceptionConvertTestService.class,
                ExceptionConvertTestController.class,
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EntityScan
public class ExceptionConvertTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @Autowired
    private TestRestTemplate restTemplate;

    //    @Test
    void resourceNotFound() {
        Result result = this.restTemplate.getForObject("/resource_not_found", ResultImpl.class);
        Assertions.assertEquals(result.getCode(), ResultTypes.RESOURCE_NOT_FOUND.getCode());
    }

    @Test
    void missingServletRequestParameter() {
        Result result = this.restTemplate.getForObject("/missingServletRequestParameter", ResultImpl.class);
        generateDocument("missingServletRequestParameter", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_MISSING.getCode()));
    }

    @Test
    void missingPathVariable() {
        Result result = this.restTemplate.getForObject("/missingPathVariable", ResultImpl.class);
        generateDocument("missingPathVariable", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_MISSING.getCode()));
    }

    @Test
    void methodArgumentTypeMismatch() {
        Result result = this.restTemplate.getForObject("/methodArgumentTypeMismatch?input=a", ResultImpl.class);
        generateDocument("methodArgumentTypeMismatch", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_INVALID.getCode()));
    }

    @Test
    void beanInvalid() {
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
        Result result = this.restTemplate.postForObject("/beanInvalid", params, ResultImpl.class);
        generateDocument("beanInvalid", result);
        Assertions.assertEquals(ResultTypes.ERRORS.getCode(), result.getCode());
    }


    @Test
    void methodArgumentNotValid() {
        TestBean testBean = EASY_RANDOM.nextObject(TestBean.class);
        HttpEntity<Object> requestEntity = new HttpEntity<>(testBean, headers(ImmutableMap.of(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
        )));
        Result result = this.restTemplate.exchange("/methodArgumentNotValid", HttpMethod.POST, requestEntity, ResultImpl.class).getBody();
        generateDocument("methodArgumentNotValid", result);
        Assertions.assertEquals(ResultTypes.ERRORS.getCode(), result.getCode());
    }

    @Test
    void jsonProcessingException() {
        HttpEntity<String> requestEntity = new HttpEntity<>("{x}", headers(ImmutableMap.of(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
        )));
        Result result = this.restTemplate.exchange("/methodArgumentNotValid", HttpMethod.POST, requestEntity, ResultImpl.class).getBody();
        generateDocument("jsonProcessingException", result);
        Assertions.assertEquals(ResultTypes.PARAMETER_INVALID.getCode(), result.getCode());
    }

    @Test
    void invalidFormatException() {
        HttpEntity<String> requestEntity = new HttpEntity<>("{a:x}", headers(ImmutableMap.of(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE,
                HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5"
        )));
        Result result = this.restTemplate.exchange("/methodArgumentNotValid", HttpMethod.POST, requestEntity, ResultImpl.class).getBody();
        generateDocument("invalidFormatException", result);
        Assertions.assertEquals(ResultTypes.PARAMETER_INVALID.getCode(), result.getCode());
    }

    public static HttpHeaders headers(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        return httpHeaders;
    }

    @Test
    void entityNotFound() {
        Result result = this.restTemplate.getForObject("/entityNotFound", ResultImpl.class);
        generateDocument("entityNotFound", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.RECORD_NOT_FOUND.getCode()));
    }

    @Test
    void duplicate() {
        Result result = this.restTemplate.postForObject("/duplicate", HttpMethod.POST, ResultImpl.class);
        generateDocument("duplicate", result);
        Assertions.assertTrue(result.getCode().startsWith("unique"));
    }

    private static void generateDocument(String name, Object result) {
        try {
            String stringPath = SourcePathUtils.getTestResourceAbsolutePath(
                    "/com/github/peacetrue/result/exception/", name, ".json"
            );
            Path path = Paths.get(stringPath);
            if (Files.notExists(path)) Files.createFile(path);
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(path.toFile(), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
