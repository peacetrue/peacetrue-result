package com.github.peacetrue.result.success;

import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author peace
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        ResultMessageSourceAutoConfiguration.class,
        ResultBuilderAutoConfiguration.class,
        ResultSuccessAutoConfiguration.class,
        SuccessResultTestController.class
})
public class AbstractSuccessResultMockTest {

    protected MockMvc mockMvc;
    @Autowired
    protected SuccessResultTestController testController;
    @Autowired
    protected SuccessAutowireResponseBodyAdvice bodyAdvice;

    @BeforeEach
    void setUp() {
        mockMvc = buildMockMvc(new MappingJackson2HttpMessageConverter());
    }

    protected MockMvc buildMockMvc(HttpMessageConverter<?> messageConverter) {
        return MockMvcBuilders.standaloneSetup(testController)
                .setMessageConverters(messageConverter)
                .setControllerAdvice(bodyAdvice)
                .build();
    }

}
