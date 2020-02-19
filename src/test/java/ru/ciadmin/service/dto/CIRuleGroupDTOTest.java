package ru.ciadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.ciadmin.web.rest.TestUtil;

public class CIRuleGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIRuleGroupDTO.class);
        CIRuleGroupDTO cIRuleGroupDTO1 = new CIRuleGroupDTO();
        cIRuleGroupDTO1.setId(1L);
        CIRuleGroupDTO cIRuleGroupDTO2 = new CIRuleGroupDTO();
        assertThat(cIRuleGroupDTO1).isNotEqualTo(cIRuleGroupDTO2);
        cIRuleGroupDTO2.setId(cIRuleGroupDTO1.getId());
        assertThat(cIRuleGroupDTO1).isEqualTo(cIRuleGroupDTO2);
        cIRuleGroupDTO2.setId(2L);
        assertThat(cIRuleGroupDTO1).isNotEqualTo(cIRuleGroupDTO2);
        cIRuleGroupDTO1.setId(null);
        assertThat(cIRuleGroupDTO1).isNotEqualTo(cIRuleGroupDTO2);
    }
}
