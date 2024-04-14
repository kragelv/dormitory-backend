package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.RoomRepository;
import by.bsuir.dorm.dto.RoomDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.exception.RoomNotFoundException;
import by.bsuir.dorm.mapper.RoomMapper;
import by.bsuir.dorm.model.entity.Room;
import by.bsuir.dorm.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

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
                .orElseThrow(() -> new RoomNotFoundException("Room { id = " + id +" } doesn't exist"));
        return roomMapper.toDto(room);
    }

    @Override
    public RoomDto getByNumber(Integer number) {
        final Room room = roomRepository.getReferenceBySimpleNaturalId(number).
                orElseThrow(() -> new RoomNotFoundException("Room { number = " + number +" } doesn't exist"));
        return roomMapper.toDto(room);
    }
}
