package bg.softuni.exercisemore.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private int minLength;
    private int maxLength;
    private boolean containsLowerCase;
    private boolean containsUpperCase;
    private boolean containsDigit;
    private boolean containsSpecialSymbol;

    @Override
    public void initialize(Password constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.maxLength = constraintAnnotation.maxLength();
        this.containsLowerCase = constraintAnnotation.containsLowerCase();
        this.containsUpperCase = constraintAnnotation.containsUpperCase();
        this.containsDigit = constraintAnnotation.containsDigit();
        this.containsSpecialSymbol = constraintAnnotation.containsSpecialSymbol();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+<>?]).{6,50}$");
    }
}
