package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.ContractRepository;
import by.bsuir.dorm.dao.RoomRepository;
import by.bsuir.dorm.dao.StudentRepository;
import by.bsuir.dorm.dto.ContractDto;
import by.bsuir.dorm.dto.request.ContractCreateRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.exception.ContractNotFoundException;
import by.bsuir.dorm.exception.RoomNotFoundException;
import by.bsuir.dorm.exception.UserNotFoundException;
import by.bsuir.dorm.mapper.ContractMapper;
import by.bsuir.dorm.model.entity.Contract;
import by.bsuir.dorm.model.entity.Room;
import by.bsuir.dorm.model.entity.Student;
import by.bsuir.dorm.model.entity.User;
import by.bsuir.dorm.service.ContractService;
import by.bsuir.dorm.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ContractServiceImpl implements ContractService {
    private final UserSecurityService userSecurityService;
    private final ContractRepository contractRepository;
    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;
    private final ContractMapper contractMapper;

    @Override
    public UUID create(String creator, ContractCreateRequestDto dto) {
        final String cardId = dto.cardId();
        final Student student;
        if (cardId == null) {
            student = null;
        } else {
            student = studentRepository.findByCardId(cardId)
                    .orElseThrow((() -> new UserNotFoundException("Student { cardId = " + cardId + " } doesn't exist")));
        }
        final Integer roomNumber = dto.roomNumber();
        final Room room = roomRepository.getReferenceBySimpleNaturalId(roomNumber).
                orElseThrow(() -> new RoomNotFoundException("Room { number = " + roomNumber + " } doesn't exist"));
        final User createdBy = userSecurityService.findByUsername(creator)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid creator id = " + creator));
        final Contract contract = contractMapper.toEntity(dto);
        contract.setNumber(0);
        contract.setTerminationDate(null);
        contract.setRoom(room);
        contract.setStudent(student);
        contract.setCreatedBy(createdBy);
        contract.setCreatedByFullName(createdBy.getFullName().toString());
        final Contract saved = contractRepository.save(contract);
        log.info("New contract { id = " + saved.getId() + ", creator = '" + saved.getCreatedByFullName()
                + "', cardId = " + saved.getCardId() + ", startDate = " + saved.getStartDate()
                + ", expiryDate = " + saved.getExpiryDate() + " }");
        return saved.getId();
    }

    @Override
    public PageResponse<ContractDto> getAll(int page, int limit) {
        final Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "date"));
        final Page<Contract> pageResult = contractRepository.findAll(pageable);
        log.info("Get contracts [page = " + page + ", limit = " + limit + "] : totalElements = "
                + pageResult.getTotalElements() + ", numberOfElements = " + pageResult.getNumberOfElements());
        return PageResponse.create(pageResult, contractMapper::toDto);
    }

    @Override
    public ContractDto getById(UUID id) {
        final Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ContractNotFoundException("Contract { id = " + id + " } doesn't exist"));
        return contractMapper.toDto(contract);
    }

    @Override
    public void terminate(UUID id) {
        final Contract contract = contractRepository.findActiveContractById(id)
                .orElseThrow(() -> new ContractNotFoundException("Active contract { id = " + id + "} doesn't exist"));
        contract.setTerminationDate(LocalDate.now());
        contractRepository.save(contract);
    }
}
