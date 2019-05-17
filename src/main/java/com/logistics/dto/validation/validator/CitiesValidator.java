package com.logistics.dto.validation.validator;

import com.logistics.dto.CargoDTO;
import com.logistics.dto.validation.annotation.DifferentCities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CitiesValidator implements ConstraintValidator<DifferentCities, Object> {

    @Override
    public void initialize(DifferentCities constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        CargoDTO cargoDTO = (CargoDTO) obj;
        if (Objects.equals(cargoDTO.getLoadingCity(),cargoDTO.getUnloadingCity())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{cities.match}")
                    .addPropertyNode("loadingCity")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
