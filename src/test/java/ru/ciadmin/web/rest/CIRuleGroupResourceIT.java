package ru.ciadmin.web.rest;

import ru.ciadmin.CiadminApp;
import ru.ciadmin.domain.CIRuleGroup;
import ru.ciadmin.repository.CIRuleGroupRepository;
import ru.ciadmin.service.CIRuleGroupService;
import ru.ciadmin.service.dto.CIRuleGroupDTO;
import ru.ciadmin.service.mapper.CIRuleGroupMapper;
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
import java.util.List;

import static ru.ciadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CIRuleGroupResource} REST controller.
 */
@SpringBootTest(classes = CiadminApp.class)
public class CIRuleGroupResourceIT {

    private static final String DEFAULT_RULE_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RULE_GROUP_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CRITICAL = false;
    private static final Boolean UPDATED_CRITICAL = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private CIRuleGroupRepository cIRuleGroupRepository;

    @Autowired
    private CIRuleGroupMapper cIRuleGroupMapper;

    @Autowired
    private CIRuleGroupService cIRuleGroupService;

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

    private MockMvc restCIRuleGroupMockMvc;

    private CIRuleGroup cIRuleGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CIRuleGroupResource cIRuleGroupResource = new CIRuleGroupResource(cIRuleGroupService);
        this.restCIRuleGroupMockMvc = MockMvcBuilders.standaloneSetup(cIRuleGroupResource)
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
    public static CIRuleGroup createEntity(EntityManager em) {
        CIRuleGroup cIRuleGroup = new CIRuleGroup()
            .ruleGroupName(DEFAULT_RULE_GROUP_NAME)
            .critical(DEFAULT_CRITICAL)
            .type(DEFAULT_TYPE);
        return cIRuleGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CIRuleGroup createUpdatedEntity(EntityManager em) {
        CIRuleGroup cIRuleGroup = new CIRuleGroup()
            .ruleGroupName(UPDATED_RULE_GROUP_NAME)
            .critical(UPDATED_CRITICAL)
            .type(UPDATED_TYPE);
        return cIRuleGroup;
    }

    @BeforeEach
    public void initTest() {
        cIRuleGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createCIRuleGroup() throws Exception {
        int databaseSizeBeforeCreate = cIRuleGroupRepository.findAll().size();

        // Create the CIRuleGroup
        CIRuleGroupDTO cIRuleGroupDTO = cIRuleGroupMapper.toDto(cIRuleGroup);
        restCIRuleGroupMockMvc.perform(post("/api/ci-rule-groups")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIRuleGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CIRuleGroup in the database
        List<CIRuleGroup> cIRuleGroupList = cIRuleGroupRepository.findAll();
        assertThat(cIRuleGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CIRuleGroup testCIRuleGroup = cIRuleGroupList.get(cIRuleGroupList.size() - 1);
        assertThat(testCIRuleGroup.getRuleGroupName()).isEqualTo(DEFAULT_RULE_GROUP_NAME);
        assertThat(testCIRuleGroup.isCritical()).isEqualTo(DEFAULT_CRITICAL);
        assertThat(testCIRuleGroup.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createCIRuleGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cIRuleGroupRepository.findAll().size();

        // Create the CIRuleGroup with an existing ID
        cIRuleGroup.setId(1L);
        CIRuleGroupDTO cIRuleGroupDTO = cIRuleGroupMapper.toDto(cIRuleGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCIRuleGroupMockMvc.perform(post("/api/ci-rule-groups")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIRuleGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CIRuleGroup in the database
        List<CIRuleGroup> cIRuleGroupList = cIRuleGroupRepository.findAll();
        assertThat(cIRuleGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCIRuleGroups() throws Exception {
        // Initialize the database
        cIRuleGroupRepository.saveAndFlush(cIRuleGroup);

        // Get all the cIRuleGroupList
        restCIRuleGroupMockMvc.perform(get("/api/ci-rule-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cIRuleGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleGroupName").value(hasItem(DEFAULT_RULE_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].critical").value(hasItem(DEFAULT_CRITICAL.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getCIRuleGroup() throws Exception {
        // Initialize the database
        cIRuleGroupRepository.saveAndFlush(cIRuleGroup);

        // Get the cIRuleGroup
        restCIRuleGroupMockMvc.perform(get("/api/ci-rule-groups/{id}", cIRuleGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cIRuleGroup.getId().intValue()))
            .andExpect(jsonPath("$.ruleGroupName").value(DEFAULT_RULE_GROUP_NAME))
            .andExpect(jsonPath("$.critical").value(DEFAULT_CRITICAL.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingCIRuleGroup() throws Exception {
        // Get the cIRuleGroup
        restCIRuleGroupMockMvc.perform(get("/api/ci-rule-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCIRuleGroup() throws Exception {
        // Initialize the database
        cIRuleGroupRepository.saveAndFlush(cIRuleGroup);

        int databaseSizeBeforeUpdate = cIRuleGroupRepository.findAll().size();

        // Update the cIRuleGroup
        CIRuleGroup updatedCIRuleGroup = cIRuleGroupRepository.findById(cIRuleGroup.getId()).get();
        // Disconnect from session so that the updates on updatedCIRuleGroup are not directly saved in db
        em.detach(updatedCIRuleGroup);
        updatedCIRuleGroup
            .ruleGroupName(UPDATED_RULE_GROUP_NAME)
            .critical(UPDATED_CRITICAL)
            .type(UPDATED_TYPE);
        CIRuleGroupDTO cIRuleGroupDTO = cIRuleGroupMapper.toDto(updatedCIRuleGroup);

        restCIRuleGroupMockMvc.perform(put("/api/ci-rule-groups")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIRuleGroupDTO)))
            .andExpect(status().isOk());

        // Validate the CIRuleGroup in the database
        List<CIRuleGroup> cIRuleGroupList = cIRuleGroupRepository.findAll();
        assertThat(cIRuleGroupList).hasSize(databaseSizeBeforeUpdate);
        CIRuleGroup testCIRuleGroup = cIRuleGroupList.get(cIRuleGroupList.size() - 1);
        assertThat(testCIRuleGroup.getRuleGroupName()).isEqualTo(UPDATED_RULE_GROUP_NAME);
        assertThat(testCIRuleGroup.isCritical()).isEqualTo(UPDATED_CRITICAL);
        assertThat(testCIRuleGroup.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCIRuleGroup() throws Exception {
        int databaseSizeBeforeUpdate = cIRuleGroupRepository.findAll().size();

        // Create the CIRuleGroup
        CIRuleGroupDTO cIRuleGroupDTO = cIRuleGroupMapper.toDto(cIRuleGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCIRuleGroupMockMvc.perform(put("/api/ci-rule-groups")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIRuleGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CIRuleGroup in the database
        List<CIRuleGroup> cIRuleGroupList = cIRuleGroupRepository.findAll();
        assertThat(cIRuleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCIRuleGroup() throws Exception {
        // Initialize the database
        cIRuleGroupRepository.saveAndFlush(cIRuleGroup);

        int databaseSizeBeforeDelete = cIRuleGroupRepository.findAll().size();

        // Delete the cIRuleGroup
        restCIRuleGroupMockMvc.perform(delete("/api/ci-rule-groups/{id}", cIRuleGroup.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CIRuleGroup> cIRuleGroupList = cIRuleGroupRepository.findAll();
        assertThat(cIRuleGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
