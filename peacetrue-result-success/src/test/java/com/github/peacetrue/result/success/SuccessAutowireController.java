package com.github.peacetrue.result.success;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;

/**
 * @author peace
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SuccessAutowireController {

    //tag::divide[]

    /** 除法运算 */
    @RequestMapping(value = "/divide")
    public Integer divide(@RequestParam Integer divisor, @RequestParam Integer dividend) {
        return divisor / dividend;
    }
    //end::divide[]


//    /** 除法运算 */
//    @RequestMapping("/divide/mono")
//    public Mono<Integer> divideMono(@RequestParam Integer divisor, @RequestParam Integer dividend) {
//        return Mono.just(divisor / dividend);
//    }

    //tag::divideDisableAutoSuccess[]

    /** 除法运算，禁用自动装配 */
    @SuccessAutowire
    @RequestMapping(value = "/divideDisableSuccessAutowire")
    public Integer divideDisableAutoSuccess(@RequestParam Integer divisor, @RequestParam Integer dividend) {
        return divisor / dividend;
    }
    //end::divideDisableAutoSuccess[]

}
