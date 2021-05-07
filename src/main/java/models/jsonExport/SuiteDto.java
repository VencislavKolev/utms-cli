package models.jsonExport;

import models.enums.Status;

import java.util.List;

public class SuiteDto {
    private String name;
    private Status status;
    private List<TestDto> tests;

    public SuiteDto() {
        this.setStatus(Status.PASSED);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<TestDto> getTests() {
        return tests;
    }

    public void setTests(List<TestDto> tests) {
        this.tests = tests;
    }
}
