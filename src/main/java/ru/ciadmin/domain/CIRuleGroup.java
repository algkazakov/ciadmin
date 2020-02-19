package ru.ciadmin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CIRuleGroup.
 */
@Entity
@Table(name = "cireportgrouprules")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CIRuleGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "cireportgrouprules")
    private Long id;

    @Column(name = "groupname")
    private String ruleGroupName;

    @Column(name = "critical")
    private Boolean critical;

    @Column(name = "typecolumn")
    private String type;

    @OneToMany(mappedBy = "cIRuleGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CIRule> contains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleGroupName() {
        return ruleGroupName;
    }

    public CIRuleGroup ruleGroupName(String ruleGroupName) {
        this.ruleGroupName = ruleGroupName;
        return this;
    }

    public void setRuleGroupName(String ruleGroupName) {
        this.ruleGroupName = ruleGroupName;
    }

    public Boolean isCritical() {
        return critical;
    }

    public CIRuleGroup critical(Boolean critical) {
        this.critical = critical;
        return this;
    }

    public void setCritical(Boolean critical) {
        this.critical = critical;
    }

    public String getType() {
        return type;
    }

    public CIRuleGroup type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<CIRule> getContains() {
        return contains;
    }

    public CIRuleGroup contains(Set<CIRule> cIRules) {
        this.contains = cIRules;
        return this;
    }

    public CIRuleGroup addContains(CIRule cIRule) {
        this.contains.add(cIRule);
        cIRule.setCIRuleGroup(this);
        return this;
    }

    public CIRuleGroup removeContains(CIRule cIRule) {
        this.contains.remove(cIRule);
        cIRule.setCIRuleGroup(null);
        return this;
    }

    public void setContains(Set<CIRule> cIRules) {
        this.contains = cIRules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CIRuleGroup)) {
            return false;
        }
        return id != null && id.equals(((CIRuleGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CIRuleGroup{" +
            "id=" + getId() +
            ", ruleGroupName='" + getRuleGroupName() + "'" +
            ", critical='" + isCritical() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
