package ru.ciadmin.repository;

import ru.ciadmin.domain.CIRule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CIRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CIRuleRepository extends JpaRepository<CIRule, Long> {

}
