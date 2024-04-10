package by.bsuir.dorm.validation.validators;

import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;
import java.util.Objects;

public class PrefixedValidatorForCollection extends AbstractPrefixedValidator<Collection<? extends String>> {
    @Override
    public boolean isValid(Collection<? extends String> collection, ConstraintValidatorContext constraintValidatorContext) {
        if (collection == null)
            return true;
        for (CharSequence element : collection) {
            int end = Math.min(prefix.length(), element.length());
            if (!Objects.equals(element.subSequence(0, end), prefix)) {
                return false;
            }
        }
        return true;
    }
}
