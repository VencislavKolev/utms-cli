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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static common.GlobalConstants.*;
import static common.GlobalConstants.INVALID_CONFIG_FILE;

public class JsonServiceImpl implements JsonService {
    private final static Set<String> VALID_COMMANDS = Set.of(
            "--config", "--run-id",
            "-c", "-r",
            "--suite-name", "--test-name",
            "-sn", "-tn"
    );

    private final YamlUtil yamlUtil;
    private final ReportGenerator reportGenerator;

    public JsonServiceImpl(YamlUtil yamlUtil, ReportGenerator reportGenerator) {
        this.yamlUtil = yamlUtil;
        this.reportGenerator = reportGenerator;
    }


    @Override
    public Object processInput(String... args) throws IOException {
        //--------------------------------TESTS--------------------------------------
        Map<String, String> cmdMap = this.getCommandsMap(args);

        //---------------------------------CHECK RUN ID-------------------------------------
        //var filePath = DIR_PREFIX + args[0];

        String inputRunId = null;
        if (cmdMap.containsKey(RUN_CMD)) {
            inputRunId = cmdMap.remove(RUN_CMD);
            boolean isNumber = this.isValidNumber(inputRunId);
            if (!isNumber) {
                return new ReportDto(INVALID_RUN_ID);
            }
        }
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

        ReportDto reportDto = this.reportGenerator.getReport(yamlDto, inputRunId, cmdMap);
        return reportDto;
    }

    @Override
    public void printJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = JacksonMapper.getMapper();
        String outputJson = mapper.writeValueAsString(obj);

        System.out.println(outputJson);
    }

    private Map<String, String> getCommandsMap(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length - 1; i += 2) {
            String cmd = args[i];
            String value = args[i + 1];
            String key = null;
            if (VALID_COMMANDS.contains(cmd)) {
                switch (cmd) {
                    case "-c":
                        key = CONFIG_CMD;
                        map.put(key, value);
                        break;
                    case "-r":
                        key = RUN_CMD;
                        map.put(key, value);
                        break;
                    case "-sn":
                        key = SUITE_CMD;
                        map.put(key, value);
                        break;
                    case "-tn":
                        key = TEST_CMD;
                        map.put(key, value);
                        break;
                    default:
                        map.put(cmd, value);
                }
                //map.put(key, value);
            }
        }
        return map;
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
