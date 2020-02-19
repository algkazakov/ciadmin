package ru.ciadmin.web.rest;

import ru.ciadmin.CiadminApp;
import ru.ciadmin.domain.CIRule;
import ru.ciadmin.repository.CIRuleRepository;
import ru.ciadmin.service.CIRuleService;
import ru.ciadmin.service.dto.CIRuleDTO;
import ru.ciadmin.service.mapper.CIRuleMapper;
import ru.ciadmin.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.ciadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CIRuleResource} REST controller.
 */
@SpringBootTest(classes = CiadminApp.class)
public class CIRuleResourceIT {

    private static final String DEFAULT_RULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RULE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CRITICAL = false;
    private static final Boolean UPDATED_CRITICAL = true;

    private static final Boolean DEFAULT_USE_IN_REPORT = false;
    private static final Boolean UPDATED_USE_IN_REPORT = true;

    private static final Boolean DEFAULT_USE_IN_MICROSERVICE = false;
    private static final Boolean UPDATED_USE_IN_MICROSERVICE = true;

    private static final Instant DEFAULT_MODIFY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CIRuleRepository cIRuleRepository;

    @Autowired
    private CIRuleMapper cIRuleMapper;

    @Autowired
    private CIRuleService cIRuleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCIRuleMockMvc;

    private CIRule cIRule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CIRuleResource cIRuleResource = new CIRuleResource(cIRuleService);
        this.restCIRuleMockMvc = MockMvcBuilders.standaloneSetup(cIRuleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CIRule createEntity(EntityManager em) {
        CIRule cIRule = new CIRule()
            .ruleName(DEFAULT_RULE_NAME)
            .critical(DEFAULT_CRITICAL)
            .useInReport(DEFAULT_USE_IN_REPORT)
            .useInMicroservice(DEFAULT_USE_IN_MICROSERVICE)
            .modifyDate(DEFAULT_MODIFY_DATE);
        return cIRule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CIRule createUpdatedEntity(EntityManager em) {
        CIRule cIRule = new CIRule()
            .ruleName(UPDATED_RULE_NAME)
            .critical(UPDATED_CRITICAL)
            .useInReport(UPDATED_USE_IN_REPORT)
            .useInMicroservice(UPDATED_USE_IN_MICROSERVICE)
            .modifyDate(UPDATED_MODIFY_DATE);
        return cIRule;
    }

    @BeforeEach
    public void initTest() {
        cIRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createCIRule() throws Exception {
        int databaseSizeBeforeCreate = cIRuleRepository.findAll().size();

        // Create the CIRule
        CIRuleDTO cIRuleDTO = cIRuleMapper.toDto(cIRule);
        restCIRuleMockMvc.perform(post("/api/ci-rules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the CIRule in the database
        List<CIRule> cIRuleList = cIRuleRepository.findAll();
        assertThat(cIRuleList).hasSize(databaseSizeBeforeCreate + 1);
        CIRule testCIRule = cIRuleList.get(cIRuleList.size() - 1);
        assertThat(testCIRule.getRuleName()).isEqualTo(DEFAULT_RULE_NAME);
        assertThat(testCIRule.isCritical()).isEqualTo(DEFAULT_CRITICAL);
        assertThat(testCIRule.isUseInReport()).isEqualTo(DEFAULT_USE_IN_REPORT);
        assertThat(testCIRule.isUseInMicroservice()).isEqualTo(DEFAULT_USE_IN_MICROSERVICE);
        assertThat(testCIRule.getModifyDate()).isEqualTo(DEFAULT_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void createCIRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cIRuleRepository.findAll().size();

        // Create the CIRule with an existing ID
        cIRule.setId(1L);
        CIRuleDTO cIRuleDTO = cIRuleMapper.toDto(cIRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCIRuleMockMvc.perform(post("/api/ci-rules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CIRule in the database
        List<CIRule> cIRuleList = cIRuleRepository.findAll();
        assertThat(cIRuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCIRules() throws Exception {
        // Initialize the database
        cIRuleRepository.saveAndFlush(cIRule);

        // Get all the cIRuleList
        restCIRuleMockMvc.perform(get("/api/ci-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cIRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleName").value(hasItem(DEFAULT_RULE_NAME)))
            .andExpect(jsonPath("$.[*].critical").value(hasItem(DEFAULT_CRITICAL.booleanValue())))
            .andExpect(jsonPath("$.[*].useInReport").value(hasItem(DEFAULT_USE_IN_REPORT.booleanValue())))
            .andExpect(jsonPath("$.[*].useInMicroservice").value(hasItem(DEFAULT_USE_IN_MICROSERVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].modifyDate").value(hasItem(DEFAULT_MODIFY_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCIRule() throws Exception {
        // Initialize the database
        cIRuleRepository.saveAndFlush(cIRule);

        // Get the cIRule
        restCIRuleMockMvc.perform(get("/api/ci-rules/{id}", cIRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cIRule.getId().intValue()))
            .andExpect(jsonPath("$.ruleName").value(DEFAULT_RULE_NAME))
            .andExpect(jsonPath("$.critical").value(DEFAULT_CRITICAL.booleanValue()))
            .andExpect(jsonPath("$.useInReport").value(DEFAULT_USE_IN_REPORT.booleanValue()))
            .andExpect(jsonPath("$.useInMicroservice").value(DEFAULT_USE_IN_MICROSERVICE.booleanValue()))
            .andExpect(jsonPath("$.modifyDate").value(DEFAULT_MODIFY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCIRule() throws Exception {
        // Get the cIRule
        restCIRuleMockMvc.perform(get("/api/ci-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCIRule() throws Exception {
        // Initialize the database
        cIRuleRepository.saveAndFlush(cIRule);

        int databaseSizeBeforeUpdate = cIRuleRepository.findAll().size();

        // Update the cIRule
        CIRule updatedCIRule = cIRuleRepository.findById(cIRule.getId()).get();
        // Disconnect from session so that the updates on updatedCIRule are not directly saved in db
        em.detach(updatedCIRule);
        updatedCIRule
            .ruleName(UPDATED_RULE_NAME)
            .critical(UPDATED_CRITICAL)
            .useInReport(UPDATED_USE_IN_REPORT)
            .useInMicroservice(UPDATED_USE_IN_MICROSERVICE)
            .modifyDate(UPDATED_MODIFY_DATE);
        CIRuleDTO cIRuleDTO = cIRuleMapper.toDto(updatedCIRule);

        restCIRuleMockMvc.perform(put("/api/ci-rules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIRuleDTO)))
            .andExpect(status().isOk());

        // Validate the CIRule in the database
        List<CIRule> cIRuleList = cIRuleRepository.findAll();
        assertThat(cIRuleList).hasSize(databaseSizeBeforeUpdate);
        CIRule testCIRule = cIRuleList.get(cIRuleList.size() - 1);
        assertThat(testCIRule.getRuleName()).isEqualTo(UPDATED_RULE_NAME);
        assertThat(testCIRule.isCritical()).isEqualTo(UPDATED_CRITICAL);
        assertThat(testCIRule.isUseInReport()).isEqualTo(UPDATED_USE_IN_REPORT);
        assertThat(testCIRule.isUseInMicroservice()).isEqualTo(UPDATED_USE_IN_MICROSERVICE);
        assertThat(testCIRule.getModifyDate()).isEqualTo(UPDATED_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCIRule() throws Exception {
        int databaseSizeBeforeUpdate = cIRuleRepository.findAll().size();

        // Create the CIRule
        CIRuleDTO cIRuleDTO = cIRuleMapper.toDto(cIRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCIRuleMockMvc.perform(put("/api/ci-rules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CIRule in the database
        List<CIRule> cIRuleList = cIRuleRepository.findAll();
        assertThat(cIRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCIRule() throws Exception {
        // Initialize the database
        cIRuleRepository.saveAndFlush(cIRule);

        int databaseSizeBeforeDelete = cIRuleRepository.findAll().size();

        // Delete the cIRule
        restCIRuleMockMvc.perform(delete("/api/ci-rules/{id}", cIRule.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CIRule> cIRuleList = cIRuleRepository.findAll();
        assertThat(cIRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
