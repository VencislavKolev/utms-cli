package util;

import yamlImport.YamlDto;

import java.io.File;
import java.io.IOException;

public interface YamlUtil {
    void traverseSuites(YamlDto yaml);

    YamlDto getYamlDtoFromYamlFile(String filePath) throws IOException;

    YamlDto getYamlDtoFromYamlFile(File file) throws IOException;

    boolean checkYamlCompatibility(File file, Class<?> aClass) throws IOException;
}
