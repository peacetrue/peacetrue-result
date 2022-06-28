//package com.github.peacetrue.result.exception.signature;
//
//import com.github.peacetrue.range.LongRange;
//import com.github.peacetrue.result.Result;
//import com.github.peacetrue.result.ResultTypes;
//import com.github.peacetrue.result.ResultUtils;
//import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
//import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
//import com.github.peacetrue.result.exception.ResultExceptionAutoConfiguration;
//import com.github.peacetrue.result.exception.ResultExceptionSupportAutoConfiguration;
//import com.github.peacetrue.signature.ClientInvalidException;
//import com.github.peacetrue.signature.NonceInvalidException;
//import com.github.peacetrue.signature.SignatureInvalidException;
//import com.github.peacetrue.signature.TimestampInvalidException;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Map;
//import java.util.Objects;
//
///**
// * @author peace
// **/
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {
//        ResultMessageSourceAutoConfiguration.class,
//        ResultBuilderAutoConfiguration.class,
//
//        ResultExceptionAutoConfiguration.class,
//        ResultExceptionSupportAutoConfiguration.class,
//        SignatureResultExceptionAutoConfiguration.class
//})
//@Slf4j
//class SignatureResultExceptionTest {
//
//    @Autowired
//    private ClientInvalidExceptionConverter clientInvalidExceptionConverter;
//    @Autowired
//    private TimestampInvalidExceptionConverter timestampInvalidExceptionConverter;
//    @Autowired
//    private NonceInvalidExceptionConverter nonceInvalidExceptionConverter;
//    @Autowired
//    private SignatureInvalidExceptionConverter signatureInvalidExceptionConverter;
//
//    @Test
//    void clientInvalidExceptionConverter() {
//        String clientId = "test";
//        Result result = clientInvalidExceptionConverter.convert(new ClientInvalidException(clientId));
//        Assertions.assertEquals(ResultTypes.PARAMETER_ILLEGAL.getCode(), clientInvalidExceptionConverter.getSupperCode());
//        Assertions.assertEquals(clientId, Objects.requireNonNull(ResultUtils.<Map<?, ?>>getData(result)).get("clientId"));
//    }
//
//    @Test
//    void timestampInvalidExceptionConverter() {
//        long clientTimestamp = System.currentTimeMillis();
//        long serverTimestamp = clientTimestamp + 2000L;
//        LongRange timestampOffset = new LongRange(-1000L, 1000L);
//        Assertions.assertEquals(ResultTypes.PARAMETER_ILLEGAL.getCode(), timestampInvalidExceptionConverter.getSupperCode());
//        Result result = timestampInvalidExceptionConverter.convert(new TimestampInvalidException(clientTimestamp, serverTimestamp, timestampOffset));
//        Assertions.assertEquals(timestampOffset.toString(), Objects.requireNonNull(ResultUtils.<Map<?, ?>>getData(result)).get("timestampOffset"));
//    }
//
//    @Test
//    void nonceInvalidExceptionConverter() {
//        Assertions.assertEquals(ResultTypes.PARAMETER_ILLEGAL.getCode(), nonceInvalidExceptionConverter.getSupperCode());
//        String nonce = "test";
//        Result result = nonceInvalidExceptionConverter.convert(new NonceInvalidException(nonce));
//        Assertions.assertEquals("NonceInvalid", result.getCode());
//        Assertions.assertEquals(nonce, Objects.requireNonNull(ResultUtils.<Map<?, ?>>getData(result)).get("nonce"));
//    }
//
//    @Test
//    void signatureInvalidExceptionConverter() {
//        Assertions.assertEquals(ResultTypes.PARAMETER_ILLEGAL.getCode(), signatureInvalidExceptionConverter.getSupperCode());
//        String signature = "test", toBeSignedMessage = "test";
//        Result result = signatureInvalidExceptionConverter.convert(new SignatureInvalidException(signature, toBeSignedMessage));
//        Assertions.assertEquals("SignatureInvalid", result.getCode());
//        Assertions.assertEquals(signature, Objects.requireNonNull(ResultUtils.<Map<?, ?>>getData(result)).get("signature"));
//    }
//}
