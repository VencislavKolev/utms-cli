package service.impl;

import models.jsonExport.TestDetailsInfoDto;
import models.jsonExport.TestDto;
import models.yamlImport.ImportSuiteTestDto;
import models.yamlImport.ImportTestDetailDto;
import service.CommandExecutor;
import service.TestGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static common.GlobalConstants.SUITE_CMD;
import static common.GlobalConstants.TEST_CMD;

public class TestGeneratorImpl implements TestGenerator {
    private final CommandExecutor commandExecutor;

    public TestGeneratorImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public List<TestDto> generateTests(ImportSuiteTestDto[] suiteTests, String currSuiteName, Map<String, String> commands) throws IOException, InterruptedException {
        List<TestDto> tests = new ArrayList<>();

        Map<String, ImportTestDetailDto> stringTestDetailMap = getMapFromSuiteTests(suiteTests);
        for (Map.Entry<String, ImportTestDetailDto> map : stringTestDetailMap.entrySet()) {

            TestDetailsInfoDto currentTest = new TestDetailsInfoDto();
            if (commands.isEmpty()) {
                //run everything
                currentTest = this.commandExecutor.testParser(map.getValue());
            } else {
                String suiteNameToRun;
                String testNameToRun;
                if (commands.size() == 1) {

                    if (commands.containsKey(SUITE_CMD)) {
                        //RUN SUITE
                        suiteNameToRun = commands.get(SUITE_CMD);
                        if (suiteNameToRun.equals(currSuiteName)) {
                            currentTest = this.commandExecutor.testParser(map.getValue());
                        } else {
                            currentTest = this.commandExecutor.getSkippedTest(map.getValue());
                        }
                    } else if (commands.containsKey(TEST_CMD)) {
                        //RUN TEST
                        testNameToRun = commands.get(TEST_CMD);
                        if (testNameToRun.equals(map.getKey())) {
                            //IF NAMES MATCH -> RUN TEST and EXPLICITLY SET ENABLED TRUE
                            map.getValue().setEnabled(true);
                            currentTest = this.commandExecutor.testParser(map.getValue());
                        } else {
                            //IF NAMES DIFFER -> GET SKIPPED TEST
                            currentTest = this.commandExecutor.getSkippedTest(map.getValue());
                        }
                    }
                } else {
                    suiteNameToRun = commands.get(SUITE_CMD);
                    testNameToRun = commands.get(TEST_CMD);
                    //RUN TEST IN SUITE
                    if (suiteNameToRun.equals(currSuiteName) && testNameToRun.equals(map.getKey())) {
                        //IF BOTH MATCH -> RUN TEST and EXPLICITLY SET ENABLED TRUE
                        map.getValue().setEnabled(true);
                        currentTest = this.commandExecutor.testParser(map.getValue());
                    } else {
                        //IF dont match -> skip
                        currentTest = this.commandExecutor.getSkippedTest(map.getValue());
                    }
                }
            }
            TestDto testDto = new TestDto(map.getKey(), currentTest);
            tests.add(testDto);
        }
        return tests;
    }

    private Map<String, ImportTestDetailDto> getMapFromSuiteTests(ImportSuiteTestDto[] suiteTests) {
        return Arrays.stream(suiteTests)
                .map(ImportSuiteTestDto::getTests)
                .flatMap(tests -> tests.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
