package com.bytatech.ayoos.consultation.web.rest;

import com.bytatech.ayoos.consultation.ConsultationApp;
import com.bytatech.ayoos.consultation.config.TestSecurityConfiguration;
import com.bytatech.ayoos.consultation.domain.BasicCheckUp;
import com.bytatech.ayoos.consultation.repository.BasicCheckUpRepository;
import com.bytatech.ayoos.consultation.repository.search.BasicCheckUpSearchRepository;
import com.bytatech.ayoos.consultation.service.BasicCheckUpService;
import com.bytatech.ayoos.consultation.service.dto.BasicCheckUpDTO;
import com.bytatech.ayoos.consultation.service.mapper.BasicCheckUpMapper;
import com.bytatech.ayoos.consultation.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.bytatech.ayoos.consultation.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BasicCheckUpResource} REST controller.
 */
@SpringBootTest(classes = {ConsultationApp.class, TestSecurityConfiguration.class})
public class BasicCheckUpResourceIT {

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Double DEFAULT_TEMPERATURE = 1D;
    private static final Double UPDATED_TEMPERATURE = 2D;

    private static final String DEFAULT_BP = "AAAAAAAAAA";
    private static final String UPDATED_BP = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_UP_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_UP_STATUS = "BBBBBBBBBB";

    @Autowired
    private BasicCheckUpRepository basicCheckUpRepository;

    @Autowired
    private BasicCheckUpMapper basicCheckUpMapper;

    @Autowired
    private BasicCheckUpService basicCheckUpService;

    /**
     * This repository is mocked in the com.bytatech.ayoos.consultation.repository.search test package.
     *
     * @see com.bytatech.ayoos.consultation.repository.search.BasicCheckUpSearchRepositoryMockConfiguration
     */
    @Autowired
    private BasicCheckUpSearchRepository mockBasicCheckUpSearchRepository;

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

    private MockMvc restBasicCheckUpMockMvc;

    private BasicCheckUp basicCheckUp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BasicCheckUpResource basicCheckUpResource = new BasicCheckUpResource(basicCheckUpService);
        this.restBasicCheckUpMockMvc = MockMvcBuilders.standaloneSetup(basicCheckUpResource)
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
    public static BasicCheckUp createEntity(EntityManager em) {
        BasicCheckUp basicCheckUp = new BasicCheckUp()
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .temperature(DEFAULT_TEMPERATURE)
            .bp(DEFAULT_BP)
            .checkUpStatus(DEFAULT_CHECK_UP_STATUS);
        return basicCheckUp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BasicCheckUp createUpdatedEntity(EntityManager em) {
        BasicCheckUp basicCheckUp = new BasicCheckUp()
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .temperature(UPDATED_TEMPERATURE)
            .bp(UPDATED_BP)
            .checkUpStatus(UPDATED_CHECK_UP_STATUS);
        return basicCheckUp;
    }

    @BeforeEach
    public void initTest() {
        basicCheckUp = createEntity(em);
    }

    @Test
    @Transactional
    public void createBasicCheckUp() throws Exception {
        int databaseSizeBeforeCreate = basicCheckUpRepository.findAll().size();

        // Create the BasicCheckUp
        BasicCheckUpDTO basicCheckUpDTO = basicCheckUpMapper.toDto(basicCheckUp);
        restBasicCheckUpMockMvc.perform(post("/api/basic-check-ups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicCheckUpDTO)))
            .andExpect(status().isCreated());

        // Validate the BasicCheckUp in the database
        List<BasicCheckUp> basicCheckUpList = basicCheckUpRepository.findAll();
        assertThat(basicCheckUpList).hasSize(databaseSizeBeforeCreate + 1);
        BasicCheckUp testBasicCheckUp = basicCheckUpList.get(basicCheckUpList.size() - 1);
        assertThat(testBasicCheckUp.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testBasicCheckUp.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testBasicCheckUp.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testBasicCheckUp.getBp()).isEqualTo(DEFAULT_BP);
        assertThat(testBasicCheckUp.getCheckUpStatus()).isEqualTo(DEFAULT_CHECK_UP_STATUS);

        // Validate the BasicCheckUp in Elasticsearch
        verify(mockBasicCheckUpSearchRepository, times(1)).save(testBasicCheckUp);
    }

    @Test
    @Transactional
    public void createBasicCheckUpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = basicCheckUpRepository.findAll().size();

        // Create the BasicCheckUp with an existing ID
        basicCheckUp.setId(1L);
        BasicCheckUpDTO basicCheckUpDTO = basicCheckUpMapper.toDto(basicCheckUp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBasicCheckUpMockMvc.perform(post("/api/basic-check-ups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicCheckUpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BasicCheckUp in the database
        List<BasicCheckUp> basicCheckUpList = basicCheckUpRepository.findAll();
        assertThat(basicCheckUpList).hasSize(databaseSizeBeforeCreate);

        // Validate the BasicCheckUp in Elasticsearch
        verify(mockBasicCheckUpSearchRepository, times(0)).save(basicCheckUp);
    }


    @Test
    @Transactional
    public void getAllBasicCheckUps() throws Exception {
        // Initialize the database
        basicCheckUpRepository.saveAndFlush(basicCheckUp);

        // Get all the basicCheckUpList
        restBasicCheckUpMockMvc.perform(get("/api/basic-check-ups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(basicCheckUp.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].bp").value(hasItem(DEFAULT_BP)))
            .andExpect(jsonPath("$.[*].checkUpStatus").value(hasItem(DEFAULT_CHECK_UP_STATUS)));
    }
    
    @Test
    @Transactional
    public void getBasicCheckUp() throws Exception {
        // Initialize the database
        basicCheckUpRepository.saveAndFlush(basicCheckUp);

        // Get the basicCheckUp
        restBasicCheckUpMockMvc.perform(get("/api/basic-check-ups/{id}", basicCheckUp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(basicCheckUp.getId().intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.bp").value(DEFAULT_BP))
            .andExpect(jsonPath("$.checkUpStatus").value(DEFAULT_CHECK_UP_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingBasicCheckUp() throws Exception {
        // Get the basicCheckUp
        restBasicCheckUpMockMvc.perform(get("/api/basic-check-ups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBasicCheckUp() throws Exception {
        // Initialize the database
        basicCheckUpRepository.saveAndFlush(basicCheckUp);

        int databaseSizeBeforeUpdate = basicCheckUpRepository.findAll().size();

        // Update the basicCheckUp
        BasicCheckUp updatedBasicCheckUp = basicCheckUpRepository.findById(basicCheckUp.getId()).get();
        // Disconnect from session so that the updates on updatedBasicCheckUp are not directly saved in db
        em.detach(updatedBasicCheckUp);
        updatedBasicCheckUp
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .temperature(UPDATED_TEMPERATURE)
            .bp(UPDATED_BP)
            .checkUpStatus(UPDATED_CHECK_UP_STATUS);
        BasicCheckUpDTO basicCheckUpDTO = basicCheckUpMapper.toDto(updatedBasicCheckUp);

        restBasicCheckUpMockMvc.perform(put("/api/basic-check-ups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicCheckUpDTO)))
            .andExpect(status().isOk());

        // Validate the BasicCheckUp in the database
        List<BasicCheckUp> basicCheckUpList = basicCheckUpRepository.findAll();
        assertThat(basicCheckUpList).hasSize(databaseSizeBeforeUpdate);
        BasicCheckUp testBasicCheckUp = basicCheckUpList.get(basicCheckUpList.size() - 1);
        assertThat(testBasicCheckUp.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testBasicCheckUp.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testBasicCheckUp.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testBasicCheckUp.getBp()).isEqualTo(UPDATED_BP);
        assertThat(testBasicCheckUp.getCheckUpStatus()).isEqualTo(UPDATED_CHECK_UP_STATUS);

        // Validate the BasicCheckUp in Elasticsearch
        verify(mockBasicCheckUpSearchRepository, times(1)).save(testBasicCheckUp);
    }

    @Test
    @Transactional
    public void updateNonExistingBasicCheckUp() throws Exception {
        int databaseSizeBeforeUpdate = basicCheckUpRepository.findAll().size();

        // Create the BasicCheckUp
        BasicCheckUpDTO basicCheckUpDTO = basicCheckUpMapper.toDto(basicCheckUp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBasicCheckUpMockMvc.perform(put("/api/basic-check-ups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicCheckUpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BasicCheckUp in the database
        List<BasicCheckUp> basicCheckUpList = basicCheckUpRepository.findAll();
        assertThat(basicCheckUpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BasicCheckUp in Elasticsearch
        verify(mockBasicCheckUpSearchRepository, times(0)).save(basicCheckUp);
    }

    @Test
    @Transactional
    public void deleteBasicCheckUp() throws Exception {
        // Initialize the database
        basicCheckUpRepository.saveAndFlush(basicCheckUp);

        int databaseSizeBeforeDelete = basicCheckUpRepository.findAll().size();

        // Delete the basicCheckUp
        restBasicCheckUpMockMvc.perform(delete("/api/basic-check-ups/{id}", basicCheckUp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BasicCheckUp> basicCheckUpList = basicCheckUpRepository.findAll();
        assertThat(basicCheckUpList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BasicCheckUp in Elasticsearch
        verify(mockBasicCheckUpSearchRepository, times(1)).deleteById(basicCheckUp.getId());
    }

    @Test
    @Transactional
    public void searchBasicCheckUp() throws Exception {
        // Initialize the database
        basicCheckUpRepository.saveAndFlush(basicCheckUp);
        when(mockBasicCheckUpSearchRepository.search(queryStringQuery("id:" + basicCheckUp.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(basicCheckUp), PageRequest.of(0, 1), 1));
        // Search the basicCheckUp
        restBasicCheckUpMockMvc.perform(get("/api/_search/basic-check-ups?query=id:" + basicCheckUp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(basicCheckUp.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].bp").value(hasItem(DEFAULT_BP)))
            .andExpect(jsonPath("$.[*].checkUpStatus").value(hasItem(DEFAULT_CHECK_UP_STATUS)));
    }
}
