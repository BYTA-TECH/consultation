package com.bytatech.ayoos.consultation.web.rest;

import com.bytatech.ayoos.consultation.service.PrescriptionService;
import com.bytatech.ayoos.consultation.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.consultation.service.dto.PrescriptionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.bytatech.ayoos.consultation.domain.Prescription}.
 */
@RestController
@RequestMapping("/api")
public class PrescriptionResource {

    private final Logger log = LoggerFactory.getLogger(PrescriptionResource.class);

    private static final String ENTITY_NAME = "consultationPrescription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescriptionService prescriptionService;

    public PrescriptionResource(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    /**
     * {@code POST  /prescriptions} : Create a new prescription.
     *
     * @param prescriptionDTO the prescriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescriptionDTO, or with status {@code 400 (Bad Request)} if the prescription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prescriptions")
    public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody PrescriptionDTO prescriptionDTO) throws URISyntaxException {
        log.debug("REST request to save Prescription : {}", prescriptionDTO);
        if (prescriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new prescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrescriptionDTO result = prescriptionService.save(prescriptionDTO);
        return ResponseEntity.created(new URI("/api/prescriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prescriptions} : Updates an existing prescription.
     *
     * @param prescriptionDTO the prescriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prescriptionDTO,
     * or with status {@code 400 (Bad Request)} if the prescriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prescriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prescriptions")
    public ResponseEntity<PrescriptionDTO> updatePrescription(@RequestBody PrescriptionDTO prescriptionDTO) throws URISyntaxException {
        log.debug("REST request to update Prescription : {}", prescriptionDTO);
        if (prescriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrescriptionDTO result = prescriptionService.save(prescriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prescriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prescriptions} : get all the prescriptions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prescriptions in body.
     */
    @GetMapping("/prescriptions")
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions(Pageable pageable) {
        log.debug("REST request to get a page of Prescriptions");
        Page<PrescriptionDTO> page = prescriptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prescriptions/:id} : get the "id" prescription.
     *
     * @param id the id of the prescriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prescriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prescriptions/{id}")
    public ResponseEntity<PrescriptionDTO> getPrescription(@PathVariable Long id) {
        log.debug("REST request to get Prescription : {}", id);
        Optional<PrescriptionDTO> prescriptionDTO = prescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prescriptionDTO);
    }

    /**
     * {@code DELETE  /prescriptions/:id} : delete the "id" prescription.
     *
     * @param id the id of the prescriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prescriptions/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        log.debug("REST request to delete Prescription : {}", id);
        prescriptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prescriptions?query=:query} : search for the prescription corresponding
     * to the query.
     *
     * @param query the query of the prescription search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/prescriptions")
    public ResponseEntity<List<PrescriptionDTO>> searchPrescriptions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Prescriptions for query {}", query);
        Page<PrescriptionDTO> page = prescriptionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
