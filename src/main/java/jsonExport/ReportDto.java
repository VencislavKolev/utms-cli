package jsonExport;

import entity.Status;
import yamlImport.Project;

import java.util.List;

public class ReportDto {
    private Long runId;
    private Project project;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
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
