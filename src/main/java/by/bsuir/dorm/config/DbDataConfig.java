package by.bsuir.dorm.config;

import by.bsuir.dorm.dao.RoleRepository;
import by.bsuir.dorm.dao.UserTypeRepository;
import by.bsuir.dorm.mapper.RoleMapper;
import by.bsuir.dorm.mapper.UserTypeMapper;
import by.bsuir.dorm.model.entity.*;
import by.bsuir.dorm.util.RoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class DbDataConfig {
    private final UserTypeRepository userTypeRepository;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserTypeMapper userTypeMapper;
    @EventListener
    @Transactional
    public void seedData(ContextRefreshedEvent event) {
        Student studentEmpty = new Student();
        Employee employeeEmpty = new Employee();
        seedUserTypes(List.of(studentEmpty, employeeEmpty));
        List<String> employeeRoleNames = prefixRoles(List.of("ADMIN", "NIGHT_DUTY", "HEAD"));
        seedRoles(employeeRoleNames);
        List<String> studentRoleNames = prefixRoles(List.of("TEST"));
        seedRoles(studentRoleNames);
        userTypeRepository.findBySimpleNaturalId(employeeEmpty.getTypename()).ifPresent(employee ->
                seedAssociationRoleUserType(employee, employeeRoleNames));
        userTypeRepository.findBySimpleNaturalId(studentEmpty.getTypename()).ifPresent(student ->
                seedAssociationRoleUserType(student, studentRoleNames));
    }

    private List<String> prefixRoles(List<String> roles) {
        return roles
                .stream()
                .map(RoleUtil::prefixRoleName)
                .collect(Collectors.toList());
    }

    private void seedUserTypes(List<User> users) {
        userTypeRepository.saveAll(
                users
                        .stream()
                        .map(User::getTypename)
                        .distinct()
                        .filter(typename -> userTypeRepository.findBySimpleNaturalId(typename).isEmpty())
                        .map(userTypeMapper::toEntity)
                        .collect(Collectors.toSet())
        );
    }

    private void seedRoles(List<String> roleNames) {
        roleRepository.saveAll(
                roleNames
                        .stream()
                        .filter(roleName -> roleRepository.findBySimpleNaturalId(roleName).isEmpty())
                        .map(roleMapper::toEntity)
                        .collect(Collectors.toSet())
        );
    }

    private void seedAssociationRoleUserType(UserType userType, List<String> roleNames) {
        Set<Role> associationRoles = roleNames
                .stream()
                .map(roleRepository::getReferenceBySimpleNaturalId)
                .collect(Collectors.toSet());
        for (Role associationRole : associationRoles) {
            userType.addRole(associationRole);
        }
    }
}
