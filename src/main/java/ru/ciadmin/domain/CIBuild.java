package ru.ciadmin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A CIBuild.
 */
@Entity
@Table(name = "ci_build")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CIBuild implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "module_version")
    private String moduleVersion;

    @Column(name = "branch")
    private String branch;

    @Column(name = "ci_result")
    private String ciResult;

    @Column(name = "sonar_result")
    private String sonarResult;

    @Column(name = "build_date")
    private ZonedDateTime buildDate;

    @Column(name = "sonar_project")
    private String sonarProject;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public CIBuild moduleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public CIBuild moduleVersion(String moduleVersion) {
        this.moduleVersion = moduleVersion;
        return this;
    }

    public void setModuleVersion(String moduleVersion) {
        this.moduleVersion = moduleVersion;
    }

    public String getBranch() {
        return branch;
    }

    public CIBuild branch(String branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCiResult() {
        return ciResult;
    }

    public CIBuild ciResult(String ciResult) {
        this.ciResult = ciResult;
        return this;
    }

    public void setCiResult(String ciResult) {
        this.ciResult = ciResult;
    }

    public String getSonarResult() {
        return sonarResult;
    }

    public CIBuild sonarResult(String sonarResult) {
        this.sonarResult = sonarResult;
        return this;
    }

    public void setSonarResult(String sonarResult) {
        this.sonarResult = sonarResult;
    }

    public ZonedDateTime getBuildDate() {
        return buildDate;
    }

    public CIBuild buildDate(ZonedDateTime buildDate) {
        this.buildDate = buildDate;
        return this;
    }

    public void setBuildDate(ZonedDateTime buildDate) {
        this.buildDate = buildDate;
    }

    public String getSonarProject() {
        return sonarProject;
    }

    public CIBuild sonarProject(String sonarProject) {
        this.sonarProject = sonarProject;
        return this;
    }

    public void setSonarProject(String sonarProject) {
        this.sonarProject = sonarProject;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CIBuild)) {
            return false;
        }
        return id != null && id.equals(((CIBuild) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CIBuild{" +
            "id=" + getId() +
            ", moduleName='" + getModuleName() + "'" +
            ", moduleVersion='" + getModuleVersion() + "'" +
            ", branch='" + getBranch() + "'" +
            ", ciResult='" + getCiResult() + "'" +
            ", sonarResult='" + getSonarResult() + "'" +
            ", buildDate='" + getBuildDate() + "'" +
            ", sonarProject='" + getSonarProject() + "'" +
            "}";
    }
}
