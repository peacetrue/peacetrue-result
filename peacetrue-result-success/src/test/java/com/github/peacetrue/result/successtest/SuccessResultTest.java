package com.github.peacetrue.result.successtest;

import com.github.peacetrue.result.ResultType;
import com.github.peacetrue.result.success.ResultAutoSuccess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 此测试要求Spring容器必须不能扫描到{@link ResultAutoSuccess}，
 * 否则会破坏 SuccessResultAutoConfiguration 中关于{@link ResultAutoSuccess}的配置，
 * 所以在successtest包下进行测试。
 * 在实际使用中，是不会自动扫描到{@link ResultAutoSuccess}的。
 *
 * @author xiayx
 */
public class SuccessResultTest {

    @RunWith(SpringRunner.class)
    @SpringBootTest
    @AutoConfigureMockMvc
    public static class AutoSuccess {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void divide() throws Exception {
            this.mockMvc.perform(get("/divide?divisor=10&dividend=2")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(ResultType.success.getCode()))
                    .andExpect(jsonPath("$.message").value(ResultType.success.getMessage()))
                    .andExpect(jsonPath("$.data").value(5))
            ;
        }

    }

    @RunWith(SpringRunner.class)
    @SpringBootTest(properties = {
            "peacetrue.result.success.disabled-methods=com.github.peacetrue.result.successtest.SuccessResultController.divide"
    })
    @AutoConfigureMockMvc
    public static class DisableAutoSuccess {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void divideDisableByAnnotation() throws Exception {
            this.mockMvc.perform(get("/divideDisableAutoSuccess?divisor=10&dividend=2")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(5))
            ;
        }

        @Test
        public void divideDisableByConfiguration() throws Exception {
            this.mockMvc.perform(get("/divide?divisor=10&dividend=2")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(5))
            ;
        }
    }


}
