package service;

import models.jsonExport.TestDto;
import models.yamlImport.ImportSuiteTestDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TestGenerator {
    List<TestDto> generateTests(ImportSuiteTestDto[] suiteTests, String currSuiteName, Map<String, String> commands) throws IOException, InterruptedException;
}
