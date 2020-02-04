package com.bytatech.ayoos.consultation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PrescriptionMapperTest {

    private PrescriptionMapper prescriptionMapper;

    @BeforeEach
    public void setUp() {
        prescriptionMapper = new PrescriptionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(prescriptionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(prescriptionMapper.fromId(null)).isNull();
    }
}
