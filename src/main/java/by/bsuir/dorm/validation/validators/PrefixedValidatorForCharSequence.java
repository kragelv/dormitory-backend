package by.bsuir.dorm.validation.validators;


import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PrefixedValidatorForCharSequence extends AbstractPrefixedValidator<CharSequence> {
    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        if (charSequence == null)
            return false;
        int end = Math.min(prefix.length(), charSequence.length());
        return Objects.equals(charSequence.subSequence(0, end), prefix);
    }
}
