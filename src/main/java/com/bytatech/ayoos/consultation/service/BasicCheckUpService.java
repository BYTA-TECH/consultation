package com.bytatech.ayoos.consultation.service;

import com.bytatech.ayoos.consultation.service.dto.BasicCheckUpDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytatech.ayoos.consultation.domain.BasicCheckUp}.
 */
public interface BasicCheckUpService {

    /**
     * Save a basicCheckUp.
     *
     * @param basicCheckUpDTO the entity to save.
     * @return the persisted entity.
     */
    BasicCheckUpDTO save(BasicCheckUpDTO basicCheckUpDTO);

    /**
     * Get all the basicCheckUps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BasicCheckUpDTO> findAll(Pageable pageable);


    /**
     * Get the "id" basicCheckUp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BasicCheckUpDTO> findOne(Long id);

    /**
     * Delete the "id" basicCheckUp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the basicCheckUp corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BasicCheckUpDTO> search(String query, Pageable pageable);
}
