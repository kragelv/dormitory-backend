package by.bsuir.dorm.validation.validators;

import by.bsuir.dorm.validation.constraints.Prefixed;
import jakarta.validation.ConstraintValidator;

public abstract class AbstractPrefixedValidator<T> implements ConstraintValidator<Prefixed, T> {
    protected String prefix;

    @Override
    public void initialize(Prefixed constraintAnnotation) {
        this.prefix = constraintAnnotation.value();
    }
}
