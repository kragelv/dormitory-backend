package by.bsuir.dorm.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = NotBlankIfPresentValidator.class
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresent {
    String message() default "{by.bsuir.dorm.by.bsuir.dorm.validation.NotBlankIfPresent.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
