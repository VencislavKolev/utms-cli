package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import models.jsonExport.ReportDto;
import models.jsonExport.SuiteDto;
import models.jsonExport.TestDto;
import models.yamlImport.ImportProjectDto;
import models.yamlImport.ImportSuiteTestDto;
import models.yamlImport.YamlDto;

import java.io.IOException;
import java.util.List;

public interface JsonReportService {

    void generateJson(YamlDto yamlDto) throws JsonProcessingException;

    void generateJson(String filePath, String runId) throws IOException;

    void printJsonString(Object obj) throws JsonProcessingException;

    Object processInput(String... args) throws IOException;

    ReportDto generateReport(YamlDto yamlDto, Long runId);

    ImportProjectDto getProject(YamlDto yamlDto);

    List<SuiteDto> provideSuites(YamlDto yamlDto);

    List<TestDto> provideTests(ImportSuiteTestDto[] suiteTests, SuiteDto suiteDto);
}
