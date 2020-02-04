package com.bytatech.ayoos.consultation.repository.search;

import com.bytatech.ayoos.consultation.domain.Diagnosis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Diagnosis} entity.
 */
public interface DiagnosisSearchRepository extends ElasticsearchRepository<Diagnosis, Long> {
}
