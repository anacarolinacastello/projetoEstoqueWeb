package br.edu.projetoEstoqueWebP.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EmailValidator implements ConstraintValidator<EmailValidation, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null) return false;
        if(value.contains(" ")) return false;
        return value.matches("[\\W\\S]+[@]+[\\W\\S]+[.]+[\\W\\S]+");
    }
    
}
