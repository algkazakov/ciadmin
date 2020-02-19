package ru.ciadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.ciadmin.web.rest.TestUtil;

public class CIRuleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIRuleDTO.class);
        CIRuleDTO cIRuleDTO1 = new CIRuleDTO();
        cIRuleDTO1.setId(1L);
        CIRuleDTO cIRuleDTO2 = new CIRuleDTO();
        assertThat(cIRuleDTO1).isNotEqualTo(cIRuleDTO2);
        cIRuleDTO2.setId(cIRuleDTO1.getId());
        assertThat(cIRuleDTO1).isEqualTo(cIRuleDTO2);
        cIRuleDTO2.setId(2L);
        assertThat(cIRuleDTO1).isNotEqualTo(cIRuleDTO2);
        cIRuleDTO1.setId(null);
        assertThat(cIRuleDTO1).isNotEqualTo(cIRuleDTO2);
    }
}
