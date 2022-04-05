package com.github.peacetrue.result.exception.user_manual;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import com.github.peacetrue.result.exception.ResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.quick_start.User;
import com.github.peacetrue.result.exception.quick_start.UserController;
import com.github.peacetrue.result.exception.quick_start.UserRepository;
import com.github.peacetrue.result.success.ResultSuccessAutoConfiguration;
import com.github.peacetrue.test.SourcePathUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author peace
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = {
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                TransactionAutoConfiguration.class,
                TestEntityManagerAutoConfiguration.class,

                ServletWebServerFactoryAutoConfiguration.class,
                DispatcherServletAutoConfiguration.class,
                WebMvcAutoConfiguration.class,
                HttpMessageConvertersAutoConfiguration.class,

                ResultMessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ResultSuccessAutoConfiguration.class,
                ResultExceptionAutoConfiguration.class,
                MissingServletRequestParameterExceptionConverter.class,

                UserRepository.class,
                UserController.class,
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
class UserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static void generateDocument(String name, Object result) {
        try {
            String stringPath = SourcePathUtils.getTestResourceAbsolutePath(
                    "/com/github/peacetrue/result/exception/user_manual/", name, ".json"
            );
            Path path = Paths.get(stringPath);
            if (Files.notExists(path.getParent())) Files.createDirectories(path.getParent());
            if (Files.notExists(path)) Files.createFile(path);
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(path.toFile(), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //tag::missingServletRequestParameter[]
    @Test
    void missingServletRequestParameter() {
        Result result = this.restTemplate.getForObject("/users", ResultImpl.class);
        generateDocument("missingServletRequestParameter", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_MISSING.getCode()));
    }
    //end::missingServletRequestParameter[]

}
