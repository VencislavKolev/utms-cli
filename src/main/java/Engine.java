import models.entity.TestRun;
import models.enums.MapType;
import models.jsonExport.ReportDto;
import service.impl.CommandMapImpl;
import service.impl.HttpClientService;
import service.JsonService;
import service.impl.RunBodyGenerator;

import java.io.IOException;
import java.util.Map;

import static common.GlobalConstants.DEBUG_CMD;
import static common.GlobalConstants.SERVER_CMD;

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

        Map<String, String> cmdMap = CommandMapImpl.getCommandsMap(args, MapType.RUN);
        try {
            ReportDto object = this.jsonService.processInput(cmdMap);

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

            //this.jsonService.printJsonString(object);

            var envMap = CommandMapImpl.getCommandsMap(args, MapType.ENVIRONMENT);
            if (envMap.containsKey(SERVER_CMD)) {
                String server = envMap.get(SERVER_CMD);

                if (object.getError() == null) {
                    if (envMap.containsKey(DEBUG_CMD) && envMap.get(DEBUG_CMD).equals("true")) {
                        this.jsonService.printJsonString(object);
                    } else {
                        String projectJson = this.jsonService.getJsonString(object.getProject());
                        String id = this.httpClientService.sendRequest(projectJson, server);

                        TestRun run = this.generator.getRunFromResult(object);
                        String runJson = this.jsonService.getJsonString(run);
                        //this.jsonService.printJsonString(run);
                        this.httpClientService.sendRunToProject(runJson, server, id);
                    }
                } else {
                    this.jsonService.printJsonString(object);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        for (String arg : args) {
            System.out.println(arg);
        }
    }
}
