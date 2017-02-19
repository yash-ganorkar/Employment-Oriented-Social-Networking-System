/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.yganorkar;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * Custom Bean Validator. Checks for the blank/empty field.
 * @author Yash Ravindra Ganorkar (A20373298)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BlankValidator.BlankFieldValidator.class)
@Documented
public @interface BlankValidator {
    
        String message() default "Field cannot be empty.";

      Class<?>[] groups() default {};
      Class<? extends Payload>[] payload() default {};
        

    
    public  class BlankFieldValidator implements ConstraintValidator<BlankValidator, String>{

        public BlankFieldValidator() {
        }

        @Override
        public void initialize(BlankValidator constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return !"".equals(value);
        }
    }
    
}
