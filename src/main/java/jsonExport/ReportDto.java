package jsonExport;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.Status;
import yamlImport.ImportProjectDto;

import java.util.List;

public class ReportDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

