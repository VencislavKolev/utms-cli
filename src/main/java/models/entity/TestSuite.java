package models.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.enums.Status;

import java.util.HashSet;
import java.util.Set;


//@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestSuite {
    private String name;
    private Status status;
    private Set<TestCase> testCases = new HashSet<>();

}
