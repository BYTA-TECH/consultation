package com.bytatech.ayoos.consultation.web.rest;

import com.bytatech.ayoos.consultation.service.ParamedicalExaminationService;
import com.bytatech.ayoos.consultation.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.consultation.service.dto.ParamedicalExaminationDTO;

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
 * REST controller for managing {@link com.bytatech.ayoos.consultation.domain.ParamedicalExamination}.
 */
@RestController
@RequestMapping("/api")
public class ParamedicalExaminationResource {

    private final Logger log = LoggerFactory.getLogger(ParamedicalExaminationResource.class);

    private static final String ENTITY_NAME = "consultationParamedicalExamination";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamedicalExaminationService paramedicalExaminationService;

    public ParamedicalExaminationResource(ParamedicalExaminationService paramedicalExaminationService) {
        this.paramedicalExaminationService = paramedicalExaminationService;
    }

    /**
     * {@code POST  /paramedical-examinations} : Create a new paramedicalExamination.
     *
     * @param paramedicalExaminationDTO the paramedicalExaminationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramedicalExaminationDTO, or with status {@code 400 (Bad Request)} if the paramedicalExamination has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paramedical-examinations")
    public ResponseEntity<ParamedicalExaminationDTO> createParamedicalExamination(@RequestBody ParamedicalExaminationDTO paramedicalExaminationDTO) throws URISyntaxException {
        log.debug("REST request to save ParamedicalExamination : {}", paramedicalExaminationDTO);
        if (paramedicalExaminationDTO.getId() != null) {
            throw new BadRequestAlertException("A new paramedicalExamination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParamedicalExaminationDTO result = paramedicalExaminationService.save(paramedicalExaminationDTO);
        return ResponseEntity.created(new URI("/api/paramedical-examinations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paramedical-examinations} : Updates an existing paramedicalExamination.
     *
     * @param paramedicalExaminationDTO the paramedicalExaminationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramedicalExaminationDTO,
     * or with status {@code 400 (Bad Request)} if the paramedicalExaminationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paramedicalExaminationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paramedical-examinations")
    public ResponseEntity<ParamedicalExaminationDTO> updateParamedicalExamination(@RequestBody ParamedicalExaminationDTO paramedicalExaminationDTO) throws URISyntaxException {
        log.debug("REST request to update ParamedicalExamination : {}", paramedicalExaminationDTO);
        if (paramedicalExaminationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParamedicalExaminationDTO result = paramedicalExaminationService.save(paramedicalExaminationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paramedicalExaminationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paramedical-examinations} : get all the paramedicalExaminations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paramedicalExaminations in body.
     */
    @GetMapping("/paramedical-examinations")
    public ResponseEntity<List<ParamedicalExaminationDTO>> getAllParamedicalExaminations(Pageable pageable) {
        log.debug("REST request to get a page of ParamedicalExaminations");
        Page<ParamedicalExaminationDTO> page = paramedicalExaminationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paramedical-examinations/:id} : get the "id" paramedicalExamination.
     *
     * @param id the id of the paramedicalExaminationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramedicalExaminationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paramedical-examinations/{id}")
    public ResponseEntity<ParamedicalExaminationDTO> getParamedicalExamination(@PathVariable Long id) {
        log.debug("REST request to get ParamedicalExamination : {}", id);
        Optional<ParamedicalExaminationDTO> paramedicalExaminationDTO = paramedicalExaminationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramedicalExaminationDTO);
    }

    /**
     * {@code DELETE  /paramedical-examinations/:id} : delete the "id" paramedicalExamination.
     *
     * @param id the id of the paramedicalExaminationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paramedical-examinations/{id}")
    public ResponseEntity<Void> deleteParamedicalExamination(@PathVariable Long id) {
        log.debug("REST request to delete ParamedicalExamination : {}", id);
        paramedicalExaminationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/paramedical-examinations?query=:query} : search for the paramedicalExamination corresponding
     * to the query.
     *
     * @param query the query of the paramedicalExamination search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/paramedical-examinations")
    public ResponseEntity<List<ParamedicalExaminationDTO>> searchParamedicalExaminations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParamedicalExaminations for query {}", query);
        Page<ParamedicalExaminationDTO> page = paramedicalExaminationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
