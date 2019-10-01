package com.github.peacetrue.result;

import com.github.peacetrue.result.exception.ExceptionResultAutoConfiguration;
import com.github.peacetrue.result.success.SuccessResultAutoConfiguration;
import com.github.peacetrue.spring.expression.MessageExpressionAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        WebMvcAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        ExceptionResultAutoConfiguration.class,
        SuccessResultAutoConfiguration.class,
        MessageExpressionAutoConfiguration.class,
        AutoResultController.class
})
@AutoConfigureMockMvc
public class AutoResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void notFound() throws Exception {
        this.mockMvc.perform(get("/not/found"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(""));
    }

    @Test
    public void success() throws Exception {
        this.mockMvc.perform(get("/success")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("success"))
                .andExpect(jsonPath("$.data").value("success"))
        ;
    }

}