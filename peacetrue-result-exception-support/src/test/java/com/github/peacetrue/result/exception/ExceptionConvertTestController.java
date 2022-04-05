package com.github.peacetrue.result.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author peace
 */
@RestController
public class ExceptionConvertTestController {


    @Autowired
    private ExceptionConvertTestService testService;


    @Autowired
    private ResultExceptionThrowService exceptionThrowService;

    //tag::missingServletRequestParameter[]

    @RequestMapping("/missingServletRequestParameter")
    public String missingServletRequestParameter(@RequestParam String input) {
        return input;
    }
    //end::missingServletRequestParameter[]

    //tag::missingPathVariable[]

    /** 路径中未包含 {input}，服务端异常 */
    @RequestMapping("/missingPathVariable")
    public String missingPathVariable(@PathVariable String input) {
        return input;
    }
    //end::missingPathVariable[]

    //tag::methodArgumentConversionNotSupported[]


    /** 不支持转换 TestBean，服务端异常 */
    @RequestMapping("/methodArgumentConversionNotSupported")
    public TestBean methodArgumentConversionNotSupported(@RequestParam TestBean input) {
        return input;
    }
    //end::methodArgumentConversionNotSupported[]

    //tag::methodArgumentTypeMismatch[]


    @RequestMapping("/methodArgumentTypeMismatch")
    public Integer methodArgumentTypeMismatch(@RequestParam Integer input) {
        return input;
    }
    //end::methodArgumentTypeMismatch[]


    //tag::bind[]

    @ResponseBody
    @RequestMapping("/bind")
    public TestBean bind(@Validated TestBean bean) {
        return bean;
    }
    //end::bind[]

    //tag::httpMessageNotReadable[]

    @ResponseBody
    @RequestMapping("/httpMessageNotReadable")
    public TestBean httpMessageNotReadable(@RequestBody TestBean bean) {
        return bean;
    }
    //end::httpMessageNotReadable[]

    //tag::methodArgumentNotValid[]

    @ResponseBody
    @RequestMapping("/methodArgumentNotValid")
    public TestBean methodArgumentNotValid(@Validated @RequestBody TestBean bean) {
        return bean;
    }
    //end::methodArgumentNotValid[]

    //tag::entityNotFound[]

    @ResponseBody
    @RequestMapping("/entityNotFound")
    public void entityNotFound(@RequestParam Long id) {
        testService.entityNotFound(id);
    }
    //end::entityNotFound[]

    //tag::duplicate[]

    @ResponseBody
    @RequestMapping("/duplicate")
    public void duplicate(TestEntity entity) {
        testService.duplicate(entity);
    }
    //end::duplicate[]

    //tag::userDisabled[]

    @ResponseBody
    @RequestMapping("/userDisabled")
    public void userDisabled() {
        exceptionThrowService.throwDataResultException("user_disabled", new Object[]{1L});
    }
    //end::userDisabled[]
}
