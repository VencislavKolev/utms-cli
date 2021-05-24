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
    public List<TestDto> generateTests(ImportSuiteTestDto[] suiteTests, String suiteToRun, Map<String, String> commands) throws IOException, InterruptedException {
        List<TestDto> tests = new ArrayList<>();

        Map<String, ImportTestDetailDto> stringTestDetailMap = this.extractTestDetailMap(suiteTests);

        for (var testMap : stringTestDetailMap.entrySet()) {

            boolean getSkipped = true;
            TestDetailsInfoDto currentTest = new TestDetailsInfoDto();

            if (commands.isEmpty()) {
                //RUN EVERYTHING -> NO SPECIAL REQUIREMENTS
                currentTest = this.commandExecutor.testParser(testMap.getValue());
                getSkipped = false;

            } else {
                String suite;
                String test;

                if (commands.size() == 1) {

                    if (commands.containsKey(SUITE_CMD)) {
                        //RUN SUITE
                        suite = commands.get(SUITE_CMD);

                        if (suite.equals(suiteToRun)) {
                            currentTest = this.commandExecutor.testParser(testMap.getValue());
                            getSkipped = false;
                        }/* else {
                            currentTest = this.commandExecutor.getSkippedTest(testMap.getValue());
                        }*/

                    } else if (commands.containsKey(TEST_CMD)) {
                        //RUN TEST
                        test = commands.get(TEST_CMD);

                        if (test.equals(testMap.getKey())) {
                            //IF NAMES MATCH -> RUN TEST and EXPLICITLY SET ENABLED TRUE

                            testMap.getValue().setEnabled(true);
                            currentTest = this.commandExecutor.testParser(testMap.getValue());
                            getSkipped = false;
                        } /*else {
                            //IF NAMES DIFFER -> GET SKIPPED TEST

                            currentTest = this.commandExecutor.getSkippedTest(testMap.getValue());
                        }*/
                    }

                } else {
                    //COMMANDS SIZE > 1 && RUN TEST IN GIVEN SUITE
                    suite = commands.get(SUITE_CMD);
                    test = commands.get(TEST_CMD);

                    if (suite.equals(suiteToRun) && test.equals(testMap.getKey())) {
                        //IF BOTH MATCH -> RUN TEST and EXPLICITLY SET ENABLED TRUE

                        testMap.getValue().setEnabled(true);
                        currentTest = this.commandExecutor.testParser(testMap.getValue());
                        getSkipped = false;

                    } /*else {
                        //IF DON'T MATCH -> GET SKIPPED TEST
                        currentTest = this.commandExecutor.getSkippedTest(testMap.getValue());
                    }*/
                }
            }
            if (getSkipped){
                currentTest = this.commandExecutor.getSkippedTest(testMap.getValue());
            }
            TestDto testDto = new TestDto(testMap.getKey(), currentTest);
            tests.add(testDto);
        }
        return tests;
    }

    private Map<String, ImportTestDetailDto> extractTestDetailMap(ImportSuiteTestDto[] suiteTests) {
        return Arrays.stream(suiteTests)
                .flatMap(t -> t.getTests().entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
