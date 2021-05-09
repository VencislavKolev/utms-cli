package models.jsonExport;

import com.fasterxml.jackson.annotation.JsonInclude;
import models.enums.Status;
import models.yamlImport.ImportProjectDto;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDto extends BaseDto {
    private Long runId;
    private ImportProjectDto project;
    private Status status;
    private List<SuiteDto> suites;

    public ReportDto() {
    }

    public ReportDto(Long runId, ImportProjectDto project, Status status, List<SuiteDto> suites) {
        this.runId = runId;
        this.project = project;
        this.status = status;
        this.suites = suites;
    }

    public ReportDto(String error) {
        super(error);
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

