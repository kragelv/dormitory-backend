package by.bsuir.dorm.model.listener;

import by.bsuir.dorm.exception.InvalidRoleNameException;
import by.bsuir.dorm.model.entity.Role;
import by.bsuir.dorm.util.RoleUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class RoleListener {
    @PrePersist
    @PreUpdate
    public void checkRoleNamePrefix(Role role) {
        final String name = role.getName();
        if (!RoleUtil.isPrefixedRole(name))
            throw new InvalidRoleNameException("Role must be prefixed with " + RoleUtil.ROLE_PREFIX + ": " + name);
    }
}
