package service;

import models.jsonExport.SuiteDto;
import models.yamlImport.YamlDto;

import java.util.List;
import java.util.Map;

public interface SuiteGenerator {
    List<SuiteDto> getSuites(YamlDto yamlDto, Map<String, String> commands);
}
