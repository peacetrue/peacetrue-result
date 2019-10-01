package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.ExtendResultType;
import com.github.peacetrue.result.ResultType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExceptionResultClientTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void notFound() throws Exception {
        DataResultImpl responseData = this.template.getForObject("/not/found", DataResultImpl.class);
        Assert.assertEquals(responseData.getCode(), ResultType.path_not_found.getCode());
    }

    @Test
    public void MissingServletRequestParameter() throws Exception {
//        JavaType javaType = TypeFactory.defaultInstance().constructParametricType(DataResultImpl.class, Parameter.class);
        DataResultImpl responseData = this.template.getForObject("/divide", DataResultImpl.class);
        Assert.assertEquals(responseData.getCode(), ResultType.parameter_missing.getCode());
    }

    @Test
    public void MissingPathVariableExceptionConverter() throws Exception {
//        JavaType javaType = TypeFactory.defaultInstance().constructParametricType(DataResultImpl.class, Parameter.class);
        DataResultImpl responseData = this.template.getForObject("/divide/path/variable", DataResultImpl.class);
        System.out.println(responseData);
        Assert.assertEquals(responseData.getCode(), ExtendResultType.path_variable_missing.getCode());
    }

    @Test
    public void methodArgumentTypeMismatchExceptionConverter() throws Exception {
//        JavaType javaType = TypeFactory.defaultInstance().constructParametricType(DataResultImpl.class, Parameter.class);
        DataResultImpl responseData = this.template.getForObject("/divide?divisor=10&dividend=1.1", DataResultImpl.class);
        System.out.println(responseData);
        Assert.assertEquals(responseData.getCode(), ResultType.parameter_format_error.getCode());
    }

    @Test
    public void bindExceptionConverter() throws Exception {
//        JavaType javaType = TypeFactory.defaultInstance().constructParametricType(DataResultImpl.class, Parameter.class);
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
        DataResultImpl responseData = this.template.postForObject("/parameter_range_error_bean", params, DataResultImpl.class);
        System.out.println(responseData);
        Assert.assertEquals(responseData.getCode(), ResultType.errors.getCode());
    }

}
