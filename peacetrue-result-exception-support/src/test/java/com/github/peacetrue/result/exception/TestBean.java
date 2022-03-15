package com.github.peacetrue.result.exception;

import com.github.peacetrue.validation.constraints.in.In;
import com.github.peacetrue.validation.constraints.unique.Unique;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author peace
 *
 **/
//tag::parameter_range_error_bean[]
@Data
@Unique(check = UniqueCheckImpl.class, id = "id", unique = "uniqueByBean")
public class TestBean {

    private Long id;
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
    @In(values = {"a", "b"})
    private String in;
    @Unique(check = UniqueCheckImpl.class)
    private String uniqueByProperty;
    private String uniqueByBean;
}
//end::parameter_range_error_bean[]
