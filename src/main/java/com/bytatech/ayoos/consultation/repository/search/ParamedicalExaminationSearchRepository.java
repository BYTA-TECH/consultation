package com.bytatech.ayoos.consultation.repository.search;

import com.bytatech.ayoos.consultation.domain.ParamedicalExamination;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ParamedicalExamination} entity.
 */
public interface ParamedicalExaminationSearchRepository extends ElasticsearchRepository<ParamedicalExamination, Long> {
}
