package ru.ciadmin.service;

import ru.ciadmin.domain.CIRule;
import ru.ciadmin.repository.CIRuleRepository;
import ru.ciadmin.service.dto.CIRuleDTO;
import ru.ciadmin.service.mapper.CIRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CIRule}.
 */
@Service
@Transactional
public class CIRuleService {

    private final Logger log = LoggerFactory.getLogger(CIRuleService.class);

    private final CIRuleRepository cIRuleRepository;

    private final CIRuleMapper cIRuleMapper;

    public CIRuleService(CIRuleRepository cIRuleRepository, CIRuleMapper cIRuleMapper) {
        this.cIRuleRepository = cIRuleRepository;
        this.cIRuleMapper = cIRuleMapper;
    }

    /**
     * Save a cIRule.
     *
     * @param cIRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public CIRuleDTO save(CIRuleDTO cIRuleDTO) {
        log.debug("Request to save CIRule : {}", cIRuleDTO);
        CIRule cIRule = cIRuleMapper.toEntity(cIRuleDTO);
        cIRule = cIRuleRepository.save(cIRule);
        return cIRuleMapper.toDto(cIRule);
    }

    /**
     * Get all the cIRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CIRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CIRules");
        return cIRuleRepository.findAll(pageable)
            .map(cIRuleMapper::toDto);
    }

    /**
     * Get one cIRule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CIRuleDTO> findOne(Long id) {
        log.debug("Request to get CIRule : {}", id);
        return cIRuleRepository.findById(id)
            .map(cIRuleMapper::toDto);
    }

    /**
     * Delete the cIRule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CIRule : {}", id);
        cIRuleRepository.deleteById(id);
    }
}
