package ru.ciadmin.repository;

import ru.ciadmin.domain.CIRuleGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CIRuleGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CIRuleGroupRepository extends JpaRepository<CIRuleGroup, Long> {

}
