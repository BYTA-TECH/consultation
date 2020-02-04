package com.bytatech.ayoos.consultation.repository.search;

import com.bytatech.ayoos.consultation.domain.Consultation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Consultation} entity.
 */
public interface ConsultationSearchRepository extends ElasticsearchRepository<Consultation, Long> {
}
