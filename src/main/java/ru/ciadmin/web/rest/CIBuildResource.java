package ru.ciadmin.web.rest;

import ru.ciadmin.service.CIBuildService;
import ru.ciadmin.web.rest.errors.BadRequestAlertException;
import ru.ciadmin.service.dto.CIBuildDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ru.ciadmin.domain.CIBuild}.
 */
@RestController
@RequestMapping("/api")
public class CIBuildResource {

    private final Logger log = LoggerFactory.getLogger(CIBuildResource.class);

    private static final String ENTITY_NAME = "cIBuild";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CIBuildService cIBuildService;

    public CIBuildResource(CIBuildService cIBuildService) {
        this.cIBuildService = cIBuildService;
    }

    /**
     * {@code POST  /ci-builds} : Create a new cIBuild.
     *
     * @param cIBuildDTO the cIBuildDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cIBuildDTO, or with status {@code 400 (Bad Request)} if the cIBuild has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ci-builds")
    public ResponseEntity<CIBuildDTO> createCIBuild(@RequestBody CIBuildDTO cIBuildDTO) throws URISyntaxException {
        log.debug("REST request to save CIBuild : {}", cIBuildDTO);
        if (cIBuildDTO.getId() != null) {
            throw new BadRequestAlertException("A new cIBuild cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CIBuildDTO result = cIBuildService.save(cIBuildDTO);
        return ResponseEntity.created(new URI("/api/ci-builds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ci-builds} : Updates an existing cIBuild.
     *
     * @param cIBuildDTO the cIBuildDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cIBuildDTO,
     * or with status {@code 400 (Bad Request)} if the cIBuildDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cIBuildDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ci-builds")
    public ResponseEntity<CIBuildDTO> updateCIBuild(@RequestBody CIBuildDTO cIBuildDTO) throws URISyntaxException {
        log.debug("REST request to update CIBuild : {}", cIBuildDTO);
        if (cIBuildDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CIBuildDTO result = cIBuildService.save(cIBuildDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cIBuildDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ci-builds} : get all the cIBuilds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cIBuilds in body.
     */
    @GetMapping("/ci-builds")
    public List<CIBuildDTO> getAllCIBuilds() {
        log.debug("REST request to get all CIBuilds");
        return cIBuildService.findAll();
    }

    /**
     * {@code GET  /ci-builds/:id} : get the "id" cIBuild.
     *
     * @param id the id of the cIBuildDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cIBuildDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ci-builds/{id}")
    public ResponseEntity<CIBuildDTO> getCIBuild(@PathVariable Long id) {
        log.debug("REST request to get CIBuild : {}", id);
        Optional<CIBuildDTO> cIBuildDTO = cIBuildService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cIBuildDTO);
    }

    /**
     * {@code DELETE  /ci-builds/:id} : delete the "id" cIBuild.
     *
     * @param id the id of the cIBuildDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ci-builds/{id}")
    public ResponseEntity<Void> deleteCIBuild(@PathVariable Long id) {
        log.debug("REST request to delete CIBuild : {}", id);
        cIBuildService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
