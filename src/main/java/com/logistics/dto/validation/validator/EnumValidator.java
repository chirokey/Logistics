package com.logistics.dto.validation.validator;

import com.logistics.dto.validation.annotation.ValidEnum;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Set<String> valueSet;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueSet.contains(value.toUpperCase());
    }

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        valueSet = new HashSet<String>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for(@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueSet.add(enumVal.toString().toUpperCase());
        }

    }

}
