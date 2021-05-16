package service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.jsonExport.ReportDto;
import models.yamlImport.YamlDto;
import service.JsonService;
import service.ReportGenerator;
import util.JacksonMapper;
import util.YamlUtil;

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

        //----------------------------------RUN_ID-REMOVED------------------------------------
//        String inputRunId = null;
//        if (cmdMap.containsKey(RUN_CMD)) {
//            inputRunId = cmdMap.remove(RUN_CMD);
//            boolean isNumber = this.isValidNumber(inputRunId);
//            if (!isNumber) {
//                return new ReportDto(INVALID_RUN_ID);
//            }
//        }

        //----------------------------------CHECK IF FILE EXISTS------------------------------------
        String file = DEFAULT_FILE;

        if (cmdMap.containsKey(CONFIG_CMD)) {
            file = cmdMap.remove(CONFIG_CMD);
        }

        if (getInputStream(file) == null) {
            return new ReportDto(NOT_FOUND_CONFIG);
        }

        if (!this.yamlUtil.checkYamlCompatibility(getInputStream(file), YamlDto.class)) {
            return new ReportDto(INVALID_CONFIG_FILE);
        }
        YamlDto yamlDto = this.yamlUtil.getYamlDtoFromYamlFile(getInputStream(file));

        ReportDto reportDto = this.reportGenerator.getReport(yamlDto, cmdMap);
        return reportDto;
    }

    @Override
    public void printJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = JacksonMapper.getMapper();
        String outputJson = mapper.writeValueAsString(obj);
        System.out.println(outputJson);
    }

    public String getJsonString(Object obj) throws JsonProcessingException {
        return JacksonMapper.getMapper().writeValueAsString(obj);
    }

    private InputStream getInputStream(String arg) {
        return this.getClass().getClassLoader().getResourceAsStream(arg);
    }

    private boolean isValidNumber(String str) {
        // null pointer
        if (str == null) {
            return false;
        }
        int len = str.length();
        // empty string
        if (len == 0) {
            return false;
        }
        // one digit, cannot begin with 0
        if (len == 1) {
            char c = str.charAt(0);
            if ((c < '1') || (c > '9')) {
                return false;
            }
        }

        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            // check positive, negative sign
            if (i == 0) {
                // only positive sign accepted
                if (c == '+' || Character.isDigit(c)) {
                    continue;
                }
                return false;
            }
            // check each character matches [0-9]
            if ((c < '0') || (c > '9')) {
                return false;
            }
        }
        return true;
    }
}
