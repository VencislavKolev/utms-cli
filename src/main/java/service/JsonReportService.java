package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jsonExport.ReportDto;
import jsonExport.SuiteDto;
import jsonExport.TestDto;
import yamlImport.Project;
import yamlImport.SuiteTest;
import yamlImport.YamlDto;

import java.util.List;

public interface JsonReportService {

    void generateJson(YamlDto yamlDto) throws JsonProcessingException;

    ReportDto generateReport(YamlDto yamlDto);

    Project getProject(YamlDto yamlDto);

    List<SuiteDto> provideSuites(YamlDto yamlDto);

    List<TestDto> provideTests(SuiteTest[] suiteTests, SuiteDto suiteDto);
}
