package ru.ciadmin.web.rest;

import org.springframework.security.access.annotation.Secured;
import ru.ciadmin.service.CIRuleService;
import ru.ciadmin.web.rest.errors.BadRequestAlertException;
import ru.ciadmin.service.dto.CIRuleDTO;

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
 * REST controller for managing {@link ru.ciadmin.domain.CIRule}.
 */
@RestController
@RequestMapping("/api")
public class CIRuleResource {

    private final Logger log = LoggerFactory.getLogger(CIRuleResource.class);

    private static final String ENTITY_NAME = "cIRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CIRuleService cIRuleService;

    public CIRuleResource(CIRuleService cIRuleService) {
        this.cIRuleService = cIRuleService;
    }

    /**
     * {@code POST  /ci-rules} : Create a new cIRule.
     *
     * @param cIRuleDTO the cIRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cIRuleDTO, or with status {@code 400 (Bad Request)} if the cIRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ci-rules")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CIRuleDTO> createCIRule(@RequestBody CIRuleDTO cIRuleDTO) throws URISyntaxException {
        log.debug("REST request to save CIRule : {}", cIRuleDTO);
        if (cIRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new cIRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CIRuleDTO result = cIRuleService.save(cIRuleDTO);
        return ResponseEntity.created(new URI("/api/ci-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ci-rules} : Updates an existing cIRule.
     *
     * @param cIRuleDTO the cIRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cIRuleDTO,
     * or with status {@code 400 (Bad Request)} if the cIRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cIRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ci-rules")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CIRuleDTO> updateCIRule(@RequestBody CIRuleDTO cIRuleDTO) throws URISyntaxException {
        log.debug("REST request to update CIRule : {}", cIRuleDTO);
        if (cIRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CIRuleDTO result = cIRuleService.save(cIRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cIRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ci-rules} : get all the cIRules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cIRules in body.
     */
    @GetMapping("/ci-rules")
    public ResponseEntity<List<CIRuleDTO>> getAllCIRules(Pageable pageable) {
        log.debug("REST request to get a page of CIRules");
        Page<CIRuleDTO> page = cIRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ci-rules/:id} : get the "id" cIRule.
     *
     * @param id the id of the cIRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cIRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ci-rules/{id}")
    public ResponseEntity<CIRuleDTO> getCIRule(@PathVariable Long id) {
        log.debug("REST request to get CIRule : {}", id);
        Optional<CIRuleDTO> cIRuleDTO = cIRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cIRuleDTO);
    }

    /**
     * {@code DELETE  /ci-rules/:id} : delete the "id" cIRule.
     *
     * @param id the id of the cIRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ci-rules/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteCIRule(@PathVariable Long id) {
        log.debug("REST request to delete CIRule : {}", id);
        cIRuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
