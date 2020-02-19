package ru.ciadmin.service.impl;

import ru.ciadmin.service.CIBuildService;
import ru.ciadmin.domain.CIBuild;
import ru.ciadmin.repository.CIBuildRepository;
import ru.ciadmin.service.dto.CIBuildDTO;
import ru.ciadmin.service.mapper.CIBuildMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CIBuild}.
 */
@Service
@Transactional
public class CIBuildServiceImpl implements CIBuildService {

    private final Logger log = LoggerFactory.getLogger(CIBuildServiceImpl.class);

    private final CIBuildRepository cIBuildRepository;

    private final CIBuildMapper cIBuildMapper;

    public CIBuildServiceImpl(CIBuildRepository cIBuildRepository, CIBuildMapper cIBuildMapper) {
        this.cIBuildRepository = cIBuildRepository;
        this.cIBuildMapper = cIBuildMapper;
    }

    /**
     * Save a cIBuild.
     *
     * @param cIBuildDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CIBuildDTO save(CIBuildDTO cIBuildDTO) {
        log.debug("Request to save CIBuild : {}", cIBuildDTO);
        CIBuild cIBuild = cIBuildMapper.toEntity(cIBuildDTO);
        cIBuild = cIBuildRepository.save(cIBuild);
        return cIBuildMapper.toDto(cIBuild);
    }

    /**
     * Get all the cIBuilds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CIBuildDTO> findAll() {
        log.debug("Request to get all CIBuilds");
        return cIBuildRepository.findAll().stream()
            .map(cIBuildMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cIBuild by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CIBuildDTO> findOne(Long id) {
        log.debug("Request to get CIBuild : {}", id);
        return cIBuildRepository.findById(id)
            .map(cIBuildMapper::toDto);
    }

    /**
     * Delete the cIBuild by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CIBuild : {}", id);
        cIBuildRepository.deleteById(id);
    }
}
