package com.bytatech.ayoos.consultation.service.impl;

import com.bytatech.ayoos.consultation.service.BasicCheckUpService;
import com.bytatech.ayoos.consultation.domain.BasicCheckUp;
import com.bytatech.ayoos.consultation.repository.BasicCheckUpRepository;
import com.bytatech.ayoos.consultation.repository.search.BasicCheckUpSearchRepository;
import com.bytatech.ayoos.consultation.service.dto.BasicCheckUpDTO;
import com.bytatech.ayoos.consultation.service.mapper.BasicCheckUpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link BasicCheckUp}.
 */
@Service
@Transactional
public class BasicCheckUpServiceImpl implements BasicCheckUpService {

    private final Logger log = LoggerFactory.getLogger(BasicCheckUpServiceImpl.class);

    private final BasicCheckUpRepository basicCheckUpRepository;

    private final BasicCheckUpMapper basicCheckUpMapper;

    private final BasicCheckUpSearchRepository basicCheckUpSearchRepository;

    public BasicCheckUpServiceImpl(BasicCheckUpRepository basicCheckUpRepository, BasicCheckUpMapper basicCheckUpMapper, BasicCheckUpSearchRepository basicCheckUpSearchRepository) {
        this.basicCheckUpRepository = basicCheckUpRepository;
        this.basicCheckUpMapper = basicCheckUpMapper;
        this.basicCheckUpSearchRepository = basicCheckUpSearchRepository;
    }

    /**
     * Save a basicCheckUp.
     *
     * @param basicCheckUpDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BasicCheckUpDTO save(BasicCheckUpDTO basicCheckUpDTO) {
        log.debug("Request to save BasicCheckUp : {}", basicCheckUpDTO);
        BasicCheckUp basicCheckUp = basicCheckUpMapper.toEntity(basicCheckUpDTO);
        basicCheckUp = basicCheckUpRepository.save(basicCheckUp);
        BasicCheckUpDTO result = basicCheckUpMapper.toDto(basicCheckUp);
        basicCheckUpSearchRepository.save(basicCheckUp);
        return result;
    }

    /**
     * Get all the basicCheckUps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BasicCheckUpDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BasicCheckUps");
        return basicCheckUpRepository.findAll(pageable)
            .map(basicCheckUpMapper::toDto);
    }


    /**
     * Get one basicCheckUp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BasicCheckUpDTO> findOne(Long id) {
        log.debug("Request to get BasicCheckUp : {}", id);
        return basicCheckUpRepository.findById(id)
            .map(basicCheckUpMapper::toDto);
    }

    /**
     * Delete the basicCheckUp by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BasicCheckUp : {}", id);
        basicCheckUpRepository.deleteById(id);
        basicCheckUpSearchRepository.deleteById(id);
    }

    /**
     * Search for the basicCheckUp corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BasicCheckUpDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BasicCheckUps for query {}", query);
        return basicCheckUpSearchRepository.search(queryStringQuery(query), pageable)
            .map(basicCheckUpMapper::toDto);
    }
}
