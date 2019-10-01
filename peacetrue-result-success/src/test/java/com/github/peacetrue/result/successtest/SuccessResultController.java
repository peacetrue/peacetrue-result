package com.github.peacetrue.result.successtest;

import com.github.peacetrue.result.success.DisableResultAutoSuccess;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiayx
 */
@RestController
public class SuccessResultController {

    //tag::divide[]

    /** 除法运算 */
    @RequestMapping("/divide")
    public Integer divide(@RequestParam Integer divisor, @RequestParam Integer dividend) {
        return divisor / dividend;
    }
    //end::divide[]

    //tag::divideDisableAutoSuccess[]

    /** 除法运算，配置成禁用自动成功 */
    @DisableResultAutoSuccess
    @RequestMapping("/divideDisableAutoSuccess")
    public Integer divideDisableAutoSuccess(@RequestParam Integer divisor, @RequestParam Integer dividend) {
        return divisor / dividend;
    }
    //end::divideDisableAutoSuccess[]

}
