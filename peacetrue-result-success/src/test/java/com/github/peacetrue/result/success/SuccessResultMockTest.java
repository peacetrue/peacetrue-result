package com.github.peacetrue.result.success;

import com.github.peacetrue.result.ResultTypes;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author peace
 **/
class SuccessResultMockTest extends AbstractSuccessResultMockTest {

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
                .andExpect(jsonPath("$.message").value("The operation succeeded"))
                .andExpect(jsonPath("$.data").value(input))
        ;

        input = "ResponseEntityOk";
        this.mockMvc.perform(get("/enableSuccessAutowire?input={0}", input)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultTypes.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value("The operation succeeded"))
                .andExpect(jsonPath("$.data").value(input))
        ;

        input = "ResponseEntityError";
        this.mockMvc.perform(get("/enableSuccessAutowire?input={0}", input)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(input))
        ;

        input = "1";
        buildMockMvc(new StringHttpMessageConverter())
                .perform(get("/enableSuccessAutowire?input={0}", input)
                        .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(input));
    }

    @Test
    void disableSuccessAutowireByResultType() throws Exception {
        int input = 1;
        this.mockMvc.perform(get("/disableSuccessAutowireByResultType")
                .param("input", String.valueOf(input))
                .header(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(input))
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

    @ActiveProfiles("disabled-methods")
    static class DisabledMethods extends AbstractSuccessResultMockTest {
        @Test
        void disableSuccessAutowireByConfiguration() throws Exception {
            String input = "1";
            this.mockMvc.perform(get("/disableSuccessAutowireByConfiguration")
                    .param("input", input)
                    .header(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.5")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(input))
            ;
        }
    }

    @ActiveProfiles("custom-messages")
    static class CustomMessage extends AbstractSuccessResultMockTest {
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
    static class CustomCodePrefix extends AbstractSuccessResultMockTest {
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
