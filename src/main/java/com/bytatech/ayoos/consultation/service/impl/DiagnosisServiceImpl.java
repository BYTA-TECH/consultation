package com.bytatech.ayoos.consultation.service.impl;

import com.bytatech.ayoos.consultation.service.DiagnosisService;
import com.bytatech.ayoos.consultation.domain.Diagnosis;
import com.bytatech.ayoos.consultation.repository.DiagnosisRepository;
import com.bytatech.ayoos.consultation.repository.search.DiagnosisSearchRepository;
import com.bytatech.ayoos.consultation.service.dto.DiagnosisDTO;
import com.bytatech.ayoos.consultation.service.mapper.DiagnosisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Diagnosis}.
 */
@Service
@Transactional
public class DiagnosisServiceImpl implements DiagnosisService {

    private final Logger log = LoggerFactory.getLogger(DiagnosisServiceImpl.class);

    private final DiagnosisRepository diagnosisRepository;

    private final DiagnosisMapper diagnosisMapper;

    private final DiagnosisSearchRepository diagnosisSearchRepository;

    public DiagnosisServiceImpl(DiagnosisRepository diagnosisRepository, DiagnosisMapper diagnosisMapper, DiagnosisSearchRepository diagnosisSearchRepository) {
        this.diagnosisRepository = diagnosisRepository;
        this.diagnosisMapper = diagnosisMapper;
        this.diagnosisSearchRepository = diagnosisSearchRepository;
    }

    /**
     * Save a diagnosis.
     *
     * @param diagnosisDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DiagnosisDTO save(DiagnosisDTO diagnosisDTO) {
        log.debug("Request to save Diagnosis : {}", diagnosisDTO);
        Diagnosis diagnosis = diagnosisMapper.toEntity(diagnosisDTO);
        diagnosis = diagnosisRepository.save(diagnosis);
        DiagnosisDTO result = diagnosisMapper.toDto(diagnosis);
        diagnosisSearchRepository.save(diagnosis);
        return result;
    }

    /**
     * Get all the diagnoses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiagnosisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Diagnoses");
        return diagnosisRepository.findAll(pageable)
            .map(diagnosisMapper::toDto);
    }


    /**
     * Get one diagnosis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiagnosisDTO> findOne(Long id) {
        log.debug("Request to get Diagnosis : {}", id);
        return diagnosisRepository.findById(id)
            .map(diagnosisMapper::toDto);
    }

    /**
     * Delete the diagnosis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Diagnosis : {}", id);
        diagnosisRepository.deleteById(id);
        diagnosisSearchRepository.deleteById(id);
    }

    /**
     * Search for the diagnosis corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiagnosisDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Diagnoses for query {}", query);
        return diagnosisSearchRepository.search(queryStringQuery(query), pageable)
            .map(diagnosisMapper::toDto);
    }
}
