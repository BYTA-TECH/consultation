package com.bytatech.ayoos.consultation.service.impl;

import com.bytatech.ayoos.consultation.service.ConsultationService;
import com.bytatech.ayoos.consultation.domain.Consultation;
import com.bytatech.ayoos.consultation.repository.ConsultationRepository;
import com.bytatech.ayoos.consultation.repository.search.ConsultationSearchRepository;
import com.bytatech.ayoos.consultation.service.dto.ConsultationDTO;
import com.bytatech.ayoos.consultation.service.mapper.ConsultationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Consultation}.
 */
@Service
@Transactional
public class ConsultationServiceImpl implements ConsultationService {

    private final Logger log = LoggerFactory.getLogger(ConsultationServiceImpl.class);

    private final ConsultationRepository consultationRepository;

    private final ConsultationMapper consultationMapper;

    private final ConsultationSearchRepository consultationSearchRepository;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository, ConsultationMapper consultationMapper, ConsultationSearchRepository consultationSearchRepository) {
        this.consultationRepository = consultationRepository;
        this.consultationMapper = consultationMapper;
        this.consultationSearchRepository = consultationSearchRepository;
    }

    /**
     * Save a consultation.
     *
     * @param consultationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConsultationDTO save(ConsultationDTO consultationDTO) {
        log.debug("Request to save Consultation : {}", consultationDTO);
        Consultation consultation = consultationMapper.toEntity(consultationDTO);
        consultation = consultationRepository.save(consultation);
        ConsultationDTO result = consultationMapper.toDto(consultation);
        consultationSearchRepository.save(consultation);
        return result;
    }

    /**
     * Get all the consultations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consultations");
        return consultationRepository.findAll(pageable)
            .map(consultationMapper::toDto);
    }


    /**
     * Get one consultation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConsultationDTO> findOne(Long id) {
        log.debug("Request to get Consultation : {}", id);
        return consultationRepository.findById(id)
            .map(consultationMapper::toDto);
    }

    /**
     * Delete the consultation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consultation : {}", id);
        consultationRepository.deleteById(id);
        consultationSearchRepository.deleteById(id);
    }

    /**
     * Search for the consultation corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Consultations for query {}", query);
        return consultationSearchRepository.search(queryStringQuery(query), pageable)
            .map(consultationMapper::toDto);
    }
}
