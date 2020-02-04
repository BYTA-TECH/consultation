package com.bytatech.ayoos.consultation.service.impl;

import com.bytatech.ayoos.consultation.service.PrescriptionService;
import com.bytatech.ayoos.consultation.domain.Prescription;
import com.bytatech.ayoos.consultation.repository.PrescriptionRepository;
import com.bytatech.ayoos.consultation.repository.search.PrescriptionSearchRepository;
import com.bytatech.ayoos.consultation.service.dto.PrescriptionDTO;
import com.bytatech.ayoos.consultation.service.mapper.PrescriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Prescription}.
 */
@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final Logger log = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

    private final PrescriptionRepository prescriptionRepository;

    private final PrescriptionMapper prescriptionMapper;

    private final PrescriptionSearchRepository prescriptionSearchRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, PrescriptionMapper prescriptionMapper, PrescriptionSearchRepository prescriptionSearchRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
        this.prescriptionSearchRepository = prescriptionSearchRepository;
    }

    /**
     * Save a prescription.
     *
     * @param prescriptionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PrescriptionDTO save(PrescriptionDTO prescriptionDTO) {
        log.debug("Request to save Prescription : {}", prescriptionDTO);
        Prescription prescription = prescriptionMapper.toEntity(prescriptionDTO);
        prescription = prescriptionRepository.save(prescription);
        PrescriptionDTO result = prescriptionMapper.toDto(prescription);
        prescriptionSearchRepository.save(prescription);
        return result;
    }

    /**
     * Get all the prescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prescriptions");
        return prescriptionRepository.findAll(pageable)
            .map(prescriptionMapper::toDto);
    }


    /**
     * Get one prescription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PrescriptionDTO> findOne(Long id) {
        log.debug("Request to get Prescription : {}", id);
        return prescriptionRepository.findById(id)
            .map(prescriptionMapper::toDto);
    }

    /**
     * Delete the prescription by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prescription : {}", id);
        prescriptionRepository.deleteById(id);
        prescriptionSearchRepository.deleteById(id);
    }

    /**
     * Search for the prescription corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrescriptionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Prescriptions for query {}", query);
        return prescriptionSearchRepository.search(queryStringQuery(query), pageable)
            .map(prescriptionMapper::toDto);
    }
}
