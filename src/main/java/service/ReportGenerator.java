package service;

import models.jsonExport.ReportDto;
import models.yamlImport.YamlDto;

import java.util.Map;

public interface ReportGenerator {
    ReportDto getReport(YamlDto yamlDto, String runId, Map<String, String> commands);
}
