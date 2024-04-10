package by.bsuir.dorm.validation.validators;

import by.bsuir.dorm.validation.constraints.NotBlankIfPresent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankIfPresentValidatorForCharSequence
        implements ConstraintValidator<NotBlankIfPresent, CharSequence> {
    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        if (charSequence == null) {
            return true;
        } else {
            return !charSequence.toString().trim().isEmpty();
        }
    }
}
