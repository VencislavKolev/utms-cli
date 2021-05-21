package service.impl;

import models.enums.Status;
import models.jsonExport.SuiteDto;
import models.jsonExport.TestDto;
import models.yamlImport.ImportSuiteDto;
import models.yamlImport.YamlDto;
import service.SuiteGenerator;
import service.TestGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuiteGeneratorImpl implements SuiteGenerator {
    private final TestGenerator testGenerator;

    public SuiteGeneratorImpl(TestGenerator testGenerator) {
        this.testGenerator = testGenerator;
    }

    @Override
    public List<SuiteDto> getSuites(YamlDto yamlDto, Map<String, String> commands) {
        List<SuiteDto> suites = new ArrayList<>();

        for (ImportSuiteDto suite : yamlDto.getSuites()) {
            suite.getMap().forEach((suiteName, suiteTests) -> {
                List<TestDto> tests = new ArrayList<>();

                try {
                    tests = this.testGenerator.generateTests(suiteTests, suiteName, commands);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Status status = this.getSuiteStatus(tests);
                SuiteDto suiteDto = new SuiteDto(suiteName, status, tests);

                suites.add(suiteDto);
            });

        }
        return suites;

    }

    private Status getSuiteStatus(List<TestDto> tests) {
        boolean hasFailedTest = tests
                .stream().anyMatch(t -> t.getTestDetailDto().getStatus().equals(Status.FAILED));
        if (hasFailedTest) {
            return Status.FAILED;
        }

        boolean allSuitesAreSkipped = tests
                .stream().allMatch(t -> t.getTestDetailDto().getStatus().equals(Status.SKIPPED));
        if (allSuitesAreSkipped) {
            return Status.SKIPPED;
        } else {
            return Status.PASSED;
        }
    }
}
