package jsonExport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestDto {
    private String name;
    @JsonProperty(value = "details")
    private OutputTestDetailDto testDetailDto;

    public TestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OutputTestDetailDto getTestDetailDto() {
        return testDetailDto;
    }

    public void setTestDetailDto(OutputTestDetailDto testDetailDto) {
        this.testDetailDto = testDetailDto;
    }
}
