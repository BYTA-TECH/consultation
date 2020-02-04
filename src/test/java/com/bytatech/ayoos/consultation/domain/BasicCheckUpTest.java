package com.bytatech.ayoos.consultation.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bytatech.ayoos.consultation.web.rest.TestUtil;

public class BasicCheckUpTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BasicCheckUp.class);
        BasicCheckUp basicCheckUp1 = new BasicCheckUp();
        basicCheckUp1.setId(1L);
        BasicCheckUp basicCheckUp2 = new BasicCheckUp();
        basicCheckUp2.setId(basicCheckUp1.getId());
        assertThat(basicCheckUp1).isEqualTo(basicCheckUp2);
        basicCheckUp2.setId(2L);
        assertThat(basicCheckUp1).isNotEqualTo(basicCheckUp2);
        basicCheckUp1.setId(null);
        assertThat(basicCheckUp1).isNotEqualTo(basicCheckUp2);
    }
}
