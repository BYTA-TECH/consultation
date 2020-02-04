package com.bytatech.ayoos.consultation.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytatech.ayoos.consultation.web.rest.TestUtil;

public class BasicCheckUpDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BasicCheckUpDTO.class);
        BasicCheckUpDTO basicCheckUpDTO1 = new BasicCheckUpDTO();
        basicCheckUpDTO1.setId(1L);
        BasicCheckUpDTO basicCheckUpDTO2 = new BasicCheckUpDTO();
        assertThat(basicCheckUpDTO1).isNotEqualTo(basicCheckUpDTO2);
        basicCheckUpDTO2.setId(basicCheckUpDTO1.getId());
        assertThat(basicCheckUpDTO1).isEqualTo(basicCheckUpDTO2);
        basicCheckUpDTO2.setId(2L);
        assertThat(basicCheckUpDTO1).isNotEqualTo(basicCheckUpDTO2);
        basicCheckUpDTO1.setId(null);
        assertThat(basicCheckUpDTO1).isNotEqualTo(basicCheckUpDTO2);
    }
}
