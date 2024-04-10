package by.bsuir.dorm.validation.constraints;

import by.bsuir.dorm.validation.validators.PrefixedValidatorForCharSequence;
import by.bsuir.dorm.validation.validators.PrefixedValidatorForCollection;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {
                PrefixedValidatorForCharSequence.class,
                PrefixedValidatorForCollection.class
        }
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Prefixed {
    String message() default "{by.bsuir.dorm.validation.Prefixed.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value() default "";
}
