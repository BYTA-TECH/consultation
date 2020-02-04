package com.bytatech.ayoos.consultation.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytatech.ayoos.consultation.web.rest.TestUtil;

public class DiagnosisDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiagnosisDTO.class);
        DiagnosisDTO diagnosisDTO1 = new DiagnosisDTO();
        diagnosisDTO1.setId(1L);
        DiagnosisDTO diagnosisDTO2 = new DiagnosisDTO();
        assertThat(diagnosisDTO1).isNotEqualTo(diagnosisDTO2);
        diagnosisDTO2.setId(diagnosisDTO1.getId());
        assertThat(diagnosisDTO1).isEqualTo(diagnosisDTO2);
        diagnosisDTO2.setId(2L);
        assertThat(diagnosisDTO1).isNotEqualTo(diagnosisDTO2);
        diagnosisDTO1.setId(null);
        assertThat(diagnosisDTO1).isNotEqualTo(diagnosisDTO2);
    }
}
