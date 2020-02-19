package ru.ciadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CIBuildMapperTest {

    private CIBuildMapper cIBuildMapper;

    @BeforeEach
    public void setUp() {
        cIBuildMapper = new CIBuildMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cIBuildMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cIBuildMapper.fromId(null)).isNull();
    }
}
