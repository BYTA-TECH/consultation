package com.bytatech.ayoos.consultation.web.rest;

import com.bytatech.ayoos.consultation.ConsultationApp;
import com.bytatech.ayoos.consultation.config.TestSecurityConfiguration;
import com.bytatech.ayoos.consultation.domain.ParamedicalExamination;
import com.bytatech.ayoos.consultation.repository.ParamedicalExaminationRepository;
import com.bytatech.ayoos.consultation.repository.search.ParamedicalExaminationSearchRepository;
import com.bytatech.ayoos.consultation.service.ParamedicalExaminationService;
import com.bytatech.ayoos.consultation.service.dto.ParamedicalExaminationDTO;
import com.bytatech.ayoos.consultation.service.mapper.ParamedicalExaminationMapper;
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
 * Integration tests for the {@link ParamedicalExaminationResource} REST controller.
 */
@SpringBootTest(classes = {ConsultationApp.class, TestSecurityConfiguration.class})
public class ParamedicalExaminationResourceIT {

    private static final String DEFAULT_BP = "AAAAAAAAAA";
    private static final String UPDATED_BP = "BBBBBBBBBB";

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Double DEFAULT_TEMPERATURE = 1D;
    private static final Double UPDATED_TEMPERATURE = 2D;

    @Autowired
    private ParamedicalExaminationRepository paramedicalExaminationRepository;

    @Autowired
    private ParamedicalExaminationMapper paramedicalExaminationMapper;

    @Autowired
    private ParamedicalExaminationService paramedicalExaminationService;

    /**
     * This repository is mocked in the com.bytatech.ayoos.consultation.repository.search test package.
     *
     * @see com.bytatech.ayoos.consultation.repository.search.ParamedicalExaminationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParamedicalExaminationSearchRepository mockParamedicalExaminationSearchRepository;

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

    private MockMvc restParamedicalExaminationMockMvc;

    private ParamedicalExamination paramedicalExamination;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParamedicalExaminationResource paramedicalExaminationResource = new ParamedicalExaminationResource(paramedicalExaminationService);
        this.restParamedicalExaminationMockMvc = MockMvcBuilders.standaloneSetup(paramedicalExaminationResource)
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
    public static ParamedicalExamination createEntity(EntityManager em) {
        ParamedicalExamination paramedicalExamination = new ParamedicalExamination()
            .bp(DEFAULT_BP)
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .temperature(DEFAULT_TEMPERATURE);
        return paramedicalExamination;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamedicalExamination createUpdatedEntity(EntityManager em) {
        ParamedicalExamination paramedicalExamination = new ParamedicalExamination()
            .bp(UPDATED_BP)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .temperature(UPDATED_TEMPERATURE);
        return paramedicalExamination;
    }

    @BeforeEach
    public void initTest() {
        paramedicalExamination = createEntity(em);
    }

    @Test
    @Transactional
    public void createParamedicalExamination() throws Exception {
        int databaseSizeBeforeCreate = paramedicalExaminationRepository.findAll().size();

        // Create the ParamedicalExamination
        ParamedicalExaminationDTO paramedicalExaminationDTO = paramedicalExaminationMapper.toDto(paramedicalExamination);
        restParamedicalExaminationMockMvc.perform(post("/api/paramedical-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramedicalExaminationDTO)))
            .andExpect(status().isCreated());

        // Validate the ParamedicalExamination in the database
        List<ParamedicalExamination> paramedicalExaminationList = paramedicalExaminationRepository.findAll();
        assertThat(paramedicalExaminationList).hasSize(databaseSizeBeforeCreate + 1);
        ParamedicalExamination testParamedicalExamination = paramedicalExaminationList.get(paramedicalExaminationList.size() - 1);
        assertThat(testParamedicalExamination.getBp()).isEqualTo(DEFAULT_BP);
        assertThat(testParamedicalExamination.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testParamedicalExamination.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testParamedicalExamination.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);

        // Validate the ParamedicalExamination in Elasticsearch
        verify(mockParamedicalExaminationSearchRepository, times(1)).save(testParamedicalExamination);
    }

    @Test
    @Transactional
    public void createParamedicalExaminationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramedicalExaminationRepository.findAll().size();

        // Create the ParamedicalExamination with an existing ID
        paramedicalExamination.setId(1L);
        ParamedicalExaminationDTO paramedicalExaminationDTO = paramedicalExaminationMapper.toDto(paramedicalExamination);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamedicalExaminationMockMvc.perform(post("/api/paramedical-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramedicalExaminationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParamedicalExamination in the database
        List<ParamedicalExamination> paramedicalExaminationList = paramedicalExaminationRepository.findAll();
        assertThat(paramedicalExaminationList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParamedicalExamination in Elasticsearch
        verify(mockParamedicalExaminationSearchRepository, times(0)).save(paramedicalExamination);
    }


    @Test
    @Transactional
    public void getAllParamedicalExaminations() throws Exception {
        // Initialize the database
        paramedicalExaminationRepository.saveAndFlush(paramedicalExamination);

        // Get all the paramedicalExaminationList
        restParamedicalExaminationMockMvc.perform(get("/api/paramedical-examinations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramedicalExamination.getId().intValue())))
            .andExpect(jsonPath("$.[*].bp").value(hasItem(DEFAULT_BP)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getParamedicalExamination() throws Exception {
        // Initialize the database
        paramedicalExaminationRepository.saveAndFlush(paramedicalExamination);

        // Get the paramedicalExamination
        restParamedicalExaminationMockMvc.perform(get("/api/paramedical-examinations/{id}", paramedicalExamination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paramedicalExamination.getId().intValue()))
            .andExpect(jsonPath("$.bp").value(DEFAULT_BP))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParamedicalExamination() throws Exception {
        // Get the paramedicalExamination
        restParamedicalExaminationMockMvc.perform(get("/api/paramedical-examinations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamedicalExamination() throws Exception {
        // Initialize the database
        paramedicalExaminationRepository.saveAndFlush(paramedicalExamination);

        int databaseSizeBeforeUpdate = paramedicalExaminationRepository.findAll().size();

        // Update the paramedicalExamination
        ParamedicalExamination updatedParamedicalExamination = paramedicalExaminationRepository.findById(paramedicalExamination.getId()).get();
        // Disconnect from session so that the updates on updatedParamedicalExamination are not directly saved in db
        em.detach(updatedParamedicalExamination);
        updatedParamedicalExamination
            .bp(UPDATED_BP)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .temperature(UPDATED_TEMPERATURE);
        ParamedicalExaminationDTO paramedicalExaminationDTO = paramedicalExaminationMapper.toDto(updatedParamedicalExamination);

        restParamedicalExaminationMockMvc.perform(put("/api/paramedical-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramedicalExaminationDTO)))
            .andExpect(status().isOk());

        // Validate the ParamedicalExamination in the database
        List<ParamedicalExamination> paramedicalExaminationList = paramedicalExaminationRepository.findAll();
        assertThat(paramedicalExaminationList).hasSize(databaseSizeBeforeUpdate);
        ParamedicalExamination testParamedicalExamination = paramedicalExaminationList.get(paramedicalExaminationList.size() - 1);
        assertThat(testParamedicalExamination.getBp()).isEqualTo(UPDATED_BP);
        assertThat(testParamedicalExamination.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testParamedicalExamination.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testParamedicalExamination.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);

        // Validate the ParamedicalExamination in Elasticsearch
        verify(mockParamedicalExaminationSearchRepository, times(1)).save(testParamedicalExamination);
    }

    @Test
    @Transactional
    public void updateNonExistingParamedicalExamination() throws Exception {
        int databaseSizeBeforeUpdate = paramedicalExaminationRepository.findAll().size();

        // Create the ParamedicalExamination
        ParamedicalExaminationDTO paramedicalExaminationDTO = paramedicalExaminationMapper.toDto(paramedicalExamination);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParamedicalExaminationMockMvc.perform(put("/api/paramedical-examinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramedicalExaminationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParamedicalExamination in the database
        List<ParamedicalExamination> paramedicalExaminationList = paramedicalExaminationRepository.findAll();
        assertThat(paramedicalExaminationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParamedicalExamination in Elasticsearch
        verify(mockParamedicalExaminationSearchRepository, times(0)).save(paramedicalExamination);
    }

    @Test
    @Transactional
    public void deleteParamedicalExamination() throws Exception {
        // Initialize the database
        paramedicalExaminationRepository.saveAndFlush(paramedicalExamination);

        int databaseSizeBeforeDelete = paramedicalExaminationRepository.findAll().size();

        // Delete the paramedicalExamination
        restParamedicalExaminationMockMvc.perform(delete("/api/paramedical-examinations/{id}", paramedicalExamination.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParamedicalExamination> paramedicalExaminationList = paramedicalExaminationRepository.findAll();
        assertThat(paramedicalExaminationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParamedicalExamination in Elasticsearch
        verify(mockParamedicalExaminationSearchRepository, times(1)).deleteById(paramedicalExamination.getId());
    }

    @Test
    @Transactional
    public void searchParamedicalExamination() throws Exception {
        // Initialize the database
        paramedicalExaminationRepository.saveAndFlush(paramedicalExamination);
        when(mockParamedicalExaminationSearchRepository.search(queryStringQuery("id:" + paramedicalExamination.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paramedicalExamination), PageRequest.of(0, 1), 1));
        // Search the paramedicalExamination
        restParamedicalExaminationMockMvc.perform(get("/api/_search/paramedical-examinations?query=id:" + paramedicalExamination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramedicalExamination.getId().intValue())))
            .andExpect(jsonPath("$.[*].bp").value(hasItem(DEFAULT_BP)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())));
    }
}
