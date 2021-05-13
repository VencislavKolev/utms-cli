package models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.enums.Status;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestRun {

    private Status status;
    private Set<TestSuite> testSuites = new HashSet<>();
    private Project project;

    public TestRun(Status status, Set<TestSuite> testSuites) {
        this.status = status;
        this.testSuites = testSuites;
    }

}
