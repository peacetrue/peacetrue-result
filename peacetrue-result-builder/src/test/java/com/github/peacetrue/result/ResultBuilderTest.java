package com.github.peacetrue.result;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * the tests for {@link MessageSourceResultBuilder}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("result-builder")
public class ResultBuilderTest {

    @Autowired
    private ResultBuilder resultBuilder;
    @Autowired
    private MessageSource messageSource;


    @Test
    public void success() throws Exception {
        Result success = resultBuilder.success();
        Assert.assertEquals("0", success.getCode());
        Assert.assertEquals("请求成功！", success.getMessage());
    }

    @Test
    public void failure() throws Exception {
        Result failure = resultBuilder.failure();
        Assert.assertEquals("1", failure.getCode());
        Assert.assertEquals("请求失败！", failure.getMessage());
    }

}