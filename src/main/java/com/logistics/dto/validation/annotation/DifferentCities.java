package com.logistics.dto.validation.annotation;

import com.logistics.dto.validation.validator.CitiesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CitiesValidator.class)
@Documented
public @interface DifferentCities {
    String message() default "{cities.match}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
