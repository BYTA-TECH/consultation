package com.bytatech.ayoos.consultation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ParamedicalExaminationMapperTest {

    private ParamedicalExaminationMapper paramedicalExaminationMapper;

    @BeforeEach
    public void setUp() {
        paramedicalExaminationMapper = new ParamedicalExaminationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(paramedicalExaminationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paramedicalExaminationMapper.fromId(null)).isNull();
    }
}
