package com.bytatech.ayoos.consultation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ConsultationMapperTest {

    private ConsultationMapper consultationMapper;

    @BeforeEach
    public void setUp() {
        consultationMapper = new ConsultationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(consultationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(consultationMapper.fromId(null)).isNull();
    }
}
