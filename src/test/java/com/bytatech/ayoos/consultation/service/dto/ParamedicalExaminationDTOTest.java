package com.bytatech.ayoos.consultation.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytatech.ayoos.consultation.web.rest.TestUtil;

public class ParamedicalExaminationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamedicalExaminationDTO.class);
        ParamedicalExaminationDTO paramedicalExaminationDTO1 = new ParamedicalExaminationDTO();
        paramedicalExaminationDTO1.setId(1L);
        ParamedicalExaminationDTO paramedicalExaminationDTO2 = new ParamedicalExaminationDTO();
        assertThat(paramedicalExaminationDTO1).isNotEqualTo(paramedicalExaminationDTO2);
        paramedicalExaminationDTO2.setId(paramedicalExaminationDTO1.getId());
        assertThat(paramedicalExaminationDTO1).isEqualTo(paramedicalExaminationDTO2);
        paramedicalExaminationDTO2.setId(2L);
        assertThat(paramedicalExaminationDTO1).isNotEqualTo(paramedicalExaminationDTO2);
        paramedicalExaminationDTO1.setId(null);
        assertThat(paramedicalExaminationDTO1).isNotEqualTo(paramedicalExaminationDTO2);
    }
}
