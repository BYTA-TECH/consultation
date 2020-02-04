package com.bytatech.ayoos.consultation.web.rest;

import com.bytatech.ayoos.consultation.ConsultationApp;
import com.bytatech.ayoos.consultation.config.TestSecurityConfiguration;
import com.bytatech.ayoos.consultation.domain.Consultation;
import com.bytatech.ayoos.consultation.repository.ConsultationRepository;
import com.bytatech.ayoos.consultation.repository.search.ConsultationSearchRepository;
import com.bytatech.ayoos.consultation.service.ConsultationService;
import com.bytatech.ayoos.consultation.service.dto.ConsultationDTO;
import com.bytatech.ayoos.consultation.service.mapper.ConsultationMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ConsultationResource} REST controller.
 */
@SpringBootTest(classes = {ConsultationApp.class, TestSecurityConfiguration.class})
public class ConsultationResourceIT {

    private static final String DEFAULT_TRACKING_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PATIENT_IDP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_IDP_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private ConsultationMapper consultationMapper;

    @Autowired
    private ConsultationService consultationService;

    /**
     * This repository is mocked in the com.bytatech.ayoos.consultation.repository.search test package.
     *
     * @see com.bytatech.ayoos.consultation.repository.search.ConsultationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConsultationSearchRepository mockConsultationSearchRepository;

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

    private MockMvc restConsultationMockMvc;

    private Consultation consultation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultationResource consultationResource = new ConsultationResource(consultationService);
        this.restConsultationMockMvc = MockMvcBuilders.standaloneSetup(consultationResource)
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
    public static Consultation createEntity(EntityManager em) {
        Consultation consultation = new Consultation()
            .trackingId(DEFAULT_TRACKING_ID)
            .patientIdpCode(DEFAULT_PATIENT_IDP_CODE)
            .date(DEFAULT_DATE)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return consultation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultation createUpdatedEntity(EntityManager em) {
        Consultation consultation = new Consultation()
            .trackingId(UPDATED_TRACKING_ID)
            .patientIdpCode(UPDATED_PATIENT_IDP_CODE)
            .date(UPDATED_DATE)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return consultation;
    }

    @BeforeEach
    public void initTest() {
        consultation = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultation() throws Exception {
        int databaseSizeBeforeCreate = consultationRepository.findAll().size();

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);
        restConsultationMockMvc.perform(post("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
            .andExpect(status().isCreated());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeCreate + 1);
        Consultation testConsultation = consultationList.get(consultationList.size() - 1);
        assertThat(testConsultation.getTrackingId()).isEqualTo(DEFAULT_TRACKING_ID);
        assertThat(testConsultation.getPatientIdpCode()).isEqualTo(DEFAULT_PATIENT_IDP_CODE);
        assertThat(testConsultation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testConsultation.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);

        // Validate the Consultation in Elasticsearch
        verify(mockConsultationSearchRepository, times(1)).save(testConsultation);
    }

    @Test
    @Transactional
    public void createConsultationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultationRepository.findAll().size();

        // Create the Consultation with an existing ID
        consultation.setId(1L);
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultationMockMvc.perform(post("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Consultation in Elasticsearch
        verify(mockConsultationSearchRepository, times(0)).save(consultation);
    }


    @Test
    @Transactional
    public void getAllConsultations() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get all the consultationList
        restConsultationMockMvc.perform(get("/api/consultations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultation.getId().intValue())))
            .andExpect(jsonPath("$.[*].trackingId").value(hasItem(DEFAULT_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].patientIdpCode").value(hasItem(DEFAULT_PATIENT_IDP_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get the consultation
        restConsultationMockMvc.perform(get("/api/consultations/{id}", consultation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultation.getId().intValue()))
            .andExpect(jsonPath("$.trackingId").value(DEFAULT_TRACKING_ID))
            .andExpect(jsonPath("$.patientIdpCode").value(DEFAULT_PATIENT_IDP_CODE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingConsultation() throws Exception {
        // Get the consultation
        restConsultationMockMvc.perform(get("/api/consultations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Update the consultation
        Consultation updatedConsultation = consultationRepository.findById(consultation.getId()).get();
        // Disconnect from session so that the updates on updatedConsultation are not directly saved in db
        em.detach(updatedConsultation);
        updatedConsultation
            .trackingId(UPDATED_TRACKING_ID)
            .patientIdpCode(UPDATED_PATIENT_IDP_CODE)
            .date(UPDATED_DATE)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        ConsultationDTO consultationDTO = consultationMapper.toDto(updatedConsultation);

        restConsultationMockMvc.perform(put("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
            .andExpect(status().isOk());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
        Consultation testConsultation = consultationList.get(consultationList.size() - 1);
        assertThat(testConsultation.getTrackingId()).isEqualTo(UPDATED_TRACKING_ID);
        assertThat(testConsultation.getPatientIdpCode()).isEqualTo(UPDATED_PATIENT_IDP_CODE);
        assertThat(testConsultation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testConsultation.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);

        // Validate the Consultation in Elasticsearch
        verify(mockConsultationSearchRepository, times(1)).save(testConsultation);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultation() throws Exception {
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultationMockMvc.perform(put("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Consultation in Elasticsearch
        verify(mockConsultationSearchRepository, times(0)).save(consultation);
    }

    @Test
    @Transactional
    public void deleteConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        int databaseSizeBeforeDelete = consultationRepository.findAll().size();

        // Delete the consultation
        restConsultationMockMvc.perform(delete("/api/consultations/{id}", consultation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Consultation in Elasticsearch
        verify(mockConsultationSearchRepository, times(1)).deleteById(consultation.getId());
    }

    @Test
    @Transactional
    public void searchConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);
        when(mockConsultationSearchRepository.search(queryStringQuery("id:" + consultation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(consultation), PageRequest.of(0, 1), 1));
        // Search the consultation
        restConsultationMockMvc.perform(get("/api/_search/consultations?query=id:" + consultation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultation.getId().intValue())))
            .andExpect(jsonPath("$.[*].trackingId").value(hasItem(DEFAULT_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].patientIdpCode").value(hasItem(DEFAULT_PATIENT_IDP_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
}
