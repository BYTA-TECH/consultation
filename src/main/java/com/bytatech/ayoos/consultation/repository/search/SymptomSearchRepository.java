package com.bytatech.ayoos.consultation.repository.search;

import com.bytatech.ayoos.consultation.domain.Symptom;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Symptom} entity.
 */
public interface SymptomSearchRepository extends ElasticsearchRepository<Symptom, Long> {
}
