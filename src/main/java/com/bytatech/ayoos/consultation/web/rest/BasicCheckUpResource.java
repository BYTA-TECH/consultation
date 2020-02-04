package com.bytatech.ayoos.consultation.web.rest;

import com.bytatech.ayoos.consultation.service.BasicCheckUpService;
import com.bytatech.ayoos.consultation.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.consultation.service.dto.BasicCheckUpDTO;

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
 * REST controller for managing {@link com.bytatech.ayoos.consultation.domain.BasicCheckUp}.
 */
@RestController
@RequestMapping("/api")
public class BasicCheckUpResource {

    private final Logger log = LoggerFactory.getLogger(BasicCheckUpResource.class);

    private static final String ENTITY_NAME = "consultationBasicCheckUp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BasicCheckUpService basicCheckUpService;

    public BasicCheckUpResource(BasicCheckUpService basicCheckUpService) {
        this.basicCheckUpService = basicCheckUpService;
    }

    /**
     * {@code POST  /basic-check-ups} : Create a new basicCheckUp.
     *
     * @param basicCheckUpDTO the basicCheckUpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new basicCheckUpDTO, or with status {@code 400 (Bad Request)} if the basicCheckUp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/basic-check-ups")
    public ResponseEntity<BasicCheckUpDTO> createBasicCheckUp(@RequestBody BasicCheckUpDTO basicCheckUpDTO) throws URISyntaxException {
        log.debug("REST request to save BasicCheckUp : {}", basicCheckUpDTO);
        if (basicCheckUpDTO.getId() != null) {
            throw new BadRequestAlertException("A new basicCheckUp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BasicCheckUpDTO result = basicCheckUpService.save(basicCheckUpDTO);
        return ResponseEntity.created(new URI("/api/basic-check-ups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /basic-check-ups} : Updates an existing basicCheckUp.
     *
     * @param basicCheckUpDTO the basicCheckUpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated basicCheckUpDTO,
     * or with status {@code 400 (Bad Request)} if the basicCheckUpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the basicCheckUpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/basic-check-ups")
    public ResponseEntity<BasicCheckUpDTO> updateBasicCheckUp(@RequestBody BasicCheckUpDTO basicCheckUpDTO) throws URISyntaxException {
        log.debug("REST request to update BasicCheckUp : {}", basicCheckUpDTO);
        if (basicCheckUpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BasicCheckUpDTO result = basicCheckUpService.save(basicCheckUpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, basicCheckUpDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /basic-check-ups} : get all the basicCheckUps.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of basicCheckUps in body.
     */
    @GetMapping("/basic-check-ups")
    public ResponseEntity<List<BasicCheckUpDTO>> getAllBasicCheckUps(Pageable pageable) {
        log.debug("REST request to get a page of BasicCheckUps");
        Page<BasicCheckUpDTO> page = basicCheckUpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /basic-check-ups/:id} : get the "id" basicCheckUp.
     *
     * @param id the id of the basicCheckUpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the basicCheckUpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/basic-check-ups/{id}")
    public ResponseEntity<BasicCheckUpDTO> getBasicCheckUp(@PathVariable Long id) {
        log.debug("REST request to get BasicCheckUp : {}", id);
        Optional<BasicCheckUpDTO> basicCheckUpDTO = basicCheckUpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(basicCheckUpDTO);
    }

    /**
     * {@code DELETE  /basic-check-ups/:id} : delete the "id" basicCheckUp.
     *
     * @param id the id of the basicCheckUpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/basic-check-ups/{id}")
    public ResponseEntity<Void> deleteBasicCheckUp(@PathVariable Long id) {
        log.debug("REST request to delete BasicCheckUp : {}", id);
        basicCheckUpService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/basic-check-ups?query=:query} : search for the basicCheckUp corresponding
     * to the query.
     *
     * @param query the query of the basicCheckUp search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/basic-check-ups")
    public ResponseEntity<List<BasicCheckUpDTO>> searchBasicCheckUps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BasicCheckUps for query {}", query);
        Page<BasicCheckUpDTO> page = basicCheckUpService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
