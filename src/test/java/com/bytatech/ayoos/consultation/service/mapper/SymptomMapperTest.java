package com.bytatech.ayoos.consultation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SymptomMapperTest {

    private SymptomMapper symptomMapper;

    @BeforeEach
    public void setUp() {
        symptomMapper = new SymptomMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(symptomMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(symptomMapper.fromId(null)).isNull();
    }
}
