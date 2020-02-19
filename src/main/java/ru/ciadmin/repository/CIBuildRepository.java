package ru.ciadmin.repository;

import ru.ciadmin.domain.CIBuild;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CIBuild entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CIBuildRepository extends JpaRepository<CIBuild, Long> {

}
