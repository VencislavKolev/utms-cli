package models.jsonExport;

import models.enums.Status;
import models.yamlImport.ImportProjectDto;

import java.util.List;

public class ReportDto {

    private Long runId;
    private ImportProjectDto project;
    private Status status;
    private List<SuiteDto> suites;

    public ReportDto() {
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public ImportProjectDto getProject() {
        return project;
    }

    public void setProject(ImportProjectDto project) {
        this.project = project;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<SuiteDto> getSuites() {
        return suites;
    }

    public void setSuites(List<SuiteDto> suites) {
        this.suites = suites;
    }
}

