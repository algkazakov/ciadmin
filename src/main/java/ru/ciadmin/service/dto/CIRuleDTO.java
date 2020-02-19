package ru.ciadmin.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ru.ciadmin.domain.CIRule} entity.
 */
public class CIRuleDTO implements Serializable {

    private Long id;

    private String ruleName;

    private Boolean critical;

    private Boolean useInReport;

    private Boolean useInMicroservice;

    private Instant modifyDate;


    private Long cIRuleGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Boolean isCritical() {
        return critical;
    }

    public void setCritical(Boolean critical) {
        this.critical = critical;
    }

    public Boolean isUseInReport() {
        return useInReport;
    }

    public void setUseInReport(Boolean useInReport) {
        this.useInReport = useInReport;
    }

    public Boolean isUseInMicroservice() {
        return useInMicroservice;
    }

    public void setUseInMicroservice(Boolean useInMicroservice) {
        this.useInMicroservice = useInMicroservice;
    }

    public Instant getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Instant modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getCIRuleGroupId() {
        return cIRuleGroupId;
    }

    public void setCIRuleGroupId(Long cIRuleGroupId) {
        this.cIRuleGroupId = cIRuleGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CIRuleDTO cIRuleDTO = (CIRuleDTO) o;
        if (cIRuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cIRuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CIRuleDTO{" +
            "id=" + getId() +
            ", ruleName='" + getRuleName() + "'" +
            ", critical='" + isCritical() + "'" +
            ", useInReport='" + isUseInReport() + "'" +
            ", useInMicroservice='" + isUseInMicroservice() + "'" +
            ", modifyDate='" + getModifyDate() + "'" +
            ", cIRuleGroupId=" + getCIRuleGroupId() +
            "}";
    }
}
