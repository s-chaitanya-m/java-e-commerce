package chaitanya.shinde.store.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = LowercaseValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lowercase {
    String message() default "must be Lower case";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
