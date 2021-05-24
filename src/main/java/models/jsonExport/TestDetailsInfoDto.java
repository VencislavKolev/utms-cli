package models.jsonExport;

import models.enums.Status;

import java.time.LocalDateTime;
import java.util.Objects;

public class TestDetailsInfoDto {

    private String description;
    private String output;
    private String error;
    private Status status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public TestDetailsInfoDto() {
    }

    public TestDetailsInfoDto(String description, String output, String error, Status status, LocalDateTime startDate, LocalDateTime endDate) {
        this.description = description;
        this.output = output;
        this.error = error;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestDetailsInfoDto that = (TestDetailsInfoDto) o;
        return Objects.equals(description, that.description) && Objects.equals(output, that.output) && Objects.equals(error, that.error) && status == that.status && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, output, error, status, startDate, endDate);
    }
}
