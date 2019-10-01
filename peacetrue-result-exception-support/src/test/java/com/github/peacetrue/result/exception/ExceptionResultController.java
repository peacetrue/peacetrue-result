package com.github.peacetrue.result.exception;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author xiayx
 */
@RestController
public class ExceptionResultController {

    //tag::divide[]

    /** 除法运算 */
    @RequestMapping("/divide")
    public Integer divide(@RequestParam Integer divisor, @RequestParam Integer dividend) {
        return divisor / dividend;
    }
    //end::divide[]


    // tag::divide[]

    /** 除法运算 */
    @RequestMapping("/divide/path/variable")
    public Integer divideByPathVariable(@PathVariable Integer divisor, @PathVariable Integer dividend) {
        return divisor / dividend;
    }
    //end::divide[]

    //tag::parameter_range_error_bean[]
    @Data
    public static class Bean2 {
        @Size(min = 1, max = 2)
        private String string;
        @Min(0)
        @Max(2)
        private Byte bytes;
        @Min(0)
        @Max(2)
        private Short shorts;
        @Min(0)
        @Max(2)
        private Integer integer;
        @Min(0)
        @Max(2)
        private Long longs;
        @Min(0)
        @Max(2)
        private Float floats;
        @Min(0)
        @Max(2)
        private Double doubles;
        @AssertFalse
        private Boolean booleans;
        @Past
        private Date date;
    }

    /** Bean 参数范围错误 */
    @ResponseBody
    @RequestMapping("/parameter_range_error_bean")
    public Bean2 parameterRangeErrorBean(@Validated Bean2 bean) {return bean;}
    //end::parameter_range_error_bean[]

}
