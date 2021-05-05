package yamlImport;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class Suite {
//
//    private SuiteTest[] backEndTests;
//    private SuiteTest[] uiTests;
    private Map<String, SuiteTest[]> map;
    //private Map<String, Map<String, TestDetail>> backEndTestMap;

    public Suite() {
        this.map = new HashMap<>();
    }

    @JsonAnyGetter
    public Map<String, SuiteTest[]> getMap() {
        return map;
    }

    @JsonAnySetter
    private void setBackEndTestMap(String key, SuiteTest[] value) {
        this.map.put(key, value);
    }

//    @JsonProperty("BackEndTests")
//    public SuiteTest[] getBackEndTests() {
//        return backEndTests;
//    }
//
//    public void setBackEndTests(SuiteTest[] value) {
//        this.backEndTests = value;
//    }
//
//    @JsonProperty("UITests")
//    public SuiteTest[] getUiTests() {
//        return uiTests;
//    }
//
//    public void setUiTests(SuiteTest[] uiTests) {
//        this.uiTests = uiTests;
//    }
}