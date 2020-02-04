package com.bytatech.ayoos.consultation.repository.search;

import com.bytatech.ayoos.consultation.domain.Prescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Prescription} entity.
 */
public interface PrescriptionSearchRepository extends ElasticsearchRepository<Prescription, Long> {
}
