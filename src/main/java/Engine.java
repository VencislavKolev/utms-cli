import models.entity.TestRun;
import models.enums.MapType;
import models.jsonExport.ReportDto;
import util.CommandMapUtil;
import service.impl.HttpClientService;
import service.JsonService;
import service.impl.RunBodyGenerator;

import java.io.IOException;
import java.util.Map;

import static common.GlobalConstants.DEBUG_CMD;
import static common.GlobalConstants.SERVER_CMD;

public class Engine {

    private final JsonService jsonService;
    private final HttpClientService httpClientService;
    private final RunBodyGenerator generator;

    public Engine(JsonService jsonService, HttpClientService httpClientService, RunBodyGenerator generator) {
        this.jsonService = jsonService;
        this.httpClientService = httpClientService;
        this.generator = generator;
    }

    public void run(String[] args) {

        Map<String, String> cmdMap = CommandMapUtil.getCommandsMap(args, MapType.RUN);
        try {
            ReportDto result = this.jsonService.processInput(cmdMap);

            //-------------------
            //ReportDto result = this.jsonService.processInput("--config", "testing.yaml", "--run-id", "1");
            //ReportDto result = this.jsonService.processInput("--config", "skipping.yaml");

            //--------------BONUS ARGUMENTS---------
            //ReportDto result = this.jsonService.processInput("--config", "testing.yaml", "--suite-name", "UITests");

            //ReportDto result = this.jsonService.processInput("--config", "testing.yaml", "--suite-name", "UITests", "--test-name", "Test4");
            //ReportDto result = this.jsonService.processInput("--config", "testing.yaml", "--suite-name", "UITests", "--test-name", "Test1");
            //ReportDto result = this.jsonService.processInput("--config", "testing.yaml", "--test-name", "Test4");

            //-------------------SHORT COMMANDS-------------
            //ReportDto result = this.jsonService.processInput("-c", "testing.yaml", "-sn", "UITests", "-tn", "Test4");
            //ReportDto result = this.jsonService.processInput("-c", "testing.yaml", "-sn", "UITests", "-tn", "Test8");
            //ReportDto result = this.jsonService.processInput("-c", "testing.yaml", "-sn", "UITests");

            //this.jsonService.printJsonString(result);

            Map<String, String> envMap = CommandMapUtil.getCommandsMap(args, MapType.ENVIRONMENT);
            if (envMap.containsKey(SERVER_CMD)) {
                String server = envMap.get(SERVER_CMD);

                if (result.getError() == null) {
                    if (envMap.containsKey(DEBUG_CMD) && envMap.get(DEBUG_CMD).equals("true")) {
                        this.jsonService.printJsonString(result);
                    } else {
                        String projectJson = this.jsonService.getJsonString(result.getProject());
                        String id = this.httpClientService.postProjectRequest(projectJson, server);

                        TestRun run = this.generator.getRunFromResult(result);
                        String runJson = this.jsonService.getJsonString(run);
                        this.httpClientService.postRunToProjectRequest(runJson, server, id);
                    }
                } else {
                    this.jsonService.printJsonString(result);
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
