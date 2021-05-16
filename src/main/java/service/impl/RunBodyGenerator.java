package service.impl;

import models.entity.TestCase;
import models.entity.TestRun;
import models.entity.TestSuite;
import models.enums.Status;
import models.jsonExport.ReportDto;
import models.jsonExport.SuiteDto;
import models.jsonExport.TestDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RunBodyGenerator {

    public RunBodyGenerator() {
    }

    public TestRun getRunFromResult(ReportDto reportDto) {
        Set<TestSuite> testSuites = this.getTestSuites(reportDto.getSuites());
        Status status = reportDto.getStatus();

        return new TestRun(status, testSuites);
    }

    public Set<TestSuite> getTestSuites(List<SuiteDto> suiteDtos) {
        Set<TestSuite> testSuites = new HashSet<>();
        for (SuiteDto suite : suiteDtos) {

            String suiteName = suite.getName();
            Status status = suite.getStatus();
            Set<TestCase> testCases = this.getTestCases(suite);

            TestSuite testSuite = new TestSuite(suiteName, status, testCases);
            testSuites.add(testSuite);
        }
        return testSuites;
    }

    public Set<TestCase> getTestCases(SuiteDto suiteDto) {
        Set<TestCase> testCases = new HashSet<>();
        for (TestDto test : suiteDto.getTests()) {

            String name = test.getName();
            String description = test.getTestDetailDto().getDescription();
            Status status = test.getTestDetailDto().getStatus();

            String output = test.getTestDetailDto().getOutput();
            String error = test.getTestDetailDto().getError();

            LocalDateTime startDate = test.getTestDetailDto().getStartDate();
            LocalDateTime endDate = test.getTestDetailDto().getEndDate();

            TestCase testCase = new TestCase(name, description, status, output, error, startDate, endDate);
            testCases.add(testCase);
        }
        return testCases;
    }
}
