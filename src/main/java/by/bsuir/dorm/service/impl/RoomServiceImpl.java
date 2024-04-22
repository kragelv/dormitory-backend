package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.ContractRepository;
import by.bsuir.dorm.dao.RoomRepository;
import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.request.RoomRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.exception.RoomAlreadyExistsException;
import by.bsuir.dorm.exception.RoomNotFoundException;
import by.bsuir.dorm.exception.RoomStateException;
import by.bsuir.dorm.mapper.ContractMapper;
import by.bsuir.dorm.mapper.RoomMapper;
import by.bsuir.dorm.mapper.StudentMapper;
import by.bsuir.dorm.model.entity.Contract;
import by.bsuir.dorm.model.entity.Room;
import by.bsuir.dorm.model.entity.Student;
import by.bsuir.dorm.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final StudentMapper studentMapper;
    private final ContractRepository contractRepository;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final ContractMapper contractMapper;

    @Override
    public PageResponse<RoomDto> getAll(int page, int limit) {
        final Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "number"));
        final Page<Room> pageResult = roomRepository.findAll(pageable);
        log.info("Get rooms [page = " + page + ", limit = " + limit + "] : totalElements = "
                + pageResult.getTotalElements() + ", numberOfElements = " + pageResult.getNumberOfElements());
        return PageResponse.create(pageResult, roomMapper::toDto);
    }

    @Override
    public RoomDto getById(UUID id) {
        final Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room { id = " + id + " } doesn't exist"));
        log.info("Get room by id: " + id);
        return roomMapper.toDto(room);
    }

    @Override
    public RoomDto getByNumber(Integer number) {
        final Room room = roomRepository.getReferenceBySimpleNaturalId(number).
                orElseThrow(() -> new RoomNotFoundException("Room { number = " + number + " } doesn't exist"));
        log.info("Get room by number: " + number);
        return roomMapper.toDto(room);
    }

    @Override
    public UUID create(RoomRequestDto dto) {
        final Integer roomNumber = dto.number();
        if (roomRepository.findBySimpleNaturalId(roomNumber).isPresent()){
            throw new RoomAlreadyExistsException("Room { number = " + roomNumber + " } already exists");
        }
        final Room room = roomMapper.toEntity(dto);
        final Room saved = roomRepository.save(room);
        log.info("New room { id = " + saved.getId() + ", number = " + roomNumber + ", floor = "
                + dto.floor() + ", capacity = " + dto.capacity() + " }");
        return saved.getId();
    }

    @Override
    public RoomDto update(UUID id, RoomRequestDto dto) {
        final Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room { id = " + id + " } doesn't exist"));
        final Integer roomNumber = dto.number();
        if (roomRepository.findBySimpleNaturalId(roomNumber).isPresent()){
            throw new RoomAlreadyExistsException("Room { number = " + roomNumber + " } already exists");
        }
        final int current = contractRepository.findAllActiveByRoom(room).size();
        final Integer newCapacity = dto.capacity();
        if (current > newCapacity) {
            throw new RoomStateException("Room { id = " + id + " } current living people ("
                    + current + ") more than new capacity (" + newCapacity + ")");
        }
        final Room updated = roomMapper.partialUpdate(dto, room);
        roomRepository.save(updated);
        log.info("Update room { id = " + updated.getId() + ", number = " + updated.getNumber() + ", f }");
        return roomMapper.toDto(updated);
    }

    @Override
    public void deleteById(UUID id) {
        roomRepository.findById(id).ifPresent(room -> {
            if (!contractRepository.findAllActiveByRoom(room).isEmpty()) {
                throw new RoomStateException("Room { id = " + id + " } has living people");
            }
            log.info("Delete room { id = " + id + " }");
            roomRepository.delete(room);
        });
    }

    @Override
    public List<RoomDto.StudentDto> getStudentsByRoomId(UUID id) {
        final Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room { id = " + id + " } doesn't exist"));
        log.info("Get students by room: " + id);
        List<Contract> allActiveByRoom = contractRepository.findAllActiveByRoom(room);
        return allActiveByRoom
                .stream()
                .map(this::contractToRoomStudentDto)
                .toList();
    }

    private RoomDto.StudentDto contractToRoomStudentDto(Contract contract) {
        final Student student = contract.getStudent();
        if (student == null) {
            return contractMapper.toRoomStudentDto(contract);
        }
        return studentMapper.toRoomStudentDto(student, contract.getId());
    }
}
