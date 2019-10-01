package com.github.peacetrue.result.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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
        String responseData = this.template.getForObject("/not/found", String.class);
        System.out.println(responseData);
    }

    @Test
    public void divide() throws Exception {
        String responseData = this.template.getForObject("/divide?divisor=10&dividend=0", String.class);
        System.out.println(responseData);
    }

}
