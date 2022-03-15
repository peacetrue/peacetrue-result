package com.github.peacetrue.result.success;

import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author peace
 **/
@SpringBootTest(classes = {
        ResultMessageSourceAutoConfiguration.class,
        ResultBuilderAutoConfiguration.class,
        SuccessAutowireAutoConfiguration.class
})
class SuccessAutowireMockTest {

    private MockMvc mockMvc;
    @Autowired
    private SuccessAutowireResponseBodyAdvice bodyAdvice;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(SuccessAutowireTestController.class)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(bodyAdvice)
                .build();
    }

    @Test
    void enableSuccessAutowire() throws Exception {
        this.mockMvc.perform(get("/enableSuccessAutowire?input=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultTypes.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").value("1"))
        ;
    }

    @Test
    public void disableSuccessAutowireByAnnotation() throws Exception {
        this.mockMvc.perform(get("/disableSuccessAutowireByAnnotation?input=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1))
        ;
    }

    @Test
    public void disableSuccessAutowireByConfiguration() throws Exception {
        this.mockMvc.perform(get("/disableSuccessAutowireByConfiguration?input=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1))
        ;
    }

}
