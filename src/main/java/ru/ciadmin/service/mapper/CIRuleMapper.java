package ru.ciadmin.service.mapper;


import ru.ciadmin.domain.*;
import ru.ciadmin.service.dto.CIRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CIRule} and its DTO {@link CIRuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {CIRuleGroupMapper.class})
public interface CIRuleMapper extends EntityMapper<CIRuleDTO, CIRule> {

    @Mapping(source = "CIRuleGroup.id", target = "CIRuleGroupId")
    CIRuleDTO toDto(CIRule cIRule);

    @Mapping(source = "CIRuleGroupId", target = "cIRuleGroup")
    CIRule toEntity(CIRuleDTO cIRuleDTO);

    default CIRule fromId(Long id) {
        if (id == null) {
            return null;
        }
        CIRule cIRule = new CIRule();
        cIRule.setId(id);
        return cIRule;
    }
}
