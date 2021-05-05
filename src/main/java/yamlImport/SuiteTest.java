package yamlImport;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class SuiteTest {
    private final Map<String, TestDetail> tests;

    public SuiteTest() {
        this.tests = new HashMap<>();
    }

    @JsonAnyGetter
    public Map<String, TestDetail> getTests() {
        return tests;
    }

    @JsonAnySetter
    public void setTest(String name, TestDetail values) {
        this.tests.put(name, values);
    }
}
