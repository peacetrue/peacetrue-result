package com.github.peacetrue.result.success;

import com.github.peacetrue.result.ResultTypes;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author peace
 **/
class SuccessAutowireMockTest extends AbstractSuccessAutowireMockTest {

    @Test
    void enableSuccessAutowire() throws Exception {
        String input = "1";
        this.mockMvc.perform(get("/enableSuccessAutowire?input={0}", input)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultTypes.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResultTypes.SUCCESS.getName()))
                .andExpect(jsonPath("$.data").value(input))
        ;

        this.mockMvc.perform(get("/enableSuccessAutowire?input={0}", input)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultTypes.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value("Successful operation"))
                .andExpect(jsonPath("$.data").value(input))
        ;
    }

    @Test
    void disableSuccessAutowireByAnnotation() throws Exception {
        this.mockMvc.perform(get("/disableSuccessAutowireByAnnotation?input=1")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1))
        ;
    }

    @Test
    void disableSuccessAutowireByConfiguration() throws Exception {
        this.mockMvc.perform(get("/disableSuccessAutowireByConfiguration?input=1")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1))
        ;
    }

    @ActiveProfiles("custom-messages")
    static class CustomMessage extends AbstractSuccessAutowireMockTest {
        @Test
        void customMessage() throws Exception {
            String input = "1";
            this.mockMvc.perform(get("/enableSuccessAutowire?input={0}", input)
                    .header(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.5")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(ResultTypes.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.message").value(Matchers.not(ResultTypes.SUCCESS.getName())))
                    .andExpect(jsonPath("$.data").value(input))
            ;
        }
    }

    @ActiveProfiles("custom-code-prefix")
    static class CustomCodePrefix extends AbstractSuccessAutowireMockTest {
        @Test
        void customCodePrefix() throws Exception {
            String input = "1";
            mockMvc.perform(get("/enableSuccessAutowire?input={0}", input)
                    .header(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.5")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(ResultTypes.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.message").value(Matchers.not(ResultTypes.SUCCESS.getName())))
                    .andExpect(jsonPath("$.data").value(input))
            ;
        }
    }


}
