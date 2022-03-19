package com.github.peacetrue.result.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author peace
 */
@RestController
public class ExceptionConvertTestController {

    //tag::missingServletRequestParameter[]

    /** 除法运算 */
    @RequestMapping("/missingServletRequestParameter")
    public String missingServletRequestParameter(@RequestParam String input) {
        return input;
    }
    //end::missingServletRequestParameter[]


    // tag::divide[]

    /** 除法运算 */
    @RequestMapping("/missingPathVariable")
    public String missingPathVariable(@PathVariable String input) {
        return input;
    }
    //end::divide[]

    //tag::missingServletRequestParameter[]

    /** 除法运算 */
    @RequestMapping("/methodArgumentTypeMismatch")
    public Integer methodArgumentTypeMismatch(@RequestParam Integer input) {
        return input;
    }
    //end::missingServletRequestParameter[]

    /** Bean 参数非法（form） */
    @ResponseBody
    @RequestMapping("/bindException")
    public TestBean bindException(@Validated TestBean bean) {
        return bean;
    }

    /** Bean 参数非法（body） */
    @ResponseBody
    @RequestMapping("/methodArgumentNotValid")
    public TestBean methodArgumentNotValid(@Validated @RequestBody TestBean bean) {
        return bean;
    }

    @Autowired
    private ExceptionConvertTestService testService;

    /** 重复 SQL 异常 */
    @ResponseBody
    @RequestMapping("/duplicateSQLException")
    public void duplicateSQLException() {
        testService.duplicateSQLException();
    }

}
