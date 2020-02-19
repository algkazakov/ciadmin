package ru.ciadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.ciadmin.web.rest.TestUtil;

public class CIBuildDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIBuildDTO.class);
        CIBuildDTO cIBuildDTO1 = new CIBuildDTO();
        cIBuildDTO1.setId(1L);
        CIBuildDTO cIBuildDTO2 = new CIBuildDTO();
        assertThat(cIBuildDTO1).isNotEqualTo(cIBuildDTO2);
        cIBuildDTO2.setId(cIBuildDTO1.getId());
        assertThat(cIBuildDTO1).isEqualTo(cIBuildDTO2);
        cIBuildDTO2.setId(2L);
        assertThat(cIBuildDTO1).isNotEqualTo(cIBuildDTO2);
        cIBuildDTO1.setId(null);
        assertThat(cIBuildDTO1).isNotEqualTo(cIBuildDTO2);
    }
}
