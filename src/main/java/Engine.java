import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import service.JsonReportService;
import service.impl.JsonReportServiceImpl;
import util.YamlUtil;
import util.YamlUtilImpl;
import yamlImport.YamlDto;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class Engine{

    private static final String YAML_FILEPATH = "src/main/resources/testing.yaml";
    private static final String TESTING2 = "src/main/resources/testing2.yaml";

    private static final String INVALID_YAML = "src/main/resources/not-valid-testing.yaml";
    private static final String NONEXISTING_YAML = "varvarvar";

    // private final YamlUtil yamlParser;
    private final JsonReportService jsonReportService;

    public Engine(JsonReportService jsonReportService) {
        // this.yamlParser = new YamlUtilImpl();
        this.jsonReportService = jsonReportService;
    }


    public void run(String[] args) {

        // this.readYamlAsMap();

//        try {
//            this.parseYamlWithJackson();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //---------NEW-------------
//        YamlDto yamlDto = null;
//        try {
//            yamlDto = this.yamlParser.parseYamlWithJackson(TESTING2);
//            this.jsonReportService.generateJson(yamlDto);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (yamlDto == null){
//
//        }

//        try {
//            this.jsonReportService.generateJson(NONEXISTING_YAML,"2");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            Object object = this.jsonReportService.processInput(NONEXISTING_YAML, "2");
            this.jsonReportService.printJsonString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void parseYamlWithJackson() throws IOException {
//        File file = new File(INVALID_YAML);
//        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
//        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        YamlDto yaml = objectMapper.readValue(file, YamlDto.class);
//
//        this.yamlParser.traverseSuites(yaml);
//
//        System.out.println();
//    }
//
//
//    private void readYamlAsMap() {
//        Yaml yaml = new Yaml();
//        InputStream inputStream = getInputStream();
//
//        Map<String, Object> data = yaml.load(inputStream);
//        //var suites = data.get("suites");
//        System.out.println(data);
//    }
//
//    private InputStream getInputStream() {
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(new File(INVALID_YAML));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return inputStream;
//    }
}
