package by.bsuir.dorm.model.listener;

import by.bsuir.dorm.exception.RoleNotCompatibleException;
import by.bsuir.dorm.model.entity.Role;
import by.bsuir.dorm.model.entity.User;
import by.bsuir.dorm.model.entity.UserType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class UserListener {
    @PrePersist
    @PreUpdate
    public void checkRoleCompatibility(User user) {
        final String userTypeName = user.getTypename();
        final Set<Role> roles = user.getRoles();
        if (roles != null) {
            final Optional<Role> firstIncompatible = roles
                    .stream()
                    .filter(
                            role -> role.getCompatibleUserTypes()
                                    .stream()
                                    .map(UserType::getName)
                                    .anyMatch(name -> !Objects.equals(name, userTypeName))
                    )
                    .findFirst();
            firstIncompatible.ifPresent(role -> {
                throw new RoleNotCompatibleException("Role '" + role.getName() + "' is not compatible");
            });
        }
    }
}
