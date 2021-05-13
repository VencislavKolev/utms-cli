import service.*;
import service.impl.*;
import util.YamlUtil;
import util.YamlUtilImpl;

public class App {
    public static void main(String[] args) {
        YamlUtil yamlUtil = new YamlUtilImpl();
        CommandExecutor commandExecutor = new CommandExecutorImpl();

        TestGenerator testGenerator = new TestGeneratorImpl(commandExecutor);
        SuiteGenerator suiteGenerator = new SuiteGeneratorImpl(testGenerator);
        ReportGenerator reportGenerator = new ReportGeneratorImpl(suiteGenerator);

        JsonService jsonReportService = new JsonServiceImpl(yamlUtil, reportGenerator);

        HttpClientService httpClientService = new HttpClientService();
        RunBodyGenerator generator = new RunBodyGenerator();

        Engine engine = new Engine(jsonReportService,httpClientService, generator);
        engine.run(args);
    }
}