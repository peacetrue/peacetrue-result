package com.github.peacetrue.result.success;

import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author peace
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        ResultMessageSourceAutoConfiguration.class,
        ResultBuilderAutoConfiguration.class,
        SuccessAutowireAutoConfiguration.class,
        SuccessAutowireTestController.class
})
class SuccessAutowireMockTest {

    protected MockMvc mockMvc;
    @Autowired
    private SuccessAutowireTestController testController;
    @Autowired
    private SuccessAutowireResponseBodyAdvice bodyAdvice;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(bodyAdvice)
                .build();
    }

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
    public static class CustomMessage extends SuccessAutowireMockTest {
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
    static class CustomCodePrefix extends SuccessAutowireMockTest {
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
