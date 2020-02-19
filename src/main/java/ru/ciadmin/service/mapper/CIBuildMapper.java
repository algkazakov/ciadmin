package ru.ciadmin.service.mapper;


import ru.ciadmin.domain.*;
import ru.ciadmin.service.dto.CIBuildDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CIBuild} and its DTO {@link CIBuildDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CIBuildMapper extends EntityMapper<CIBuildDTO, CIBuild> {



    default CIBuild fromId(Long id) {
        if (id == null) {
            return null;
        }
        CIBuild cIBuild = new CIBuild();
        cIBuild.setId(id);
        return cIBuild;
    }
}
