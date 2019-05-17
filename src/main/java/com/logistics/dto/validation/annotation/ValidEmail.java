package com.logistics.dto.validation.annotation;

import com.logistics.dto.validation.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default "{email.valid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
