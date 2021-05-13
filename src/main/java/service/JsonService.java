package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import models.jsonExport.ReportDto;

import java.io.IOException;
import java.util.Map;

public interface JsonService {
    ReportDto processInput(Map<String, String> cmdMap) throws IOException;

    void printJsonString(Object obj) throws JsonProcessingException;

    String getJsonString(Object obj) throws JsonProcessingException;
}
