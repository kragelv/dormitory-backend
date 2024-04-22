package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dao.ContractRepository;
import by.bsuir.dorm.dao.GroupRepository;
import by.bsuir.dorm.dao.RoleRepository;
import by.bsuir.dorm.dto.RoomInStudentDto;
import by.bsuir.dorm.exception.GroupNotFoundException;
import by.bsuir.dorm.exception.RoleNotFoundException;
import by.bsuir.dorm.model.entity.Contract;
import by.bsuir.dorm.model.entity.Group;
import by.bsuir.dorm.model.entity.Role;
import by.bsuir.dorm.model.entity.Student;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExtensionsMapper {
    private final ContractMapper contractMapper;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final ContractRepository contractRepository;

    @Named("roleNameToRoleRef")
    public Role roleNameToRoleRef(String name) {
        final Role roleRef = roleRepository.getReferenceBySimpleNaturalId(name)
                .orElseThrow(() -> new RoleNotFoundException("Role { name = '" + name + "' } doesn't exist"));
        return roleRef;
    }

    @Named("groupNumberToGroupRef")
    public Group groupNumberToGroupRef(String number) {
        final Group groupRef = groupRepository.getReferenceBySimpleNaturalId(number)
                .orElseThrow(() -> new GroupNotFoundException("Group { number = '" + number + "' } doesn't exist"));
        return groupRef;
    }

    @Named("collectionToSize")
    public int collectionToSize(Collection<?> collection) {
        if (collection == null)
            return 0;
        return collection.size();
    }

    @Named("getStudentRoom")
    public RoomInStudentDto getStudentRoom(Student student) {
        final Optional<Contract> activeContract = contractRepository.findActiveContractByStudent(student);
        if (activeContract.isEmpty()) {
            return null;
        }
        final Contract contract = activeContract.get();
        final Integer number = contract.getRoom().getNumber();
        return new RoomInStudentDto(number, contractMapper.toInStudentRoomDto(contract));
    }
}
