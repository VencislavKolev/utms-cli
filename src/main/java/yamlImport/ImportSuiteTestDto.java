package yamlImport;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class ImportSuiteTestDto {
    private final Map<String, ImportTestDetailDto> tests;

    public ImportSuiteTestDto() {
        this.tests = new HashMap<>();
    }

    @JsonAnyGetter
    public Map<String, ImportTestDetailDto> getTests() {
        return tests;
    }

    @JsonAnySetter
    public void setTest(String name, ImportTestDetailDto values) {
        this.tests.put(name, values);
    }
}
