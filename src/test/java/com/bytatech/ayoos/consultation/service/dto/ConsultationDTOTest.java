package com.bytatech.ayoos.consultation.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytatech.ayoos.consultation.web.rest.TestUtil;

public class ConsultationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultationDTO.class);
        ConsultationDTO consultationDTO1 = new ConsultationDTO();
        consultationDTO1.setId(1L);
        ConsultationDTO consultationDTO2 = new ConsultationDTO();
        assertThat(consultationDTO1).isNotEqualTo(consultationDTO2);
        consultationDTO2.setId(consultationDTO1.getId());
        assertThat(consultationDTO1).isEqualTo(consultationDTO2);
        consultationDTO2.setId(2L);
        assertThat(consultationDTO1).isNotEqualTo(consultationDTO2);
        consultationDTO1.setId(null);
        assertThat(consultationDTO1).isNotEqualTo(consultationDTO2);
    }
}
