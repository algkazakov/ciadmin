package ru.ciadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.ciadmin.web.rest.TestUtil;

public class CIRuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIRule.class);
        CIRule cIRule1 = new CIRule();
        cIRule1.setId(1L);
        CIRule cIRule2 = new CIRule();
        cIRule2.setId(cIRule1.getId());
        assertThat(cIRule1).isEqualTo(cIRule2);
        cIRule2.setId(2L);
        assertThat(cIRule1).isNotEqualTo(cIRule2);
        cIRule1.setId(null);
        assertThat(cIRule1).isNotEqualTo(cIRule2);
    }
}
