package by.bsuir.dorm.mapper;

import by.bsuir.dorm.dao.ContractRepository;
import by.bsuir.dorm.dao.GroupRepository;
import by.bsuir.dorm.dao.RoleRepository;
import by.bsuir.dorm.dto.RoomInStudentDto;
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
public class RoomExtensions {
    private final ContractRepository contractRepository;

    @Named("getCurrentNumber")
    public Integer getCurrentNumber(Room room) {
        final List<Contract> activeContracts = contractRepository.findAllActiveByRoom(room);
        return activeContracts.size();
    }
}
