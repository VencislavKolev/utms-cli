package service;

import jsonExport.TestDetailsInfoDto;
import yamlImport.ImportTestDetailDto;

import java.io.IOException;

public interface CommandExecutor {
    TestDetailsInfoDto testParser(ImportTestDetailDto detail) throws InterruptedException, IOException;
}
