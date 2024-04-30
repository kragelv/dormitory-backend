package by.bsuir.dorm.service.impl;


import by.bsuir.dorm.dao.InternalRegulationRepository;
import by.bsuir.dorm.dto.InternalRegulationDto;
import by.bsuir.dorm.dto.request.InternalRegulationRequestDto;
import by.bsuir.dorm.dto.response.PageResponse;
import by.bsuir.dorm.exception.RegulationAlreadyExistsException;
import by.bsuir.dorm.exception.RegulationNotFoundException;
import by.bsuir.dorm.exception.RegulationStateException;
import by.bsuir.dorm.mapper.RegulationMapper;
import by.bsuir.dorm.model.entity.InternalRegulation;
import by.bsuir.dorm.service.RegulationService;
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
public class RegulationServiceImpl implements RegulationService {
    private final RegulationMapper regulationMapper;
    private final InternalRegulationRepository internalRegulationRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<InternalRegulationDto> getAll(int page, int limit, String search) {
        final Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "itemString"));
        final Page<InternalRegulation> pageResult;
        if (search == null || search.isEmpty()) {
            pageResult = internalRegulationRepository.findAll(pageable);
        } else {
            pageResult = internalRegulationRepository.findAllBySearchString(search, pageable);
        }
        log.info("Get regulations [page = " + page + ", limit = " + limit + ", search = " + search + "] : totalElements = "
                + pageResult.getTotalElements() + ", numberOfElements = " + pageResult.getNumberOfElements());
        return PageResponse.create(pageResult, regulationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public InternalRegulationDto getById(UUID id) {
        final InternalRegulation internalRegulation = internalRegulationRepository.findById(id)
                .orElseThrow(() -> new RegulationNotFoundException("Regulation { id = " + id + " } doesn't exist"));
        log.info("Get regulation by id: " + id);
        return regulationMapper.toDto(internalRegulation);
    }

    @Override
    public UUID create(InternalRegulationRequestDto dto) {
        final String newItem = dto.item();
        internalRegulationRepository.findBySimpleNaturalId(newItem).ifPresent(regulation -> {
            throw new RegulationAlreadyExistsException("Regulation { id = " + regulation.getId()
                    + ", item = " + newItem + " } already exists");
        });
        final InternalRegulation regulation = regulationMapper.toEntity(dto);
        final InternalRegulation saved = internalRegulationRepository.save(regulation);
        log.info("New regulation { id = " + saved.getId() + ", item = " + saved.getItemString() + "} created  ");
        return saved.getId();
    }

    @Override
    public InternalRegulationDto update(UUID id, InternalRegulationRequestDto dto) {
        final InternalRegulation regulation = internalRegulationRepository.findById(id)
                .orElseThrow(() -> new RegulationNotFoundException("Regulation { id = " + id + " } doesn't exist"));
        final String newItem = dto.item();
        internalRegulationRepository.findBySimpleNaturalId(newItem).ifPresent(existing -> {
            throw new RegulationAlreadyExistsException("Regulation { id = " + existing.getId()
                    + ", item = " + newItem + " } already exists");
        });
        final InternalRegulation updated = regulationMapper.partialUpdate(dto, regulation);
        internalRegulationRepository.save(updated);
        log.info("Regulation { id = " + updated.getId() + ", item = " + updated.getItemString() + "} updated ");
        return regulationMapper.toDto(updated);
    }

    @Override
    public void deleteById(UUID id) {
        internalRegulationRepository.findById(id).ifPresent(regulation -> {
            if (!regulation.getStudentViolations().isEmpty()) {
                throw new RegulationStateException("Student violation refers to regulation { id = " + id + " } ");
            }
            internalRegulationRepository.delete(regulation);
            log.info("Regulation { id = " + id + ", item = " + regulation.getItemString() + "} deleted");
        });
    }
}
