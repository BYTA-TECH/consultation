package com.bytatech.ayoos.consultation.service;

import com.bytatech.ayoos.consultation.service.dto.PrescriptionDTO;

import net.sf.jasperreports.engine.JRException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bytatech.ayoos.consultation.domain.Prescription}.
 */
public interface PrescriptionService {

    /**
     * Save a prescription.
     *
     * @param prescriptionDTO the entity to save.
     * @return the persisted entity.
     */
    PrescriptionDTO save(PrescriptionDTO prescriptionDTO);

    /**
     * Get all the prescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrescriptionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" prescription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrescriptionDTO> findOne(Long id);

    /**
     * Delete the "id" prescription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the prescription corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrescriptionDTO> search(String query, Pageable pageable);

    /**
     * Gets prescriptionReport : using javabean.
     *
     * @return the byte[].
     *
     * @throws JRException.
     */
    
	byte[] getReportAsPdfUsingJavaBean() throws JRException;
}

