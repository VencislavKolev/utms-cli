package service.impl;

import entity.Status;
import entity.Stream;
import jsonExport.OutputTestDetailDto;
import service.CommandExecutor;
import yamlImport.TestDetail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

public class CommandExecutorImpl implements CommandExecutor {
    private static final String PREFIX = "cmd.exe /c ";

    @Override
    public OutputTestDetailDto testParser(TestDetail detail) throws InterruptedException, IOException {

        if (!detail.getEnabled()) {
            return new OutputTestDetailDto(null, null, null, Status.SKIPPED, null, null);
        }

        String description = detail.getDescription();
        String encodedOutput = "";
        String encodedError = "";
        Status status;

        LocalDateTime startDate = LocalDateTime.now();
        Runtime run = Runtime.getRuntime();
         Process process = run.exec(PREFIX + detail.getCommand());

       // Process process = run.exec(PREFIX + "ls");
        int exitValue = process.waitFor();

        if (exitValue == 0) {
            String originalOutput = getOutput(process, Stream.INPUT);
            encodedOutput = this.encodeString(originalOutput);
            status = Status.PASSED;
        } else {
            String originalError = getOutput(process, Stream.ERROR);
            encodedError = this.encodeString(originalError);
            status = Status.FAILED;
        }

        LocalDateTime endDate = LocalDateTime.now();

        return new OutputTestDetailDto(description, encodedOutput, encodedError, status, startDate, endDate);
    }

    private String getOutput(Process process, Stream option) throws IOException {
        InputStreamReader streamReader = getStreamReader(process, option);

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(streamReader);
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    private InputStreamReader getStreamReader(Process process, Stream option) {
        InputStreamReader streamReader;

        if (option.equals(Stream.INPUT)) {
            streamReader = new InputStreamReader(process.getInputStream());
        } else {
            streamReader = new InputStreamReader(process.getErrorStream());
        }
        return streamReader;
    }

    private String encodeString(String originalOutput) {
        return Base64.getEncoder().encodeToString(originalOutput.getBytes(StandardCharsets.UTF_8));
    }
}
