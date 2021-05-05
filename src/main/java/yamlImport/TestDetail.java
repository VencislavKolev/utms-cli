package yamlImport;

public class TestDetail {

    private boolean enabled;
    private String command;
    private String description;

    public TestDetail() {
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String value) {
        this.command = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    @Override
    public String toString() {
        return "TestDetail{" +
                "enabled=" + enabled +
                ", command='" + command + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
