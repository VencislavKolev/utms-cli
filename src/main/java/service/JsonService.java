package service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface JsonService {
    Object processInput(String... args) throws IOException;

    void printJsonString(Object obj) throws JsonProcessingException;
}
