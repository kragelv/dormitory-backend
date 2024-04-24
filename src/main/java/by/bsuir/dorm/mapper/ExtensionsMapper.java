package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dao.ContractRepository;
import by.bsuir.dorm.dao.GroupRepository;
import by.bsuir.dorm.dao.RoleRepository;
import by.bsuir.dorm.dto.ReportingNoteDto;
import by.bsuir.dorm.dto.RoomInStudentDto;
import by.bsuir.dorm.dto.request.ReportingNoteCreateRequestDto;
import by.bsuir.dorm.exception.GroupNotFoundException;
import by.bsuir.dorm.exception.RoleNotFoundException;
import by.bsuir.dorm.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
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
        final Optional<Contract> activeContract = contractRepository.findByStudentAndActive(student);
        if (activeContract.isEmpty()) {
            return null;
        }
        final Contract contract = activeContract.get();
        final Integer number = contract.getRoom().getNumber();
        return new RoomInStudentDto(number, contractMapper.toInStudentRoomDto(contract));
    }

    @Named("getStudentRoomNumber")
    public Integer getStudentRoomNumber(Student student) {
        final Optional<Contract> activeContract = contractRepository.findByStudentAndActive(student);
        return activeContract.map(Contract::getRoom).map(Room::getNumber).orElse(null);
    }

    @Named("getCurrentNumber")
    public Integer getCurrentNumber(Room room) {
        final List<Contract> activeContracts = contractRepository.findAllActiveByRoom(room);
        return activeContracts.size();
    }

    @Named("itemStringToItems")
    public RegulationItem itemStringToItems(String item) {
        final String[] items = item.split("\\.");
        RegulationItem prev = null;
        for (String s : items) {
            final RegulationItem current = new RegulationItem();
            current.setNumber(Short.valueOf(s));
            current.setParent(prev);
            prev = current;
        }
        return prev;
    }
}
