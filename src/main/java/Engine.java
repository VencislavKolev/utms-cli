import service.JsonReportService;

import java.io.IOException;

public class Engine {

    private static final String YAML_FILEPATH = "./testing.yaml";
    private static final String TESTING2 = "/testing2.yaml";

    private static final String INVALID_YAML = "./not-valid-testing.yaml";
    private static final String NONEXISTING_YAML = "varvarvar";

    private final JsonReportService jsonReportService;

    public Engine(JsonReportService jsonReportService) {
        this.jsonReportService = jsonReportService;
    }

    public void run(String[] args) {

        try {
            Object object = this.jsonReportService.processInput(args);
            //Object object = this.jsonReportService.processInput("--config", "testing.yaml", "--run-id", "1");
            //Object object = this.jsonReportService.processInput("--config", "skipping.yaml");
            this.jsonReportService.printJsonString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String arg : args) {
            System.out.println(arg);
        }
    }
}
