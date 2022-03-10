package com.github.peacetrue.result.success;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.ResultException;
import com.github.peacetrue.result.ResultTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.function.Function;

/**
 * //TODO 不知道具体需要使用哪些 AutoConfiguration 类，所以使用了 SuccessAutowireTestApplication
 *
 * @author peace
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuccessAutowireClientTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void divide() {
        //修改 RestTemplate 接口，使其支持构造类型，类似 objectMapper.getTypeFactory().constructArrayType()
        //手动根据 DataResultImpl + 具体类型 = 构造包装类型
        //不指定类型，使用 RestTemplate 的默认转换，然后手动强制转换
        Integer divisor = 10, dividend = 1, remainder = divisor / dividend;
        Integer actualRemainder = execute(resultType -> this.restTemplate.getForObject(
                "/divide?divisor={0}&dividend={1}", resultType, divisor, dividend
        ), Integer.class);
        Assertions.assertEquals(remainder, actualRemainder);
    }

    public <T> T execute(Function<Class<?>, ?> invoker, Class<T> responseType) {
        DataResultImpl<?> result = (DataResultImpl<?>) invoker.apply(DataResultImpl.class);
        if (!ResultTypes.isSuccess(result.getCode())) throw new ResultException(result);
        return objectMapper.convertValue(result.getData(), responseType);
    }

    @Test
    void divideDisableSuccessAutowire() {
        Integer divisor = 10, dividend = 1, remainder = divisor / dividend;
        Integer actualRemainder = this.restTemplate.getForObject(
                "/divideDisableSuccessAutowire?divisor={0}&dividend={1}",
                Integer.class, divisor, dividend
        );
        Assertions.assertEquals(remainder, actualRemainder);
    }

}
