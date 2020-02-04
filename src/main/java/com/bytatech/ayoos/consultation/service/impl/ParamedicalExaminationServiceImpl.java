package com.bytatech.ayoos.consultation.service.impl;

import com.bytatech.ayoos.consultation.service.ParamedicalExaminationService;
import com.bytatech.ayoos.consultation.domain.ParamedicalExamination;
import com.bytatech.ayoos.consultation.repository.ParamedicalExaminationRepository;
import com.bytatech.ayoos.consultation.repository.search.ParamedicalExaminationSearchRepository;
import com.bytatech.ayoos.consultation.service.dto.ParamedicalExaminationDTO;
import com.bytatech.ayoos.consultation.service.mapper.ParamedicalExaminationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ParamedicalExamination}.
 */
@Service
@Transactional
public class ParamedicalExaminationServiceImpl implements ParamedicalExaminationService {

    private final Logger log = LoggerFactory.getLogger(ParamedicalExaminationServiceImpl.class);

    private final ParamedicalExaminationRepository paramedicalExaminationRepository;

    private final ParamedicalExaminationMapper paramedicalExaminationMapper;

    private final ParamedicalExaminationSearchRepository paramedicalExaminationSearchRepository;

    public ParamedicalExaminationServiceImpl(ParamedicalExaminationRepository paramedicalExaminationRepository, ParamedicalExaminationMapper paramedicalExaminationMapper, ParamedicalExaminationSearchRepository paramedicalExaminationSearchRepository) {
        this.paramedicalExaminationRepository = paramedicalExaminationRepository;
        this.paramedicalExaminationMapper = paramedicalExaminationMapper;
        this.paramedicalExaminationSearchRepository = paramedicalExaminationSearchRepository;
    }

    /**
     * Save a paramedicalExamination.
     *
     * @param paramedicalExaminationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ParamedicalExaminationDTO save(ParamedicalExaminationDTO paramedicalExaminationDTO) {
        log.debug("Request to save ParamedicalExamination : {}", paramedicalExaminationDTO);
        ParamedicalExamination paramedicalExamination = paramedicalExaminationMapper.toEntity(paramedicalExaminationDTO);
        paramedicalExamination = paramedicalExaminationRepository.save(paramedicalExamination);
        ParamedicalExaminationDTO result = paramedicalExaminationMapper.toDto(paramedicalExamination);
        paramedicalExaminationSearchRepository.save(paramedicalExamination);
        return result;
    }

    /**
     * Get all the paramedicalExaminations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParamedicalExaminationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParamedicalExaminations");
        return paramedicalExaminationRepository.findAll(pageable)
            .map(paramedicalExaminationMapper::toDto);
    }


    /**
     * Get one paramedicalExamination by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParamedicalExaminationDTO> findOne(Long id) {
        log.debug("Request to get ParamedicalExamination : {}", id);
        return paramedicalExaminationRepository.findById(id)
            .map(paramedicalExaminationMapper::toDto);
    }

    /**
     * Delete the paramedicalExamination by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParamedicalExamination : {}", id);
        paramedicalExaminationRepository.deleteById(id);
        paramedicalExaminationSearchRepository.deleteById(id);
    }

    /**
     * Search for the paramedicalExamination corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParamedicalExaminationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParamedicalExaminations for query {}", query);
        return paramedicalExaminationSearchRepository.search(queryStringQuery(query), pageable)
            .map(paramedicalExaminationMapper::toDto);
    }
}
