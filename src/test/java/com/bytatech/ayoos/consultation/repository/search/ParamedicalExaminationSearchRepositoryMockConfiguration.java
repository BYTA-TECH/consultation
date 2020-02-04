package com.bytatech.ayoos.consultation.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ParamedicalExaminationSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ParamedicalExaminationSearchRepositoryMockConfiguration {

    @MockBean
    private ParamedicalExaminationSearchRepository mockParamedicalExaminationSearchRepository;

}
