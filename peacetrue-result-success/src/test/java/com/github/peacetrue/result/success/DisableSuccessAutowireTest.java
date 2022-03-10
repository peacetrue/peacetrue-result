package com.github.peacetrue.result.success;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author peace
 **/
@SpringBootTest(classes = {
        WebMvcAutoConfiguration.class,
        SuccessAutowireAutoConfiguration.class,
        SuccessAutowireController.class
}, properties = {
        "peacetrue.result.autowire.success.disabled-methods=com.github.peacetrue.result.autowire.success.SuccessAutowireController.divide"
})
@AutoConfigureMockMvc
class DisableSuccessAutowireTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void divideDisableByAnnotation() throws Exception {
        this.mockMvc.perform(get("/divideDisableSuccessAutowire?divisor=10&dividend=2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5))
        ;
    }

    @Test
    void divideDisableByConfiguration() throws Exception {
        this.mockMvc.perform(get("/divide?divisor=10&dividend=2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5))
        ;
    }
}
