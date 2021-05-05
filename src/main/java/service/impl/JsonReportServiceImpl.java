package service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entity.Status;
import jsonExport.ReportDto;
import jsonExport.SuiteDto;
import jsonExport.OutputTestDetailDto;
import jsonExport.TestDto;
import service.CommandExecutor;
import service.JsonReportService;
import util.YamlUtil;
import util.YamlUtilImpl;
import yamlImport.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonReportServiceImpl implements JsonReportService {
    //private final YamlUtil yamlUtil;
    private final CommandExecutor commandExecutor;

    public JsonReportServiceImpl() {
        //yamlUtil = new YamlUtilImpl();
        commandExecutor = new CommandExecutorImpl();
    }

    @Override
    public void generateJson(YamlDto yamlDto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ReportDto reportDto = this.generateReport(yamlDto);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reportDto);

        System.out.println(json);
    }

    @Override
    public ReportDto generateReport(YamlDto yamlDto) {
        ReportDto report = new ReportDto();
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
    public Project getProject(YamlDto yamlDto) {
        return yamlDto.getProject();
    }

    @Override
    public List<SuiteDto> provideSuites(YamlDto yamlDto) {
        List<SuiteDto> suites = new ArrayList<>();

        for (Suite suite : yamlDto.getSuites()) {
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
    public List<TestDto> provideTests(SuiteTest[] suiteTests, SuiteDto suiteDto) {
        List<TestDto> testDtos = new ArrayList<>();

        Map<String, TestDetail> stringTestDetailMap = Arrays.stream(suiteTests)
                .map(SuiteTest::getTests)
                .flatMap(tests -> tests.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<String, TestDetail> map : stringTestDetailMap.entrySet()) {
            TestDto testDto = new TestDto();
            testDto.setName(map.getKey());

            //TestDto ---> "Test1": {
            try {
                OutputTestDetailDto currentTest = this.commandExecutor.testParser(map.getValue());
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
}
