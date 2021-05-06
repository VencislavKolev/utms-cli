import service.JsonReportService;
import service.impl.JsonReportServiceImpl;

public class App {
    public static void main(String[] args) {
        JsonReportService jsonReportService = new JsonReportServiceImpl();
        Engine engine = new Engine(jsonReportService);
        engine.run(args);
    }
}