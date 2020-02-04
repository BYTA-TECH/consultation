package com.bytatech.ayoos.consultation.service;

import com.bytatech.ayoos.consultation.service.dto.ParamedicalExaminationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytatech.ayoos.consultation.domain.ParamedicalExamination}.
 */
public interface ParamedicalExaminationService {

    /**
     * Save a paramedicalExamination.
     *
     * @param paramedicalExaminationDTO the entity to save.
     * @return the persisted entity.
     */
    ParamedicalExaminationDTO save(ParamedicalExaminationDTO paramedicalExaminationDTO);

    /**
     * Get all the paramedicalExaminations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParamedicalExaminationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" paramedicalExamination.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParamedicalExaminationDTO> findOne(Long id);

    /**
     * Delete the "id" paramedicalExamination.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the paramedicalExamination corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParamedicalExaminationDTO> search(String query, Pageable pageable);
}
