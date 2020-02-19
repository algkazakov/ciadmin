package ru.ciadmin.service.mapper;


import ru.ciadmin.domain.*;
import ru.ciadmin.service.dto.CIRuleGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CIRuleGroup} and its DTO {@link CIRuleGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CIRuleGroupMapper extends EntityMapper<CIRuleGroupDTO, CIRuleGroup> {


    @Mapping(target = "contains", ignore = true)
    @Mapping(target = "removeContains", ignore = true)
    CIRuleGroup toEntity(CIRuleGroupDTO cIRuleGroupDTO);

    default CIRuleGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        CIRuleGroup cIRuleGroup = new CIRuleGroup();
        cIRuleGroup.setId(id);
        return cIRuleGroup;
    }
}
