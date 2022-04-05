package com.github.peacetrue.result.exception.quick_start;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.GenericDataResult;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import com.github.peacetrue.result.exception.ResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.ResultExceptionSupportAutoConfiguration;
import com.github.peacetrue.result.exception.jackson.JacksonResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.persistence.PersistenceResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.spring.SpringExceptionResultAutoConfiguration;
import com.github.peacetrue.result.exception.sql.SQLResultExceptionAutoConfiguration;
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

                HttpMessageConvertersAutoConfiguration.class,
                DispatcherServletAutoConfiguration.class,
                WebMvcAutoConfiguration.class,
                ServletWebServerFactoryAutoConfiguration.class,

                ResultSuccessAutoConfiguration.class,
                ResultMessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ResultExceptionAutoConfiguration.class,
                ResultExceptionSupportAutoConfiguration.class,
                JacksonResultExceptionAutoConfiguration.class,
                SpringExceptionResultAutoConfiguration.class,
                SQLResultExceptionAutoConfiguration.class,
                PersistenceResultExceptionAutoConfiguration.class,

                UserController.class,
                UserRepository.class,
                UserNotExistExceptionConverter.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EntityScan
@EnableJpaRepositories
class UserTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static void generateDocument(String name, Object result) {
        try {
            String stringPath = SourcePathUtils.getTestResourceAbsolutePath(
                    "/com/github/peacetrue/result/exception/quick_start/", name, ".json"
            );
            Path path = Paths.get(stringPath);
            if (Files.notExists(path)) Files.createFile(path);
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(path.toFile(), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //tag::success[]
    @Test
    void success() {
        Result result = this.restTemplate.getForObject("/users?id={0}", GenericDataResult.class, "1");
        generateDocument("success", result);
        Assertions.assertTrue(ResultTypes.isSuccess(result.getCode()));
    }
    //end::success[]


    //tag::missingServletRequestParameter[]
    @Test
    void missingServletRequestParameter() {
        Result result = this.restTemplate.getForObject("/users", ResultImpl.class);
        generateDocument("missingServletRequestParameter", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_MISSING.getCode()));
    }
    //end::missingServletRequestParameter[]


    //tag::methodArgumentTypeMismatch[]
    @Test
    void methodArgumentTypeMismatch() {
        Result result = this.restTemplate.getForObject("/users?id=test", ResultImpl.class);
        generateDocument("methodArgumentTypeMismatch", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_INVALID.getCode()));
    }
    //end::methodArgumentTypeMismatch[]


    //tag::entityNotFound[]
    @Test
    void entityNotFound() {
        long idNotExists = 0L;
        Result result = this.restTemplate.getForObject("/users?id={0}", ResultImpl.class, idNotExists);
        generateDocument("entityNotFound", result);
        Assertions.assertTrue(result.getCode().startsWith(ResultTypes.PARAMETER_ILLEGAL.getCode()));
    }
    //end::entityNotFound[]


    //tag::result-exception[]
    @Test
    void login() {
        Result result = this.restTemplate.getForObject("/login/result-exception?name={0}&password={1}", ResultImpl.class, "test", "test");
        generateDocument("result-exception", result);
        Assertions.assertEquals("user_not_exist", result.getCode());
    }
    //end::result-exception[]


    //tag::custom-exception[]
    @Test
    void loginCustomException() {
        Result result = this.restTemplate.getForObject("/login/custom-exception?name={0}&password={1}", ResultImpl.class, "test", "test");
        generateDocument("custom-exception", result);
        Assertions.assertEquals("user_not_exist", result.getCode());
    }
    //end::custom-exception[]

}
