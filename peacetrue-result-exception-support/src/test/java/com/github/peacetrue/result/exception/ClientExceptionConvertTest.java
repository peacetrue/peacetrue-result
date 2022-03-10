package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.GenericDataResult;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import com.github.peacetrue.result.exception.spring.SpringResultExceptionAutoConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author peace
 */
@SpringBootTest(
        classes = {
                ResultMessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ResultExceptionAutoConfiguration.class,
                ResultExceptionSupportAutoConfiguration.class,
                SpringResultExceptionAutoConfiguration.class,
                ExceptionController.class,
                DispatcherServletAutoConfiguration.class,
                ErrorMvcAutoConfiguration.class,
                WebMvcAutoConfiguration.class,
                ServletWebServerFactoryAutoConfiguration.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ClientExceptionConvertTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void resource_not_found() {
        GenericDataResult result = this.restTemplate.getForObject("/resource_not_found", GenericDataResult.class);
        Assertions.assertEquals(result.getCode(), ResultTypes.RESOURCE_NOT_FOUND.getCode());
    }

    @Test
    void bindExceptionConverter() {
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
        GenericDataResult responseData = this.restTemplate.postForObject("/bindException", params, GenericDataResult.class);
        Assertions.assertEquals(responseData.getCode(), ResultTypes.ERRORS.getCode());
    }

    /**
     * @see org.springframework.web.bind.annotation.RequestParam
     */
    @Test
    void MissingServletRequestParameter() {
        GenericDataResult responseData = this.restTemplate.getForObject("/divide", GenericDataResult.class);
        System.out.println(responseData);
        Assertions.assertTrue(responseData.getCode().startsWith(ResultTypes.PARAMETER_MISSING.getCode()));
    }

    @Test
    void MissingPathVariable() {
        GenericDataResult responseData = this.restTemplate.getForObject("/divide/path/variable", GenericDataResult.class);
        System.out.println(responseData);
        Assertions.assertTrue(responseData.getCode().startsWith(ResultTypes.PARAMETER_MISSING.getCode()));
    }

    @Test
    void MethodArgumentTypeMismatch() {
        GenericDataResult responseData = this.restTemplate.getForObject("/divide?divisor=10&dividend=1.1", GenericDataResult.class);
        System.out.println(responseData);
        Assertions.assertTrue(responseData.getCode().startsWith(ResultTypes.PARAMETER_INVALID.getCode()));
    }

    @Test
    void idaas() {
//        LocaleContextHolder.setLocale(Locale.ENGLISH);
        HttpHeaders headers = new HttpHeaders();
//        Locale.SIMPLIFIED_CHINESE
        headers.add(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5");
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<GenericDataResult> response = this.restTemplate.exchange(
                "/idaas", HttpMethod.GET, requestEntity, GenericDataResult.class);
        GenericDataResult responseData = response.getBody();
        System.out.println(responseData);
        Assertions.assertEquals(responseData.getCode(), ResultTypes.ERRORS.getCode());
    }


}
