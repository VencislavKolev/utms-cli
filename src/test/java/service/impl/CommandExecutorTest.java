package service.impl;

import models.enums.Status;
import models.jsonExport.TestDetailsInfoDto;
import models.yamlImport.ImportTestDetailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CommandExecutor;
import util.Base64Util;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommandExecutorTest {
    private CommandExecutor commandExecutor;

    @BeforeEach
    public void setUp() {
        this.commandExecutor = new CommandExecutorImpl();
    }

    @Test
    public void givenTestDetailWhenDisabledShouldReturnSkipped() throws IOException, InterruptedException {
        //given
        var test = new ImportTestDetailDto(false, "echo Hello");

        //when
        var expected = commandExecutor.testParser(test);

        //then
        var actual = new TestDetailsInfoDto(test.getDescription(), null, null, Status.SKIPPED, null, null);

        assertEquals(expected, actual);
    }

    @Test
    public void givenTestDetailWhenEnabledShouldReturn() throws IOException, InterruptedException {
        //given
        var test = new ImportTestDetailDto(true, "echo Hello");

        //when
        var expected = commandExecutor.testParser(test);

        //then
        assertNull(expected.getDescription());
        assertEquals(expected.getStatus(), Status.PASSED);
        assertEquals(expected.getError(), "");
        assertEquals(expected.getOutput(), Base64Util.encode("Hello"));
    }

    @Test
    public void givenTestDetailWhenEnabledShouldReturnFailedWhenExitCodeNotZero() throws IOException, InterruptedException {
        //given
        var test = new ImportTestDetailDto(true, "(exit -1)");

        //when
        var expected = commandExecutor.testParser(test);

        //then
        assertEquals(expected.getStatus(), Status.FAILED);
    }
}
