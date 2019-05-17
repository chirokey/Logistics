package com.logistics.dto.validation.annotation;

import com.logistics.dto.validation.validator.EnumValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface ValidEnum {

    Class<? extends Enum<?>> enumClass();

    String message() default "{enum.valid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
