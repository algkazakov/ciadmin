package ru.ciadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.ciadmin.web.rest.TestUtil;

public class CIRuleGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIRuleGroup.class);
        CIRuleGroup cIRuleGroup1 = new CIRuleGroup();
        cIRuleGroup1.setId(1L);
        CIRuleGroup cIRuleGroup2 = new CIRuleGroup();
        cIRuleGroup2.setId(cIRuleGroup1.getId());
        assertThat(cIRuleGroup1).isEqualTo(cIRuleGroup2);
        cIRuleGroup2.setId(2L);
        assertThat(cIRuleGroup1).isNotEqualTo(cIRuleGroup2);
        cIRuleGroup1.setId(null);
        assertThat(cIRuleGroup1).isNotEqualTo(cIRuleGroup2);
    }
}
