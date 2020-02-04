package com.bytatech.ayoos.consultation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DiagnosisMapperTest {

    private DiagnosisMapper diagnosisMapper;

    @BeforeEach
    public void setUp() {
        diagnosisMapper = new DiagnosisMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(diagnosisMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(diagnosisMapper.fromId(null)).isNull();
    }
}
