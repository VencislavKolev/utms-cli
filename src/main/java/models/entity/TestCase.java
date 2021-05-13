package models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.enums.Status;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestCase {
    private Long id;
    private String name;
    private String description;

    private Status status;
    private String output;
    private String error;
    private LocalDateTime start;
    private LocalDateTime end;

    private TestSuite testSuite;

    public TestCase(String name, String description, Status status, String output, String error, LocalDateTime start, LocalDateTime end) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.output = output;
        this.error = error;
        this.start = start;
        this.end = end;
    }
}
