package ru.ciadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CIRuleMapperTest {

    private CIRuleMapper cIRuleMapper;

    @BeforeEach
    public void setUp() {
        cIRuleMapper = new CIRuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cIRuleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cIRuleMapper.fromId(null)).isNull();
    }
}
