package com.bytatech.ayoos.consultation.service;

import com.bytatech.ayoos.consultation.service.dto.DiagnosisDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytatech.ayoos.consultation.domain.Diagnosis}.
 */
public interface DiagnosisService {

    /**
     * Save a diagnosis.
     *
     * @param diagnosisDTO the entity to save.
     * @return the persisted entity.
     */
    DiagnosisDTO save(DiagnosisDTO diagnosisDTO);

    /**
     * Get all the diagnoses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiagnosisDTO> findAll(Pageable pageable);


    /**
     * Get the "id" diagnosis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiagnosisDTO> findOne(Long id);

    /**
     * Delete the "id" diagnosis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the diagnosis corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiagnosisDTO> search(String query, Pageable pageable);
}
