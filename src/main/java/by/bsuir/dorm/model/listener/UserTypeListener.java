package by.bsuir.dorm.model.listener;

import by.bsuir.dorm.exception.InvalidUserTypeException;
import by.bsuir.dorm.model.entity.UserType;
import by.bsuir.dorm.util.UserTypeUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserTypeListener {
    @PrePersist
    @PreUpdate
    public void checkUserTypeNamePrefix(UserType userType) {
        final String name = userType.getName();
        if (!UserTypeUtil.isPrefixedUserTypeName(name))
            throw new InvalidUserTypeException("UserType name must be prefixed with "
                    + UserTypeUtil.USER_TYPE_PREFIX + ": " + name);
    }
}
