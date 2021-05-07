package service;

import models.jsonExport.TestDetailsInfoDto;
import models.yamlImport.ImportTestDetailDto;

import java.io.IOException;

public interface CommandExecutor {
    TestDetailsInfoDto testParser(ImportTestDetailDto detail) throws InterruptedException, IOException;
}
