package service.impl;

import models.enums.Status;
import models.enums.StreamType;
import models.jsonExport.TestDetailsInfoDto;
import service.CommandExecutor;
import models.yamlImport.ImportTestDetailDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

public class CommandExecutorImpl implements CommandExecutor {
    private static final String WIN_PREFIX = "cmd.exe /c ";

    @Override
    public TestDetailsInfoDto testParser(ImportTestDetailDto detail) throws InterruptedException, IOException {

        if (!detail.getEnabled()) {
            return this.getSkippedTest(detail);
        }

        String description = detail.getDescription();
        String encodedOutput, encodedError, originalOutput, originalError;

        LocalDateTime startDate = LocalDateTime.now();

        Runtime run = Runtime.getRuntime();
        Process process = this.getProcess(detail, run);

        int exitValue = process.waitFor();

        originalOutput = this.getCommandOutput(process, StreamType.INPUT);
        encodedOutput = this.encodeToBase64(originalOutput);

        originalError = this.getCommandOutput(process, StreamType.ERROR);
        encodedError = this.encodeToBase64(originalError);

        Status status = this.getStatus(exitValue);
        LocalDateTime endDate = LocalDateTime.now();

        return new TestDetailsInfoDto(description, encodedOutput, encodedError, status, startDate, endDate);
    }

    private Status getStatus(int exitValue) {
        Status status;
        if (exitValue == 0) {
            status = Status.PASSED;
        } else {
            status = Status.FAILED;
        }
        return status;
    }

    private Process getProcess(ImportTestDetailDto detail, Runtime run) throws IOException {
        Process process;

        String operSys = System.getProperty("os.name").toLowerCase();
        if (operSys.contains("win")) {
            process = run.exec(WIN_PREFIX + detail.getCommand());
        } else {
            process = run.exec(detail.getCommand());
        }
        return process;
    }

    @Override
    public TestDetailsInfoDto getSkippedTest(ImportTestDetailDto detail) {
        return new TestDetailsInfoDto(detail.getDescription(), null, null, Status.SKIPPED, null, null);
    }

    private String getCommandOutput(Process process, StreamType streamType) throws IOException {
        InputStreamReader streamReader = this.getStreamReader(process, streamType);
        BufferedReader reader = new BufferedReader(streamReader);

        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    private InputStreamReader getStreamReader(Process process, StreamType streamType) {
        InputStreamReader streamReader;

        if (streamType.equals(StreamType.INPUT)) {
            streamReader = new InputStreamReader(process.getInputStream());
        } else {
            streamReader = new InputStreamReader(process.getErrorStream());
        }
        return streamReader;
    }

    private String encodeToBase64(String originalOutput) {
        return Base64.getEncoder().encodeToString(originalOutput.getBytes(StandardCharsets.UTF_8));
    }
}
