package service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.enums.Status;
import models.jsonExport.*;
import service.CommandExecutor;
import service.JsonReportService;
import util.*;
import models.yamlImport.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static common.GlobalConstants.*;

public class JsonReportServiceImpl implements JsonReportService {
    private final YamlUtil yamlUtil;
    private final CommandExecutor commandExecutor;

    private static final String DIR_PREFIX = "src/main/resources/";

    public JsonReportServiceImpl(YamlUtil yamlUtil, CommandExecutor commandExecutor) {
        this.yamlUtil = yamlUtil;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public Object processInput(String... args) throws IOException {
        //--------------------------------TESTS--------------------------------------

        //---------------------------------CHECK RUN ID-------------------------------------
        //var filePath = DIR_PREFIX + args[0];
        var inputRunId = args[1];
        boolean isNumber = this.isValidNumber(inputRunId);
        if (!isNumber) {
            return new ErrorDto(INVALD_RUN_ID);
        }
        //----------------------------------CHECK IF FILE EXISTS------------------------------------

        if (getInputStream(args[0]) == null) {
            return new ErrorDto(NOT_FOUND_CONFIG);
        }
//      //-----------------------------------CHECK IF FILE IS VALID-----------------------------------
        if (!this.yamlUtil.checkYamlCompatibility(getInputStream(args[0]), YamlDto.class)) {
            return new ErrorDto(INVALID_CONFIG_FILE);
        }
        //------------------------------------PARSE FILE TO DTO----------------------------------
        YamlDto yamlDto = this.yamlUtil.getYamlDtoFromYamlFile(getInputStream(args[0]));

        //-------------------------------------GENERATE REPORT OBJECT---------------------------------
        Long runId = Long.parseLong(inputRunId);
        ReportDto reportDto = this.generateReport(yamlDto, runId);
        return reportDto;
    }

    @Override
    public void printJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = JacksonMapper.getMapper();
        String outputJson = mapper.writeValueAsString(obj);

        System.out.println(outputJson);
    }

    @Override
    public void generateJson(YamlDto yamlDto) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // ReportDto reportDto = this.generateReport(yamlDto);
        //String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reportDto);

        //  this.printJsonString(reportDto);
        //  System.out.println(json);
    }

    @Override
    public void generateJson(String filePath, String inputRunId) throws IOException {

        //TODO DELETE METHOD
        //----------------------------------------------------------------------

        boolean isNumber = this.isValidNumber(inputRunId);
        if (!isNumber) {
            this.printJsonString(new ErrorDto("Run Id is not valid."));
        }
        Long runId = Long.parseLong(inputRunId);
        //----------------------------------------------------------------------

//        if (!this.fileUtil.checkIfExists(filePath)) {
//            this.printJsonString(new ErrorDto("Configuration file not found."));
//        }
        File file = new File(filePath);
        //----------------------------------------------------------------------
//        try {
//            this.yamlUtil.parseYamlWithJackson(filePath);
//        } catch (IOException e) {
//           // e.printStackTrace();
//            this.printJsonString(new ErrorDto("Configuration file is not valid."));
//        }

        if (!this.yamlUtil.checkYamlCompatibility(file, YamlDto.class)) {
            this.printJsonString(new ErrorDto("Configuration file is not valid."));
        } else {
            //----------------------------------------------------------------------
            YamlDto yamlDto = this.yamlUtil.getYamlDtoFromYamlFile(filePath);
            ReportDto reportDto = this.generateReport(yamlDto, runId);
            this.printJsonString(reportDto);
        }
    }

    @Override
    public ReportDto generateReport(YamlDto yamlDto, Long runId) {
        ReportDto report = new ReportDto();
        report.setRunId(runId);
        report.setProject(yamlDto.getProject());

        List<SuiteDto> suites = this.provideSuites(yamlDto);
        report.setSuites(suites);

        boolean hasFailed = report.getSuites()
                .stream().anyMatch(s -> s.getStatus().equals(Status.FAILED));

        if (hasFailed) {
            report.setStatus(Status.FAILED);
        } else {
            report.setStatus(Status.PASSED);
        }

        return report;
    }

    @Override
    public ImportProjectDto getProject(YamlDto yamlDto) {
        return yamlDto.getProject();
    }

    @Override
    public List<SuiteDto> provideSuites(YamlDto yamlDto) {
        List<SuiteDto> suites = new ArrayList<>();

        for (ImportSuiteDto suite : yamlDto.getSuites()) {
            suite.getMap().forEach((key, value) -> {

                SuiteDto suiteDto = new SuiteDto();
                suiteDto.setName(key);
                List<TestDto> tests = this.provideTests(value, suiteDto);
                suiteDto.setTests(tests);

                suites.add(suiteDto);
            });
        }
        return suites;
    }

    @Override
    public List<TestDto> provideTests(ImportSuiteTestDto[] suiteTests, SuiteDto suiteDto) {
        List<TestDto> testDtos = new ArrayList<>();

        Map<String, ImportTestDetailDto> stringTestDetailMap = Arrays.stream(suiteTests)
                .map(ImportSuiteTestDto::getTests)
                .flatMap(tests -> tests.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<String, ImportTestDetailDto> map : stringTestDetailMap.entrySet()) {
            TestDto testDto = new TestDto();
            testDto.setName(map.getKey());

            //TestDto ---> "Test1": {
            try {
                TestDetailsInfoDto currentTest = this.commandExecutor.testParser(map.getValue());
                testDto.setTestDetailDto(currentTest);

                if (currentTest.getStatus().equals(Status.FAILED) && suiteDto.getStatus().equals(Status.PASSED)) {
                    suiteDto.setStatus(Status.FAILED);
                }

                testDtos.add(testDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return testDtos;
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
