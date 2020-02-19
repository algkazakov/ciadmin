package ru.ciadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CIRuleGroupMapperTest {

    private CIRuleGroupMapper cIRuleGroupMapper;

    @BeforeEach
    public void setUp() {
        cIRuleGroupMapper = new CIRuleGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cIRuleGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cIRuleGroupMapper.fromId(null)).isNull();
    }
}
