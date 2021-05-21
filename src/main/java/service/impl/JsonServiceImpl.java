package service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import models.jsonExport.ReportDto;
import models.yamlImport.YamlDto;
import service.JsonService;
import service.ReportGenerator;
import util.JacksonMapper;
import util.YamlUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static common.GlobalConstants.*;

public class JsonServiceImpl implements JsonService {
    private final YamlUtil yamlUtil;
    private final ReportGenerator reportGenerator;

    public JsonServiceImpl(YamlUtil yamlUtil, ReportGenerator reportGenerator) {
        this.yamlUtil = yamlUtil;
        this.reportGenerator = reportGenerator;
    }

    @Override
    public ReportDto processInput(Map<String, String> cmdMap) throws IOException {
        //----------------------------------CHECK IF FILE EXISTS------------------------------------
        String filePath = DEFAULT_FILE;

        if (cmdMap.containsKey(CONFIG_CMD)) {
            filePath = cmdMap.remove(CONFIG_CMD);
        }
//-----------------------------------------------------------------------------------------
//        if (getInputStream(filePath) == null) {
//            return new ReportDto(NOT_FOUND_CONFIG);
//        }

//        if (!this.yamlUtil.checkYamlCompatibility(getInputStream(filePath), YamlDto.class)) {
//            return new ReportDto(INVALID_CONFIG_FILE);
//        }
//        YamlDto yamlDto = this.yamlUtil.getYamlDtoFromYamlFile(getInputStream(filePath));
//-----------------------------------------------------------------------------------------
        File file = new File(filePath);
        System.out.println(file.getAbsolutePath());
        if (!file.isFile()) {
            return new ReportDto(NOT_FOUND_CONFIG);
        }

        if (!this.yamlUtil.checkYamlCompatibility(file, YamlDto.class)) {
            return new ReportDto(INVALID_CONFIG_FILE);
        }
        YamlDto yamlDto = this.yamlUtil.getYamlDtoFromYamlFile(file);
//-----------------------------------------------------------------------------------------
        ReportDto reportDto = this.reportGenerator.getReport(yamlDto, cmdMap);
        return reportDto;
    }

    @Override
    public String getJsonString(Object obj) throws JsonProcessingException {
        return JacksonMapper.getMapper().writeValueAsString(obj);
    }

    @Override
    public void printJsonString(Object obj) throws JsonProcessingException {
        String outputJson = this.getJsonString(obj);
        System.out.println(outputJson);
    }

    private InputStream getInputStream(String arg) {
        return this.getClass().getClassLoader().getResourceAsStream(arg);
    }
}
