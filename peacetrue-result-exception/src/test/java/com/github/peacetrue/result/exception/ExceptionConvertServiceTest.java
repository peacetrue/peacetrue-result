package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * tests for result exception
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionConvertServiceTest {

    @Autowired
    private ExceptionConvertService exceptionConvertService;

    @Test
    public void resultException() throws Exception {
        ResultException exception = new ResultException("code", "message");
        Result result = exceptionConvertService.convert(exception);
        Assert.assertEquals(result.toString(), exception.toString());
    }

    @Test
    public void dataResultException() throws Exception {
        DataResultException exception = new DataResultException("code", "message", "data");
        Result result = exceptionConvertService.convert(exception);
        Assert.assertEquals(result.toString(), exception.toString());
    }


}
