package com.github.peacetrue.result.success;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peace
 */
@RestController
@RequestMapping
public class SuccessResultTestController {

    //tag::enableSuccessAutowire[]

    /** 启用成功自动封装 */
    @ResponseBody
    @RequestMapping(value = "/enableSuccessAutowire", produces = MediaType.APPLICATION_JSON_VALUE)
    public String enableSuccessAutowire(String input) {
        return input;
    }
    //end::enableSuccessAutowire[]

    //tag::disableSuccessAutowireByResultType[]

    /** 通过配置禁用成功自动封装 */
    @ResponseBody
    @RequestMapping(value = "/disableSuccessAutowireByResultType")
    public Integer disableSuccessAutowireByResultType(String input) {
        return Integer.parseInt(input);
    }
    //end::disableSuccessAutowireByResultType[]


    //tag::disableSuccessAutowireByConfiguration[]

    /** 通过配置禁用成功自动封装 */
    @ResponseBody
    @RequestMapping(value = "/disableSuccessAutowireByConfiguration")
    public String disableSuccessAutowireByConfiguration(String input) {
        return input;
    }
    //end::disableSuccessAutowireByConfiguration[]

    //tag::disableSuccessAutowireByAnnotation[]

    /** 通过注解禁用成功自动封装 */
    @SuccessAutowire(disabled = true)
    @ResponseBody
    @RequestMapping(value = "/disableSuccessAutowireByAnnotation")
    public String disableSuccessAutowireByAnnotation(String input) {
        return input;
    }
    //end::disableSuccessAutowireByAnnotation[]

}
