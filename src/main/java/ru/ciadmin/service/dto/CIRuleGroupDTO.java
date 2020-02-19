package ru.ciadmin.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ru.ciadmin.domain.CIRuleGroup} entity.
 */
public class CIRuleGroupDTO implements Serializable {

    private Long id;

    private String ruleGroupName;

    private Boolean critical;

    private String type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleGroupName() {
        return ruleGroupName;
    }

    public void setRuleGroupName(String ruleGroupName) {
        this.ruleGroupName = ruleGroupName;
    }

    public Boolean isCritical() {
        return critical;
    }

    public void setCritical(Boolean critical) {
        this.critical = critical;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CIRuleGroupDTO cIRuleGroupDTO = (CIRuleGroupDTO) o;
        if (cIRuleGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cIRuleGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CIRuleGroupDTO{" +
            "id=" + getId() +
            ", ruleGroupName='" + getRuleGroupName() + "'" +
            ", critical='" + isCritical() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
