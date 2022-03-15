package com.github.peacetrue.result;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author peace
 */
@Controller
@RequestMapping
public class AutoResultController {

    //tag::success[]
    @ResponseBody
    @RequestMapping("/success")
    public String success() {
        return "success";
    }
    //end::success[]

    //tag::failure[]
    @ResponseBody
    @RequestMapping("/failure")
    public Integer failure() {
        //本来正常是要返回一个整数的，中途处理过程中发生了一些异常
        return 1 / 0;
    }
    //end::failure[]

    //tag::parameter_missing[]
    @ResponseBody
    @RequestMapping("/parameter_missing")
    public String parameterMissing(@RequestParam String required) {
        return required;
    }
    //end::parameter_missing[]

}
