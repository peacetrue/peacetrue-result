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

    //tag::divide[]

    /** 除法运算 */
    @RequestMapping("/divide")
    public Integer divide(@RequestParam Integer divisor, @RequestParam Integer dividend) {
        return divisor / dividend;
    }
    //end::divide[]


}
