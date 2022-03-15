package com.github.peacetrue.result.success;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peace
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SuccessAutowireTestController {

    //tag::enableSuccessAutowire[]

    /** 启用成功自动封装 */
    @RequestMapping("/enableSuccessAutowire")
    public String enableSuccessAutowire(String input) {
        return input;
    }
    //end::enableSuccessAutowire[]


//    /** 除法运算 */
//    @RequestMapping("/divide/mono")
//    public Mono<Integer> divideMono(@RequestParam Integer divisor, @RequestParam Integer dividend) {
//        return Mono.just(divisor / dividend);
//    }

    //tag::disableSuccessAutowireByAnnotation[]

    /** 通过注解禁用成功自动封装 */
    @SuccessAutowire
    @RequestMapping(value = "/disableSuccessAutowireByAnnotation")
    public String disableSuccessAutowireByAnnotation(String input) {
        return input;
    }
    //end::disableSuccessAutowireByAnnotation[]

    //tag::disableSuccessAutowireByConfiguration[]

    /** 通过配置禁用成功自动封装 */
    @SuccessAutowire
    @RequestMapping(value = "/disableSuccessAutowireByConfiguration")
    public String disableSuccessAutowireByConfiguration(String input) {
        return input;
    }
    //end::disableSuccessAutowireByConfiguration[]

}
