package com.github.peacetrue.result;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiayx
 */
@RestController
@RequestMapping("/calculate")
public class CalculateController {

    //tag::divideSuccess[]

    /** 除法运算 */
    @RequestMapping("/divide/success")
    public Result divideSuccess(@RequestParam Integer divisor, @RequestParam Integer dividend) {
        return new DataResultImpl<>(ResultType.success, divisor / dividend);
    }
    //end::divideSuccess[]


    //tag::divide[]

    /** 除法运算 */
    @RequestMapping("/divide")
    public Result divide(@RequestParam Integer divisor, @RequestParam Integer dividend) {
        try {
            return new DataResultImpl<>(ResultType.success, divisor / dividend);
        } catch (Exception e) {
            //控制层必须捕获所有业务异常，不能向外抛出异常
            return new DataResultImpl<>(ResultType.failure, e.getMessage());
        }
    }
    //end::divide[]


}
