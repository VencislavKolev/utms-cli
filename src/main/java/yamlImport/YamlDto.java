package yamlImport;

import java.util.List;

public class YamlDto {
    private Project project;
    private List<Suite> suites;

    public YamlDto() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project value) {
        this.project = value;
    }

    public List<Suite> getSuites() {
        return suites;
    }

    public void setSuites(List<Suite> suites) {
        this.suites = suites;
    }
}
