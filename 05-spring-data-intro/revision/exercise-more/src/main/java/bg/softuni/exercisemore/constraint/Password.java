package bg.softuni.exercisemore.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "Invalid password format";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int minLength();
    int maxLength();
    boolean containsLowerCase();
    boolean containsUpperCase();
    boolean containsDigit();
    boolean containsSpecialSymbol();
}
