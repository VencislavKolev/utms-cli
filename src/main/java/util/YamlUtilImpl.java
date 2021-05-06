package util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import yamlImport.ImportSuiteDto;
import yamlImport.ImportSuiteTestDto;
import yamlImport.YamlDto;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class YamlUtilImpl implements YamlUtil {

    private final FileUtil fileUtil;

    public YamlUtilImpl() {
        fileUtil = new FileUtilImpl();
    }

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
    public YamlDto getYamlDtoFromYamlFile(String filePath) throws IOException {
        File file = this.fileUtil.getFile(filePath);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(file, YamlDto.class);
    }

    @Override
    public YamlDto getYamlDtoFromYamlFile(File file) throws IOException {
        // ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return new ObjectMapper(new YAMLFactory()).readValue(file, YamlDto.class);
        // return objectMapper.readValue(file, YamlDto.class);
    }

    @Override
    public boolean checkYamlCompatibility(File file, Class<?> aClass) throws IOException {
        //ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            new ObjectMapper(new YAMLFactory()).readValue(file, YamlDto.class);
            //mapper.readValue(file, aClass);
            return true;
        } catch (JsonParseException | JsonMappingException e) {
            return false;
        }

    }
}
