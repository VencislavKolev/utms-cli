import service.CommandExecutor;
import service.JsonReportService;
import service.impl.CommandExecutorImpl;
import service.impl.JsonReportServiceImpl;
import util.YamlUtil;
import util.YamlUtilImpl;

public class App {
    public static void main(String[] args) {
        YamlUtil yamlUtil = new YamlUtilImpl();
        CommandExecutor commandExecutor = new CommandExecutorImpl();

        JsonReportService jsonReportService = new JsonReportServiceImpl(yamlUtil, commandExecutor);

        Engine engine = new Engine(jsonReportService);
        engine.run(args);
    }
}