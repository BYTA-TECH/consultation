package com.bytatech.ayoos.consultation.service;

import com.bytatech.ayoos.consultation.service.dto.ConsultationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytatech.ayoos.consultation.domain.Consultation}.
 */
public interface ConsultationService {

    /**
     * Save a consultation.
     *
     * @param consultationDTO the entity to save.
     * @return the persisted entity.
     */
    ConsultationDTO save(ConsultationDTO consultationDTO);

    /**
     * Get all the consultations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsultationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" consultation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsultationDTO> findOne(Long id);

    /**
     * Delete the "id" consultation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the consultation corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsultationDTO> search(String query, Pageable pageable);
}
