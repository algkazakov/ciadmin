package ru.ciadmin.web.rest;

import ru.ciadmin.service.CIRuleGroupService;
import ru.ciadmin.web.rest.errors.BadRequestAlertException;
import ru.ciadmin.service.dto.CIRuleGroupDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ru.ciadmin.domain.CIRuleGroup}.
 */
@RestController
@RequestMapping("/api")
public class CIRuleGroupResource {

    private final Logger log = LoggerFactory.getLogger(CIRuleGroupResource.class);

    private static final String ENTITY_NAME = "cIRuleGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CIRuleGroupService cIRuleGroupService;

    public CIRuleGroupResource(CIRuleGroupService cIRuleGroupService) {
        this.cIRuleGroupService = cIRuleGroupService;
    }

    /**
     * {@code POST  /ci-rule-groups} : Create a new cIRuleGroup.
     *
     * @param cIRuleGroupDTO the cIRuleGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cIRuleGroupDTO, or with status {@code 400 (Bad Request)} if the cIRuleGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ci-rule-groups")
    public ResponseEntity<CIRuleGroupDTO> createCIRuleGroup(@RequestBody CIRuleGroupDTO cIRuleGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CIRuleGroup : {}", cIRuleGroupDTO);
        if (cIRuleGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new cIRuleGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CIRuleGroupDTO result = cIRuleGroupService.save(cIRuleGroupDTO);
        return ResponseEntity.created(new URI("/api/ci-rule-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ci-rule-groups} : Updates an existing cIRuleGroup.
     *
     * @param cIRuleGroupDTO the cIRuleGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cIRuleGroupDTO,
     * or with status {@code 400 (Bad Request)} if the cIRuleGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cIRuleGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ci-rule-groups")
    public ResponseEntity<CIRuleGroupDTO> updateCIRuleGroup(@RequestBody CIRuleGroupDTO cIRuleGroupDTO) throws URISyntaxException {
        log.debug("REST request to update CIRuleGroup : {}", cIRuleGroupDTO);
        if (cIRuleGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CIRuleGroupDTO result = cIRuleGroupService.save(cIRuleGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cIRuleGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ci-rule-groups} : get all the cIRuleGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cIRuleGroups in body.
     */
    @GetMapping("/ci-rule-groups")
    public ResponseEntity<List<CIRuleGroupDTO>> getAllCIRuleGroups(Pageable pageable) {
        log.debug("REST request to get a page of CIRuleGroups");
        Page<CIRuleGroupDTO> page = cIRuleGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ci-rule-groups/:id} : get the "id" cIRuleGroup.
     *
     * @param id the id of the cIRuleGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cIRuleGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ci-rule-groups/{id}")
    public ResponseEntity<CIRuleGroupDTO> getCIRuleGroup(@PathVariable Long id) {
        log.debug("REST request to get CIRuleGroup : {}", id);
        Optional<CIRuleGroupDTO> cIRuleGroupDTO = cIRuleGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cIRuleGroupDTO);
    }

    /**
     * {@code DELETE  /ci-rule-groups/:id} : delete the "id" cIRuleGroup.
     *
     * @param id the id of the cIRuleGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ci-rule-groups/{id}")
    public ResponseEntity<Void> deleteCIRuleGroup(@PathVariable Long id) {
        log.debug("REST request to delete CIRuleGroup : {}", id);
        cIRuleGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
