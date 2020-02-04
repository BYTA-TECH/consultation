package com.bytatech.ayoos.consultation.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytatech.ayoos.consultation.web.rest.TestUtil;

public class SymptomDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SymptomDTO.class);
        SymptomDTO symptomDTO1 = new SymptomDTO();
        symptomDTO1.setId(1L);
        SymptomDTO symptomDTO2 = new SymptomDTO();
        assertThat(symptomDTO1).isNotEqualTo(symptomDTO2);
        symptomDTO2.setId(symptomDTO1.getId());
        assertThat(symptomDTO1).isEqualTo(symptomDTO2);
        symptomDTO2.setId(2L);
        assertThat(symptomDTO1).isNotEqualTo(symptomDTO2);
        symptomDTO1.setId(null);
        assertThat(symptomDTO1).isNotEqualTo(symptomDTO2);
    }
}
