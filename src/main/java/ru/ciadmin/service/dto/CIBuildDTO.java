package ru.ciadmin.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ru.ciadmin.domain.CIBuild} entity.
 */
public class CIBuildDTO implements Serializable {

    private Long id;

    private String moduleName;

    private String moduleVersion;

    private String branch;

    private String ciResult;

    private String sonarResult;

    private ZonedDateTime buildDate;

    private String sonarProject;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public void setModuleVersion(String moduleVersion) {
        this.moduleVersion = moduleVersion;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCiResult() {
        return ciResult;
    }

    public void setCiResult(String ciResult) {
        this.ciResult = ciResult;
    }

    public String getSonarResult() {
        return sonarResult;
    }

    public void setSonarResult(String sonarResult) {
        this.sonarResult = sonarResult;
    }

    public ZonedDateTime getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(ZonedDateTime buildDate) {
        this.buildDate = buildDate;
    }

    public String getSonarProject() {
        return sonarProject;
    }

    public void setSonarProject(String sonarProject) {
        this.sonarProject = sonarProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CIBuildDTO cIBuildDTO = (CIBuildDTO) o;
        if (cIBuildDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cIBuildDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CIBuildDTO{" +
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
