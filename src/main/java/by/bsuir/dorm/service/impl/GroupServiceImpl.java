package by.bsuir.dorm.service.impl;

import by.bsuir.dorm.dao.GroupRepository;
import by.bsuir.dorm.dto.GroupDto;
import by.bsuir.dorm.dto.request.GroupRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.exception.GroupAlreadyExistsException;
import by.bsuir.dorm.exception.GroupNotFoundException;
import by.bsuir.dorm.mapper.GroupMapper;
import by.bsuir.dorm.model.entity.Group;
import by.bsuir.dorm.service.GroupService;
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
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<GroupDto> getAll(int page, int limit) {
        final Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "number"));
        final Page<Group> pageResult = groupRepository.findAll(pageable);
        log.info("Get groups [page = " + page + ", limit = " + limit + "] : totalElements = "
                + pageResult.getTotalElements() + ", numberOfElements = " + pageResult.getNumberOfElements());
        return PageResponse.create(pageResult, groupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupDto getById(UUID id) {
        final Group group = groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group { id = " + id + " } doesn't exist"));
        return groupMapper.toDto(group);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupDto getByNumber(String number) {
        final Group group = groupRepository.findBySimpleNaturalId(number)
                .orElseThrow(() -> new GroupNotFoundException("Group { number = " + number + " } doesn't exist"));
        return groupMapper.toDto(group);
    }

    @Override
    public UUID create(GroupRequestDto dto) {
        final String number = dto.number();
        if (groupRepository.existsByNumber(number)) {
            throw new GroupAlreadyExistsException("Group { number = " + number + " } already exists");
        }
        final Group group = groupMapper.toEntity(dto);
        final Group saved = groupRepository.save(group);
        log.info("Create group { id = " + saved.getId() + ", number = " + saved.getNumber() + " }");
        return saved.getId();
    }

    @Override
    public void deleteById(UUID id) {
        groupRepository.findById(id).ifPresent(group -> {
            log.info("Delete group by id { id = " + group.getId() + ", number = " + group.getNumber() + "}");
            groupRepository.delete(group);
        });

    }

    @Override
    public void deleteByNumber(String number) {
        groupRepository.findBySimpleNaturalId(number).ifPresent(group -> {
            log.info("Delete group by number { id = " + group.getId() + ", number = " + group.getNumber() + "}");
            groupRepository.delete(group);
        });
    }
}
