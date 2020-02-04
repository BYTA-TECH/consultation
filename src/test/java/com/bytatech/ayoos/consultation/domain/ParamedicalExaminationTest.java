package com.bytatech.ayoos.consultation.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytatech.ayoos.consultation.web.rest.TestUtil;

public class ParamedicalExaminationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamedicalExamination.class);
        ParamedicalExamination paramedicalExamination1 = new ParamedicalExamination();
        paramedicalExamination1.setId(1L);
        ParamedicalExamination paramedicalExamination2 = new ParamedicalExamination();
        paramedicalExamination2.setId(paramedicalExamination1.getId());
        assertThat(paramedicalExamination1).isEqualTo(paramedicalExamination2);
        paramedicalExamination2.setId(2L);
        assertThat(paramedicalExamination1).isNotEqualTo(paramedicalExamination2);
        paramedicalExamination1.setId(null);
        assertThat(paramedicalExamination1).isNotEqualTo(paramedicalExamination2);
    }
}
