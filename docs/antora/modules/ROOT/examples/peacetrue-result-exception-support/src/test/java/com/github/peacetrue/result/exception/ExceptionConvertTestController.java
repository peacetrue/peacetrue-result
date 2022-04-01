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

    @RequestMapping("/missingServletRequestParameter")
    public String missingServletRequestParameter(@RequestParam String input) {
        return input;
    }
    //end::missingServletRequestParameter[]


    // tag::missingPathVariable[]

    @RequestMapping("/missingPathVariable")
    public String missingPathVariable(@PathVariable String input) {
        return input;
    }
    //end::missingPathVariable[]

    //tag::methodArgumentTypeMismatch[]

    @RequestMapping("/methodArgumentTypeMismatch")
    public Integer methodArgumentTypeMismatch(@RequestParam Integer input) {
        return input;
    }
    //end::methodArgumentTypeMismatch[]

    //tag::beanInvalid[]

    @ResponseBody
    @RequestMapping("/beanInvalid")
    public TestBean beanInvalid(@Validated TestBean bean) {
        return bean;
    }
    //end::beanInvalid[]

    //tag::methodArgumentNotValid[]

    @ResponseBody
    @RequestMapping("/methodArgumentNotValid")
    public TestBean methodArgumentNotValid(@Validated @RequestBody TestBean bean) {
        return bean;
    }
    //end::methodArgumentNotValid[]

    @Autowired
    private ExceptionConvertTestService testService;

    //tag::methodArgumentNotValid[]

    @ResponseBody
    @RequestMapping("/entityNotFound")
    public void entityNotFound() {
        testService.entityNotFound();
    }
    //end::entityNotFound[]

    //tag::duplicate[]

    @ResponseBody
    @RequestMapping("/duplicate")
    public void duplicate() {
        testService.duplicate();
    }
    //end::duplicate[]

}
