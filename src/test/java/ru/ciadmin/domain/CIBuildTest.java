package ru.ciadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.ciadmin.web.rest.TestUtil;

public class CIBuildTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CIBuild.class);
        CIBuild cIBuild1 = new CIBuild();
        cIBuild1.setId(1L);
        CIBuild cIBuild2 = new CIBuild();
        cIBuild2.setId(cIBuild1.getId());
        assertThat(cIBuild1).isEqualTo(cIBuild2);
        cIBuild2.setId(2L);
        assertThat(cIBuild1).isNotEqualTo(cIBuild2);
        cIBuild1.setId(null);
        assertThat(cIBuild1).isNotEqualTo(cIBuild2);
    }
}
