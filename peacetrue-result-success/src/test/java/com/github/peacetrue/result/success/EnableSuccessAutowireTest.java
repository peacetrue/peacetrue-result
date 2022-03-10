package com.github.peacetrue.result.success;

import com.github.peacetrue.result.ResultTypes;
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
})
@AutoConfigureMockMvc
class EnableSuccessAutowireTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void divide() throws Exception {
        this.mockMvc.perform(get("/divide?divisor=10&dividend=2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultTypes.SUCCESS.getCode()))
                .andExpect(jsonPath("$.data").value(5))
        ;
    }

}
