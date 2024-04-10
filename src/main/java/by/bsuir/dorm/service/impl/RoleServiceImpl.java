package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.RoleRepository;
import by.bsuir.dorm.dao.UserTypeRepository;
import by.bsuir.dorm.dto.RoleDto;
import by.bsuir.dorm.exception.RoleNotFoundException;
import by.bsuir.dorm.exception.UserTypeNotFoundException;
import by.bsuir.dorm.mapper.RoleMapper;
import by.bsuir.dorm.model.entity.Role;
import by.bsuir.dorm.model.entity.UserType;
import by.bsuir.dorm.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserTypeRepository userTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getRolesByUserType(String typename) {
        UserType userType = userTypeRepository.findBySimpleNaturalId(typename.toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new UserTypeNotFoundException("User type { name = '" +
                        typename + "' } is not found"));
        return roleMapper.toDto(roleRepository.findAllByCompatibleUserTypesContainsOrderById(userType));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getRolesByUserType() {
        return roleMapper.toDto(roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto getByRoleId(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role { id = '" + id + "' } doesn't exist"));
        return roleMapper.toDto(role);
    }
}
