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

public class Engine implements Runnable {

    private static final String YAML_FILEPATH = "src/main/resources/testing.yaml";

    private final YamlUtil yamlParser;
    private final JsonReportService jsonReportService;

    public Engine() {
        this.yamlParser = new YamlUtilImpl();
        this.jsonReportService = new JsonReportServiceImpl();
    }

    @Override
    public void run() {

        // this.readYamlAsMap();

//        try {
//            this.parseYamlWithJackson();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //---------NEW-------------
        YamlDto yamlDto = null;
        try {
            yamlDto = this.yamlParser.parseYamlWithJackson(YAML_FILEPATH);
            this.jsonReportService.generateJson(yamlDto);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseYamlWithJackson() throws IOException {
        File file = new File(YAML_FILEPATH);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        YamlDto yaml = objectMapper.readValue(file, YamlDto.class);

        this.yamlParser.traverseSuites(yaml);

        System.out.println();
    }


    private void readYamlAsMap() {
        Yaml yaml = new Yaml();
        InputStream inputStream = getInputStream();

        Map<String, Object> data = yaml.load(inputStream);
        //var suites = data.get("suites");
        System.out.println(data);
    }

    private InputStream getInputStream() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(YAML_FILEPATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStream;
    }


}
