package service;

import jsonExport.OutputTestDetailDto;
import jsonExport.TestDto;
import yamlImport.TestDetail;

import java.io.IOException;

public interface CommandExecutor {
    OutputTestDetailDto testParser(TestDetail detail) throws InterruptedException, IOException;
}
