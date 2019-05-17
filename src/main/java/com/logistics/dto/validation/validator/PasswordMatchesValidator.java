package com.logistics.dto.validation.validator;

import com.logistics.dto.user.UserDTO;
import com.logistics.dto.validation.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDTO employee = (UserDTO) obj;
        if (!Objects.equals(employee.getPassword(),employee.getMatchingPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "{passwords.match}")
                    .addPropertyNode("password").addConstraintViolation();
            return false;
        }
        return true;
    }

}
