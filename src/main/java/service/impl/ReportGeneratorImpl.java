package service.impl;

import models.enums.Status;
import models.jsonExport.ReportDto;
import models.jsonExport.SuiteDto;
import models.yamlImport.YamlDto;
import service.ReportGenerator;
import service.SuiteGenerator;

import java.util.*;

import static common.GlobalConstants.*;

public class ReportGeneratorImpl implements ReportGenerator {
    private final SuiteGenerator suiteGenerator;

    public ReportGeneratorImpl(SuiteGenerator suiteGenerator) {
        this.suiteGenerator = suiteGenerator;
    }

    @Override
    public ReportDto getReport(YamlDto yamlDto, String runId, Map<String, String> commands) {
        ReportDto report = new ReportDto();

        if (commands.containsKey(SUITE_CMD)) {
            String suiteToRun = commands.get(SUITE_CMD);
            boolean suiteExists = this.checkForExistingSuiteName(yamlDto, suiteToRun);
            if (!suiteExists) {
                report.setError(NOT_FOUND_SUITE);
                return report;
            }
        }
        if (commands.containsKey(TEST_CMD)) {
            String testToRun = commands.get(TEST_CMD);

            boolean testExists = this.checkForExistingTestName(yamlDto, testToRun);
            if (!testExists) {
                report.setError(NOT_FOUND_TEST);
                return report;
            }
        }

        if (commands.containsKey(SUITE_CMD) && commands.containsKey(TEST_CMD)) {
            //BOTH VALUES EXIST IN YAML BUT CHECK IF TEST EXISTS IN GIVEN SUITE
            String suite = commands.get(SUITE_CMD);
            String test = commands.get(TEST_CMD);

            boolean testExistsInSuite = this.checkForExistingTestInSuite(yamlDto, suite, test);
            if (!testExistsInSuite) {
                report.setError(NOT_FOUND_TEST_IN_SUITE);
                return report;
            }
        }

        if (runId != null) {
            report.setRunId(Long.parseLong(runId));
        }
        report.setProject(yamlDto.getProject());

        List<SuiteDto> suites = this.suiteGenerator.getSuites(yamlDto, commands);
        report.setSuites(suites);

        Status status = this.setReportStatus(report.getSuites());
        report.setStatus(status);
        return report;

    }

    private Status setReportStatus(List<SuiteDto> suites) {
        boolean hasFailedSuite = suites
                .stream().anyMatch(s -> s.getStatus().equals(Status.FAILED));
        if (hasFailedSuite) {
            return Status.FAILED;
        }

        boolean allSuitesSkipped = suites
                .stream().allMatch(s -> s.getStatus().equals(Status.SKIPPED));

        if (allSuitesSkipped) {
            return Status.SKIPPED;
        } else {
            return Status.PASSED;
        }
    }

    private boolean checkForExistingTestInSuite(YamlDto yamlDto, String suiteToFind, String test) {
        var foundSuite = yamlDto.getSuites()
                .stream()
                .filter(s -> s.getMap().containsKey(suiteToFind))
                .findFirst();

        return Arrays.stream(foundSuite.get().getMap().get(suiteToFind))
                .anyMatch(st -> st.getTests().containsKey(test));
    }

    private boolean checkForExistingSuiteName(YamlDto yamlDto, String suiteToRun) {
        return yamlDto.getSuites()
                .stream().anyMatch(s -> s.getMap().containsKey(suiteToRun));
    }

    private boolean checkForExistingTestName(YamlDto yamlDto, String testToRun) {
        return yamlDto.getSuites()
                .stream().anyMatch(s -> s.getMap().entrySet()
                        .stream().anyMatch(m -> Arrays.stream(m.getValue())
                                .anyMatch(x -> x.getTests().containsKey(testToRun))));
    }
}
