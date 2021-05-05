package util;

import yamlImport.YamlDto;

import java.io.IOException;

public interface YamlUtil {
    void traverseSuites(YamlDto yaml);

    YamlDto parseYamlWithJackson(String path) throws IOException;
}
