package com.github.peacetrue.result.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.GenericDataResult;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import com.github.peacetrue.result.exception.jackson.JacksonResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.persistence.PersistenceResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.spring.SpringResultExceptionAutoConfiguration;
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
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.convert.converter.Converter;
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
import java.util.Date;
import java.util.List;
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

                ServletWebServerFactoryAutoConfiguration.class,
                DispatcherServletAutoConfiguration.class,
                WebMvcAutoConfiguration.class,

                HttpMessageConvertersAutoConfiguration.class,
                ExceptionConvertTest.DateConverter.class,

                ResultMessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ResultExceptionAutoConfiguration.class,
                ErrorMvcAutoConfiguration.class,

                ResultExceptionSupportAutoConfiguration.class,
                JacksonResultExceptionAutoConfiguration.class,
                SpringResultExceptionAutoConfiguration.class,
                SQLResultExceptionAutoConfiguration.class,
                PersistenceResultExceptionAutoConfiguration.class,

                ExceptionConvertTestService.class,
                ExceptionConvertTestController.class,
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EntityScan
class ExceptionConvertTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();
    @Autowired
    private TestRestTemplate restTemplate;

    private static HttpHeaders headers(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        return httpHeaders;
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

    @Test
    void resourceNotFound() {
        Result result = this.restTemplate.getForObject("/resource_not_found", ResultImpl.class);
        generateDocument("resourceNotFound", result);
        Assertions.assertEquals(ResultTypes.RESOURCE_NOT_FOUND.getCode(), result.getCode());
    }

    //tag::missingServletRequestParameter[]
    @Test
    void missingServletRequestParameter() {
        Result result = this.restTemplate.getForObject("/missingServletRequestParameter", ResultImpl.class);
        generateDocument("missingServletRequestParameter", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_MISSING.getCode()));
    }
    //end::missingServletRequestParameter[]

    //tag::missingPathVariable[]
    @Test
    void missingPathVariable() {
        Result result = this.restTemplate.getForObject("/missingPathVariable", ResultImpl.class);
        generateDocument("missingPathVariable", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.SERVER_ERROR.getCode()));
    }
    //end::missingPathVariable[]

    //tag::methodArgumentConversionNotSupported[]
    @Test
    void methodArgumentConversionNotSupported() {
        //org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException: Failed to convert value of type 'java.lang.String' to required type 'com.github.peacetrue.result.exception.TestBean'; nested exception is java.lang.IllegalStateException: Cannot convert value of type 'java.lang.String' to required type 'com.github.peacetrue.result.exception.TestBean': no matching editors or conversion strategy found
        Result result = this.restTemplate.getForObject("/methodArgumentConversionNotSupported?input=test", ResultImpl.class);
        generateDocument("methodArgumentConversionNotSupported", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.SERVER_ERROR.getCode()));
    }
    //end::methodArgumentConversionNotSupported[]

    //tag::methodArgumentTypeMismatch[]
    @Test
    void methodArgumentTypeMismatch() {
        Result result = this.restTemplate.getForObject("/methodArgumentTypeMismatch?input=test", ResultImpl.class);
        generateDocument("methodArgumentTypeMismatch", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_INVALID.getCode()));
    }
    //end::methodArgumentTypeMismatch[]

    //tag::bind[]
    @Test
    void bind() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("assertFalse", "true");
        params.add("assertTrue", "false");
        params.add("decimalMin", "-1.0");
        params.add("decimalMax", "1.0");
        params.add("digits", "10.01");
        params.add("email", "test.com");
        params.add("future", String.valueOf(System.currentTimeMillis()));
        params.add("futureOrPresent", String.valueOf(System.currentTimeMillis() - 1));
        params.add("min", "-1");
        params.add("max", "1");
        params.add("negative", "0");
        params.add("negativeOrZero", "1");
        params.add("notBlank", " ");
        params.add("notEmpty", "");
        params.add("notNull", null);
        params.add("nulls", "0");
        params.add("past", String.valueOf(System.currentTimeMillis() + 1000 * 1000));
        params.add("pastOrPresent", String.valueOf(System.currentTimeMillis() + 1000 * 1000));
        params.add("pattern", "00");
        params.add("positive", "0");
        params.add("positiveOrZero", "-1");
        params.add("size", "00");
        GenericDataResult result = this.restTemplate.postForObject("/bind", params, GenericDataResult.class);
        generateDocument("bind", result);

        Assertions.assertEquals(ResultTypes.ERRORS.getCode(), result.getCode());
        Assertions.assertTrue(result.getData() instanceof List);
        Assertions.assertEquals(params.size(), ((List<?>) result.getData()).size());
    }
    //end::bind[]

    //tag::requestBodyMissing[]
    @Test
    void requestBodyMissing() {
        //Required request body is missing: public com.github.peacetrue.result.exception.TestBean com.github.peacetrue.result.exception.ExceptionConvertTestController.httpMessageNotReadable(com.github.peacetrue.result.exception.TestBean)
        String emptyRequestBody = "";
        HttpEntity<String> requestEntity = new HttpEntity<>(emptyRequestBody, headers(ImmutableMap.of(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
        )));
        Result result = this.restTemplate.exchange("/httpMessageNotReadable", HttpMethod.POST, requestEntity, ResultImpl.class).getBody();
        generateDocument("requestBodyMissing", result);
        Assertions.assertEquals(ResultTypes.PARAMETER_MISSING.getCode(), result.getCode());
    }
    //end::requestBodyMissing[]

    //tag::requestBodyJsonInvalid[]
    @Test
    void requestBodyJsonInvalid() {
        //org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Unexpected character ('?' (code 63)): expected a valid value (number, String, array, object, 'true', 'false' or 'null'); nested exception is com.fasterxml.jackson.core.JsonParseException: Unexpected character ('?' (code 63)): expected a valid value (number, String, array, object, 'true', 'false' or 'null')
        //Caused by: com.fasterxml.jackson.core.JsonParseException: Unexpected character ('?' (code 63)): expected a valid value (number, String, array, object, 'true', 'false' or 'null')
        String invalidRequestBody = "??";
        HttpEntity<String> requestEntity = new HttpEntity<>(invalidRequestBody, headers(ImmutableMap.of(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
        )));
        Result result = this.restTemplate.exchange("/httpMessageNotReadable", HttpMethod.POST, requestEntity, ResultImpl.class).getBody();
        generateDocument("requestBodyJsonInvalid", result);
        Assertions.assertEquals(ResultTypes.PARAMETER_INVALID.getCode(), result.getCode());

        //org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Unexpected end-of-input in field name; nested exception is com.fasterxml.jackson.core.io.JsonEOFException: Unexpected end-of-input in field name
        //Caused by: com.fasterxml.jackson.core.io.JsonEOFException: Unexpected end-of-input in field name
        invalidRequestBody = "{\"name}";
        requestEntity = new HttpEntity<>(invalidRequestBody, headers(ImmutableMap.of(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
        )));
        result = this.restTemplate.exchange("/httpMessageNotReadable", HttpMethod.POST, requestEntity, ResultImpl.class).getBody();
        generateDocument("requestBodyJsonInvalidEOF", result);
        Assertions.assertEquals(ResultTypes.PARAMETER_INVALID.getCode(), result.getCode());
    }
    //end::requestBodyJsonInvalid[]

    //tag::requestBodyJsonFormatInvalid[]
    @Test
    void requestBodyJsonFormatInvalid() {
        //InvalidFormatException
        //org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot deserialize value of type `java.util.Date` from String "?": not a valid representation (error: Failed to parse Date value '?': Cannot parse date "?": not compatible with any of standard forms ("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd")); nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `java.util.Date` from String "?": not a valid representation (error: Failed to parse Date value '?': Cannot parse date "?": not compatible with any of standard forms ("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd"))
        //Caused by: com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `java.util.Date` from String "?": not a valid representation (error: Failed to parse Date value '?': Cannot parse date "?": not compatible with any of standard forms ("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd"))
        String formatInvalidRequestBody = "{\"future\": \"?\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(formatInvalidRequestBody, headers(ImmutableMap.of(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
        )));
        Result result = this.restTemplate.exchange("/httpMessageNotReadable", HttpMethod.POST, requestEntity, ResultImpl.class).getBody();
        generateDocument("requestBodyJsonFormatInvalid", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_INVALID.getCode()));
    }
    //end::requestBodyJsonFormatInvalid[]

    //tag::methodArgumentNotValid[]
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
    //end::methodArgumentNotValid[]

    //tag::entityNotFound[]
    @Test
    void entityNotFound() {
        long idNotExists = 0L;
        Result result = this.restTemplate.getForObject("/entityNotFound?id={0}", ResultImpl.class, idNotExists);
        generateDocument("entityNotFound", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_ILLEGAL.getCode()));
    }
    //end::entityNotFound[]

    //tag::duplicate[]
    @Test
    void duplicate() {
        //javax.persistence.PersistenceException: org.hibernate.exception.ConstraintViolationException: could not execute statement
        //Caused by: org.hibernate.exception.ConstraintViolationException: could not execute statement
        //Caused by: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry 'test' for key 'test_entity.uk_name'
        String existName = "test";
        Result result = this.restTemplate.postForObject("/duplicate?name={0}", HttpMethod.POST, ResultImpl.class, existName);
        generateDocument("duplicate", result);
        Assertions.assertTrue(result.getCode().startsWith("unique"));
    }
    //end::duplicate[]

    //tag::userDisabled[]
    @Test
    void userDisabled() {
        Result result = this.restTemplate.postForObject("/userDisabled", HttpMethod.POST, ResultImpl.class);
        generateDocument("userDisabled", result);
        Assertions.assertEquals(result.getCode(), "user_disabled");
    }
    //end::userDisabled[]

    public static class DateConverter implements Converter<String, Date> {
        @Override
        public Date convert(String s) {
            return new Date(Long.parseLong(s));
        }
    }
}
