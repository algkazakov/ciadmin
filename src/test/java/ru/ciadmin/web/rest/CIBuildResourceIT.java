package ru.ciadmin.web.rest;

import ru.ciadmin.CiadminApp;
import ru.ciadmin.domain.CIBuild;
import ru.ciadmin.repository.CIBuildRepository;
import ru.ciadmin.service.CIBuildService;
import ru.ciadmin.service.dto.CIBuildDTO;
import ru.ciadmin.service.mapper.CIBuildMapper;
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
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ru.ciadmin.web.rest.TestUtil.sameInstant;
import static ru.ciadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CIBuildResource} REST controller.
 */
@SpringBootTest(classes = CiadminApp.class)
public class CIBuildResourceIT {

    private static final String DEFAULT_MODULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODULE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODULE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_MODULE_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_CI_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_CI_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_SONAR_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_SONAR_RESULT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BUILD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BUILD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SONAR_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_SONAR_PROJECT = "BBBBBBBBBB";

    @Autowired
    private CIBuildRepository cIBuildRepository;

    @Autowired
    private CIBuildMapper cIBuildMapper;

    @Autowired
    private CIBuildService cIBuildService;

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

    private MockMvc restCIBuildMockMvc;

    private CIBuild cIBuild;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CIBuildResource cIBuildResource = new CIBuildResource(cIBuildService);
        this.restCIBuildMockMvc = MockMvcBuilders.standaloneSetup(cIBuildResource)
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
    public static CIBuild createEntity(EntityManager em) {
        CIBuild cIBuild = new CIBuild()
            .moduleName(DEFAULT_MODULE_NAME)
            .moduleVersion(DEFAULT_MODULE_VERSION)
            .branch(DEFAULT_BRANCH)
            .ciResult(DEFAULT_CI_RESULT)
            .sonarResult(DEFAULT_SONAR_RESULT)
            .buildDate(DEFAULT_BUILD_DATE)
            .sonarProject(DEFAULT_SONAR_PROJECT);
        return cIBuild;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CIBuild createUpdatedEntity(EntityManager em) {
        CIBuild cIBuild = new CIBuild()
            .moduleName(UPDATED_MODULE_NAME)
            .moduleVersion(UPDATED_MODULE_VERSION)
            .branch(UPDATED_BRANCH)
            .ciResult(UPDATED_CI_RESULT)
            .sonarResult(UPDATED_SONAR_RESULT)
            .buildDate(UPDATED_BUILD_DATE)
            .sonarProject(UPDATED_SONAR_PROJECT);
        return cIBuild;
    }

    @BeforeEach
    public void initTest() {
        cIBuild = createEntity(em);
    }

    @Test
    @Transactional
    public void createCIBuild() throws Exception {
        int databaseSizeBeforeCreate = cIBuildRepository.findAll().size();

        // Create the CIBuild
        CIBuildDTO cIBuildDTO = cIBuildMapper.toDto(cIBuild);
        restCIBuildMockMvc.perform(post("/api/ci-builds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIBuildDTO)))
            .andExpect(status().isCreated());

        // Validate the CIBuild in the database
        List<CIBuild> cIBuildList = cIBuildRepository.findAll();
        assertThat(cIBuildList).hasSize(databaseSizeBeforeCreate + 1);
        CIBuild testCIBuild = cIBuildList.get(cIBuildList.size() - 1);
        assertThat(testCIBuild.getModuleName()).isEqualTo(DEFAULT_MODULE_NAME);
        assertThat(testCIBuild.getModuleVersion()).isEqualTo(DEFAULT_MODULE_VERSION);
        assertThat(testCIBuild.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testCIBuild.getCiResult()).isEqualTo(DEFAULT_CI_RESULT);
        assertThat(testCIBuild.getSonarResult()).isEqualTo(DEFAULT_SONAR_RESULT);
        assertThat(testCIBuild.getBuildDate()).isEqualTo(DEFAULT_BUILD_DATE);
        assertThat(testCIBuild.getSonarProject()).isEqualTo(DEFAULT_SONAR_PROJECT);
    }

    @Test
    @Transactional
    public void createCIBuildWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cIBuildRepository.findAll().size();

        // Create the CIBuild with an existing ID
        cIBuild.setId(1L);
        CIBuildDTO cIBuildDTO = cIBuildMapper.toDto(cIBuild);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCIBuildMockMvc.perform(post("/api/ci-builds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIBuildDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CIBuild in the database
        List<CIBuild> cIBuildList = cIBuildRepository.findAll();
        assertThat(cIBuildList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCIBuilds() throws Exception {
        // Initialize the database
        cIBuildRepository.saveAndFlush(cIBuild);

        // Get all the cIBuildList
        restCIBuildMockMvc.perform(get("/api/ci-builds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cIBuild.getId().intValue())))
            .andExpect(jsonPath("$.[*].moduleName").value(hasItem(DEFAULT_MODULE_NAME)))
            .andExpect(jsonPath("$.[*].moduleVersion").value(hasItem(DEFAULT_MODULE_VERSION)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].ciResult").value(hasItem(DEFAULT_CI_RESULT)))
            .andExpect(jsonPath("$.[*].sonarResult").value(hasItem(DEFAULT_SONAR_RESULT)))
            .andExpect(jsonPath("$.[*].buildDate").value(hasItem(sameInstant(DEFAULT_BUILD_DATE))))
            .andExpect(jsonPath("$.[*].sonarProject").value(hasItem(DEFAULT_SONAR_PROJECT)));
    }
    
    @Test
    @Transactional
    public void getCIBuild() throws Exception {
        // Initialize the database
        cIBuildRepository.saveAndFlush(cIBuild);

        // Get the cIBuild
        restCIBuildMockMvc.perform(get("/api/ci-builds/{id}", cIBuild.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cIBuild.getId().intValue()))
            .andExpect(jsonPath("$.moduleName").value(DEFAULT_MODULE_NAME))
            .andExpect(jsonPath("$.moduleVersion").value(DEFAULT_MODULE_VERSION))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.ciResult").value(DEFAULT_CI_RESULT))
            .andExpect(jsonPath("$.sonarResult").value(DEFAULT_SONAR_RESULT))
            .andExpect(jsonPath("$.buildDate").value(sameInstant(DEFAULT_BUILD_DATE)))
            .andExpect(jsonPath("$.sonarProject").value(DEFAULT_SONAR_PROJECT));
    }

    @Test
    @Transactional
    public void getNonExistingCIBuild() throws Exception {
        // Get the cIBuild
        restCIBuildMockMvc.perform(get("/api/ci-builds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCIBuild() throws Exception {
        // Initialize the database
        cIBuildRepository.saveAndFlush(cIBuild);

        int databaseSizeBeforeUpdate = cIBuildRepository.findAll().size();

        // Update the cIBuild
        CIBuild updatedCIBuild = cIBuildRepository.findById(cIBuild.getId()).get();
        // Disconnect from session so that the updates on updatedCIBuild are not directly saved in db
        em.detach(updatedCIBuild);
        updatedCIBuild
            .moduleName(UPDATED_MODULE_NAME)
            .moduleVersion(UPDATED_MODULE_VERSION)
            .branch(UPDATED_BRANCH)
            .ciResult(UPDATED_CI_RESULT)
            .sonarResult(UPDATED_SONAR_RESULT)
            .buildDate(UPDATED_BUILD_DATE)
            .sonarProject(UPDATED_SONAR_PROJECT);
        CIBuildDTO cIBuildDTO = cIBuildMapper.toDto(updatedCIBuild);

        restCIBuildMockMvc.perform(put("/api/ci-builds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIBuildDTO)))
            .andExpect(status().isOk());

        // Validate the CIBuild in the database
        List<CIBuild> cIBuildList = cIBuildRepository.findAll();
        assertThat(cIBuildList).hasSize(databaseSizeBeforeUpdate);
        CIBuild testCIBuild = cIBuildList.get(cIBuildList.size() - 1);
        assertThat(testCIBuild.getModuleName()).isEqualTo(UPDATED_MODULE_NAME);
        assertThat(testCIBuild.getModuleVersion()).isEqualTo(UPDATED_MODULE_VERSION);
        assertThat(testCIBuild.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testCIBuild.getCiResult()).isEqualTo(UPDATED_CI_RESULT);
        assertThat(testCIBuild.getSonarResult()).isEqualTo(UPDATED_SONAR_RESULT);
        assertThat(testCIBuild.getBuildDate()).isEqualTo(UPDATED_BUILD_DATE);
        assertThat(testCIBuild.getSonarProject()).isEqualTo(UPDATED_SONAR_PROJECT);
    }

    @Test
    @Transactional
    public void updateNonExistingCIBuild() throws Exception {
        int databaseSizeBeforeUpdate = cIBuildRepository.findAll().size();

        // Create the CIBuild
        CIBuildDTO cIBuildDTO = cIBuildMapper.toDto(cIBuild);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCIBuildMockMvc.perform(put("/api/ci-builds")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cIBuildDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CIBuild in the database
        List<CIBuild> cIBuildList = cIBuildRepository.findAll();
        assertThat(cIBuildList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCIBuild() throws Exception {
        // Initialize the database
        cIBuildRepository.saveAndFlush(cIBuild);

        int databaseSizeBeforeDelete = cIBuildRepository.findAll().size();

        // Delete the cIBuild
        restCIBuildMockMvc.perform(delete("/api/ci-builds/{id}", cIBuild.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CIBuild> cIBuildList = cIBuildRepository.findAll();
        assertThat(cIBuildList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
