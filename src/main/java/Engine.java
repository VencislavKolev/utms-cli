import models.entity.TestRun;
import models.jsonExport.ReportDto;
import service.impl.HttpClientService;
import service.JsonService;
import service.impl.RunBodyGenerator;

import java.io.IOException;

public class Engine {

    private static final String YAML_FILEPATH = "./testing.yaml";
    private static final String TESTING2 = "/testing2.yaml";

    private static final String INVALID_YAML = "./not-valid-testing.yaml";
    private static final String NONEXISTING_YAML = "varvarvar";

    private final JsonService jsonService;
    private final HttpClientService httpClientService;
    private final RunBodyGenerator generator;

    public Engine(JsonService jsonService, HttpClientService httpClientService, RunBodyGenerator generator) {
        this.jsonService = jsonService;
        this.httpClientService = httpClientService;
        this.generator = generator;
    }

    public void run(String[] args) {

        try {
            ReportDto object = this.jsonService.processInput(args);

            //-------------------
            //Object object = this.jsonService.processInput("--config", "testing.yaml", "--run-id", "1");
            //Object object = this.jsonService.processInput("--config", "skipping.yaml");

            //--------------BONUS ARGUMENTS---------
            //Object object = this.jsonService.processInput("--config", "testing.yaml", "--suite-name", "UITests");

            //Object object = this.jsonService.processInput("--config", "testing.yaml", "--suite-name", "UITests", "--test-name", "Test4");
            //Object object = this.jsonService.processInput("--config", "testing.yaml", "--suite-name", "UITests", "--test-name", "Test1");
            //Object object = this.jsonService.processInput("--config", "testing.yaml", "--test-name", "Test4");

            //-------------------SHORT COMMANDS-------------
            //Object object = this.jsonService.processInput("-c", "testing.yaml", "-sn", "UITests", "-tn", "Test4");
            //Object object = this.jsonService.processInput("-c", "testing.yaml", "-sn", "UITests", "-tn", "Test8");
            //Object object = this.jsonService.processInput("-c", "testing.yaml", "-sn", "UITests");

            this.jsonService.printJsonString(object);

            if (object.getError() == null) {
                String projectJson = this.jsonService.getJsonString(object.getProject());
                String id = this.httpClientService.sendRequest(projectJson);

                TestRun run = this.generator.getRunFromResult(object);
                String runJson = this.jsonService.getJsonString(run);
                this.jsonService.printJsonString(run);

                this.httpClientService.sendRunToProject(runJson, id);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        for (String arg : args) {
            System.out.println(arg);
        }
    }
}
