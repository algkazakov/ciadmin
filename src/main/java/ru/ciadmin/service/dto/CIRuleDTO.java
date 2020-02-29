package ru.ciadmin.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ru.ciadmin.domain.CIRule} entity.
 */
public class CIRuleDTO implements Serializable {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("ruleName")
    private String ruleName;

    public CIRuleDTO() {}
    @JsonProperty("critical")
    private Boolean critical;
    @JsonProperty("useInReport")
    private Boolean useInReport;
    @JsonProperty("useInMicroservice")
    private Boolean useInMicroservice;
    @JsonProperty("modifyDate")
    private Instant modifyDate;

    @JsonProperty("cIRuleGroupId")
    private Long cIRuleGroupId;

    @JsonCreator
    public CIRuleDTO(@JsonProperty("id") Long id,
                     @JsonProperty("ruleName") String ruleName,
                     @JsonProperty("critical") Boolean critical,
                     @JsonProperty("useInReport") Boolean useInReport,
                     @JsonProperty("useInMicroservice") Boolean useInMicroservice,
                     @JsonProperty("modifyDate") Instant modifyDate,
                     @JsonProperty("cIRuleGroupId") Long cIRuleGroupId) {
        this.id = id;
        this.ruleName = ruleName;
        this.critical = critical;
        this.useInReport = useInReport;
        this.useInMicroservice = useInMicroservice;
        this.modifyDate = modifyDate;
        this.cIRuleGroupId = cIRuleGroupId;
    }

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
