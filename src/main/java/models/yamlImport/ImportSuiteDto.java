package models.yamlImport;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class ImportSuiteDto {
    private final Map<String, ImportSuiteTestDto[]> map;

    public ImportSuiteDto() {
        this.map = new HashMap<>();
    }

    @JsonAnyGetter
    public Map<String, ImportSuiteTestDto[]> getMap() {
        return map;
    }

    @JsonAnySetter
    private void setBackEndTestMap(String key, ImportSuiteTestDto[] value) {
        this.map.put(key, value);
    }
}