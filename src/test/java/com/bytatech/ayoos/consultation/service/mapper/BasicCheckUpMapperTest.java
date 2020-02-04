package com.bytatech.ayoos.consultation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class BasicCheckUpMapperTest {

    private BasicCheckUpMapper basicCheckUpMapper;

    @BeforeEach
    public void setUp() {
        basicCheckUpMapper = new BasicCheckUpMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(basicCheckUpMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(basicCheckUpMapper.fromId(null)).isNull();
    }
}
