package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dao.RoleRepository;
import by.bsuir.dorm.exception.RoleNotFoundException;
import by.bsuir.dorm.model.entity.Role;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ExtensionsMapper {
    private final RoleRepository roleRepository;

    @Named("roleNameToRoleRef")
    public Role roleNameToRoleRef(String name) {
        final Role roleRef = roleRepository.getReferenceBySimpleNaturalId(name)
                .orElseThrow(() -> new RoleNotFoundException("Role { name = '" + name + "' } doesn't exist"));
        return roleRef;
    }

    @Named("collectionToSize")
    public int collectionToSize(Collection<?> collection) {
        if (collection == null)
            return 0;
        return collection.size();
    }
}
