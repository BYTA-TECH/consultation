package com.bytatech.ayoos.consultation.repository.search;

import com.bytatech.ayoos.consultation.domain.BasicCheckUp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link BasicCheckUp} entity.
 */
public interface BasicCheckUpSearchRepository extends ElasticsearchRepository<BasicCheckUp, Long> {
}
