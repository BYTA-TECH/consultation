package com.bytatech.ayoos.consultation.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SymptomSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SymptomSearchRepositoryMockConfiguration {

    @MockBean
    private SymptomSearchRepository mockSymptomSearchRepository;

}
