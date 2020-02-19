package ru.ciadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CIRule.
 */
@Entity
@Table(name = "cireportrules")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CIRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "cireportrulesid")
    private Long id;

    @Column(name = "rulename")
    private String ruleName;

    @Column(name = "critical")
    private Boolean critical;

    @Column(name = "useinreport")
    private Boolean useInReport;

    @Column(name = "useinmicro")
    private Boolean useInMicroservice;

    @Column(name = "modify_date")
    private Instant modifyDate;

    @ManyToOne
    @JsonIgnoreProperties("contains")
    @JoinColumn(name="cireportgrouprulesid", nullable=false)
    private CIRuleGroup cIRuleGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public CIRule ruleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Boolean isCritical() {
        return critical;
    }

    public CIRule critical(Boolean critical) {
        this.critical = critical;
        return this;
    }

    public void setCritical(Boolean critical) {
        this.critical = critical;
    }

    public Boolean isUseInReport() {
        return useInReport;
    }

    public CIRule useInReport(Boolean useInReport) {
        this.useInReport = useInReport;
        return this;
    }

    public void setUseInReport(Boolean useInReport) {
        this.useInReport = useInReport;
    }

    public Boolean isUseInMicroservice() {
        return useInMicroservice;
    }

    public CIRule useInMicroservice(Boolean useInMicroservice) {
        this.useInMicroservice = useInMicroservice;
        return this;
    }

    public void setUseInMicroservice(Boolean useInMicroservice) {
        this.useInMicroservice = useInMicroservice;
    }

    public Instant getModifyDate() {
        return modifyDate;
    }

    public CIRule modifyDate(Instant modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }

    public void setModifyDate(Instant modifyDate) {
        this.modifyDate = modifyDate;
    }

    public CIRuleGroup getCIRuleGroup() {
        return cIRuleGroup;
    }

    public CIRule cIRuleGroup(CIRuleGroup cIRuleGroup) {
        this.cIRuleGroup = cIRuleGroup;
        return this;
    }

    public void setCIRuleGroup(CIRuleGroup cIRuleGroup) {
        this.cIRuleGroup = cIRuleGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CIRule)) {
            return false;
        }
        return id != null && id.equals(((CIRule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CIRule{" +
            "id=" + getId() +
            ", ruleName='" + getRuleName() + "'" +
            ", critical='" + isCritical() + "'" +
            ", useInReport='" + isUseInReport() + "'" +
            ", useInMicroservice='" + isUseInMicroservice() + "'" +
            ", modifyDate='" + getModifyDate() + "'" +
            "}";
    }
}
