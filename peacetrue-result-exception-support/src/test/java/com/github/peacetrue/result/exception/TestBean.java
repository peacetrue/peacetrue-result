package com.github.peacetrue.result.exception;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author peace
 **/
//tag::TestBean[]
@Data
public class TestBean {

    @AssertFalse
    private Boolean assertFalse;
    @AssertTrue
    private Boolean assertTrue;
    @DecimalMin("0.0")
    private BigDecimal decimalMin;
    @DecimalMax("0.0")
    private BigDecimal decimalMax;
    @Digits(integer = 1, fraction = 1)
    private BigDecimal digits;
    @Email
    private String email;
    @Future
    private Date future;
    @FutureOrPresent
    private Date futureOrPresent;
    @Min(0)
    private Integer min;
    @Max(0)
    private Integer max;
    @Negative()
    private Integer negative;
    @NegativeOrZero()
    private Integer negativeOrZero;
    @NotBlank()
    private String notBlank;
    @NotEmpty()
    private String notEmpty;
    @NotNull()
    private Integer notNull;
    @Null
    private Integer nulls;
    @Past
    private Date past;
    @PastOrPresent
    private Date pastOrPresent;
    @Pattern(regexp = "\\w")
    private String pattern;
    @Positive
    private Integer positive;
    @PositiveOrZero
    private Integer positiveOrZero;
    @Size(max = 1)
    private String size;
    private List<@Valid TestBean> testBeans;
}
//end::TestBean[]
