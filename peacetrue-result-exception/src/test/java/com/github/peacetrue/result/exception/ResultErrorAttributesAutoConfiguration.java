//package com.github.peacetrue.result.exception;
//
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.web.ErrorAttributes;
//import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 响应结果异常属性自动配置。支持 spring 4.x 版本，不支持 spring 5.x 版本。
// *
// * @author peace
// **/
//@Configuration
//@ConditionalOnClass(ErrorMvcAutoConfiguration.class)
//@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
//public class ResultErrorAttributesAutoConfiguration {
//    @Bean
//    @ConditionalOnMissingBean(ErrorAttributes.class)
//    public ErrorAttributes resultErrorAttributes() {
//        return new ResultErrorAttributes();
//    }
//}
