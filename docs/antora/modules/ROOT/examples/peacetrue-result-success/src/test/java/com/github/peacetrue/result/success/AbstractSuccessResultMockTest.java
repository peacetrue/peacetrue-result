package com.github.peacetrue.result.success;

import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        SuccessResultAutoConfiguration.class,
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
        mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(bodyAdvice)
                .build();
    }

}
