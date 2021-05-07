package models.yamlImport;

import java.util.List;

public class YamlDto {
    private ImportProjectDto project;
    private List<ImportSuiteDto> suites;

    public YamlDto() {
    }

    public ImportProjectDto getProject() {
        return project;
    }

    public void setProject(ImportProjectDto value) {
        this.project = value;
    }

    public List<ImportSuiteDto> getSuites() {
        return suites;
    }

    public void setSuites(List<ImportSuiteDto> suites) {
        this.suites = suites;
    }
}
