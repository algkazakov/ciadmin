package ru.ciadmin.service;

import ru.ciadmin.service.dto.CIBuildDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ru.ciadmin.domain.CIBuild}.
 */
public interface CIBuildService {

    /**
     * Save a cIBuild.
     *
     * @param cIBuildDTO the entity to save.
     * @return the persisted entity.
     */
    CIBuildDTO save(CIBuildDTO cIBuildDTO);

    /**
     * Get all the cIBuilds.
     *
     * @return the list of entities.
     */
    List<CIBuildDTO> findAll();

    /**
     * Get the "id" cIBuild.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CIBuildDTO> findOne(Long id);

    /**
     * Delete the "id" cIBuild.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
