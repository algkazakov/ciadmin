package ru.ciadmin.service;

import ru.ciadmin.domain.CIRuleGroup;
import ru.ciadmin.repository.CIRuleGroupRepository;
import ru.ciadmin.service.dto.CIRuleGroupDTO;
import ru.ciadmin.service.mapper.CIRuleGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CIRuleGroup}.
 */
@Service
@Transactional
public class CIRuleGroupService {

    private final Logger log = LoggerFactory.getLogger(CIRuleGroupService.class);

    private final CIRuleGroupRepository cIRuleGroupRepository;

    private final CIRuleGroupMapper cIRuleGroupMapper;

    public CIRuleGroupService(CIRuleGroupRepository cIRuleGroupRepository, CIRuleGroupMapper cIRuleGroupMapper) {
        this.cIRuleGroupRepository = cIRuleGroupRepository;
        this.cIRuleGroupMapper = cIRuleGroupMapper;
    }

    /**
     * Save a cIRuleGroup.
     *
     * @param cIRuleGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public CIRuleGroupDTO save(CIRuleGroupDTO cIRuleGroupDTO) {
        log.debug("Request to save CIRuleGroup : {}", cIRuleGroupDTO);
        CIRuleGroup cIRuleGroup = cIRuleGroupMapper.toEntity(cIRuleGroupDTO);
        cIRuleGroup = cIRuleGroupRepository.save(cIRuleGroup);
        return cIRuleGroupMapper.toDto(cIRuleGroup);
    }

    /**
     * Get all the cIRuleGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CIRuleGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CIRuleGroups");
        return cIRuleGroupRepository.findAll(pageable)
            .map(cIRuleGroupMapper::toDto);
    }

    /**
     * Get one cIRuleGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CIRuleGroupDTO> findOne(Long id) {
        log.debug("Request to get CIRuleGroup : {}", id);
        return cIRuleGroupRepository.findById(id)
            .map(cIRuleGroupMapper::toDto);
    }

    /**
     * Delete the cIRuleGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CIRuleGroup : {}", id);
        cIRuleGroupRepository.deleteById(id);
    }
}
