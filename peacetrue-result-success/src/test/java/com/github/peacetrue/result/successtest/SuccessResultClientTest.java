package com.github.peacetrue.result.successtest;

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
public class SuccessResultClientTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void divideException() throws Exception {
        String response = this.template.getForObject("/divide?divisor=10&dividend=0", String.class);
        System.out.println(response);
    }

}