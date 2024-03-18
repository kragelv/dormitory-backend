package by.bsuir.dorm.config;

import by.bsuir.dorm.dao.EmployeeRoleRepository;
import by.bsuir.dorm.dao.StudentRoleRepository;
import by.bsuir.dorm.entity.EmployeeRole;
import by.bsuir.dorm.entity.StudentRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class DbDataConfig {

    private final StudentRoleRepository studentRoleRepository;
    private final EmployeeRoleRepository employeeRoleRepository;

    @EventListener
    public void seedData(ContextRefreshedEvent event) {
        List<String> studentRoles = List.of("STUDENT", "NIGHT_DUTY");
        List<String> employeeRoles = List.of("EMPLOYEE", "TEACHER", "ADMIN");
        studentRoleRepository.saveAll(studentRoles.stream().map(s -> {
            StudentRole role = new StudentRole();
            role.setName(s);
            return role;
        }).collect(Collectors.toList()));
        employeeRoleRepository.saveAll(employeeRoles.stream().map(s -> {
            EmployeeRole role = new EmployeeRole();
            role.setName(s);
            return role;
        }).collect(Collectors.toList()));
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(
                "ROLE_NIGHT_DUTY > ROLE_STUDENT\n" +
                "ROLE_ADMIN > ROLE_NIGHT_DUTY\n" +
                "ROLE_TEACHER > ROLE_EMPLOYEE\n" +
                "ROLE_ADMIN > ROLE_TEACHER"
        );
        return roleHierarchy;
    }
}
