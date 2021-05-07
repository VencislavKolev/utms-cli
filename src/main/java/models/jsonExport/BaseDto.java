package models.jsonExport;

public abstract class BaseDto {
    private String error;

    public BaseDto() {
    }

    public BaseDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
