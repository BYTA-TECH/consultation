package com.bytatech.ayoos.consultation.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytatech.ayoos.consultation.web.rest.TestUtil;

public class PrescriptionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrescriptionDTO.class);
        PrescriptionDTO prescriptionDTO1 = new PrescriptionDTO();
        prescriptionDTO1.setId(1L);
        PrescriptionDTO prescriptionDTO2 = new PrescriptionDTO();
        assertThat(prescriptionDTO1).isNotEqualTo(prescriptionDTO2);
        prescriptionDTO2.setId(prescriptionDTO1.getId());
        assertThat(prescriptionDTO1).isEqualTo(prescriptionDTO2);
        prescriptionDTO2.setId(2L);
        assertThat(prescriptionDTO1).isNotEqualTo(prescriptionDTO2);
        prescriptionDTO1.setId(null);
        assertThat(prescriptionDTO1).isNotEqualTo(prescriptionDTO2);
    }
}
