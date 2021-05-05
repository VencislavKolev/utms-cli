package jsonExport;

import entity.Status;

import java.time.LocalDateTime;

public class OutputTestDetailDto {
    private String description;
    private String output;
    private String error;
    private Status status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public OutputTestDetailDto() {
    }

    public OutputTestDetailDto(String description, String output, String error, Status status, LocalDateTime startDate, LocalDateTime endDate) {
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
}
