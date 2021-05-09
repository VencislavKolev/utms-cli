package models.jsonExport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestDto {
    private String name;
    @JsonProperty(value = "details")
    private TestDetailsInfoDto testDetailDto;

    public TestDto() {
    }

    public TestDto(String name, TestDetailsInfoDto testDetailDto) {
        this.name = name;
        this.testDetailDto = testDetailDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestDetailsInfoDto getTestDetailDto() {
        return testDetailDto;
    }

    public void setTestDetailDto(TestDetailsInfoDto testDetailDto) {
        this.testDetailDto = testDetailDto;
    }
}
