package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import yamlImport.ImportSuiteDto;
import yamlImport.ImportSuiteTestDto;
import yamlImport.YamlDto;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class YamlUtilImpl implements YamlUtil {

    @Override
    public void traverseSuites(YamlDto yaml) {
        for (ImportSuiteDto suite : yaml.getSuites()) {
            suite.getMap().forEach((key, value) -> {
                System.out.println(key);

                Arrays.stream(value)
                        .map(ImportSuiteTestDto::getTests)
                        .flatMap(tests -> tests.entrySet().stream())
                        .forEach(kvp -> System.out.printf("Name: %s - %s%n", kvp.getKey(), kvp.getValue()));
            });
        }
    }

    @Override
    public YamlDto parseYamlWithJackson(String path) throws IOException {
        File file = new File(path);
        //TODO check for existing file

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(file, YamlDto.class);
    }
}
