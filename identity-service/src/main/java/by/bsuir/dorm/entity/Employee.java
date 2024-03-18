package by.bsuir.dorm.entity;

import by.bsuir.dorm.util.RoleUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee")
@PrimaryKeyJoinColumn(name="userId")
public class Employee extends User {
    @ManyToMany
    @JoinTable(name = "employee_employeeRoles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "employeeRoles_id"))
    private Set<EmployeeRole> employeeRoles = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return employeeRoles
                .stream()
                .map(Role::getName)
                .map(RoleUtil::mapRoleName)
                .collect(Collectors.toSet());
    }
}