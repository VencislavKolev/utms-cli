package service.impl;

import models.enums.Status;
import models.jsonExport.TestDetailsInfoDto;
import models.jsonExport.TestDto;
import models.yamlImport.ImportSuiteTestDto;
import models.yamlImport.ImportTestDetailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.CommandExecutor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.GlobalConstants.SUITE_CMD;
import static common.GlobalConstants.TEST_CMD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestGeneratorTest {

    @Mock
    private CommandExecutor commandExecutor;

    @InjectMocks
    private TestGeneratorImpl testGenerator;

    private TestDetailsInfoDto passedTest;
    private TestDetailsInfoDto skippedTest;

    @BeforeEach
    public void setUp() {
        passedTest = new TestDetailsInfoDto("some description", "output", "", Status.PASSED, LocalDateTime.now(), LocalDateTime.now().plusNanos(100));
        skippedTest = new TestDetailsInfoDto("some description", null, null, Status.SKIPPED, null, null);
    }

    @Test
    void givenEmptyMapShouldRunEverything(@Mock ImportSuiteTestDto suiteTest) throws IOException, InterruptedException {

        Map<String, ImportTestDetailDto> tests = suiteTest.getTests();
        Map<String, String> commandMap = new HashMap<>();


        String testName = "Test1";
        var test = new ImportTestDetailDto(true, "echo Hello");
        TestDetailsInfoDto testDetailsInfoDto = this.commandExecutor.testParser(test);
        verify(this.commandExecutor).testParser(test);

    }

    @Test
    void name() throws IOException, InterruptedException {
        var expected = new TestDetailsInfoDto("some description", "output", "", Status.PASSED, LocalDateTime.now(), LocalDateTime.now().plusNanos(100));

        when(this.commandExecutor.testParser(Mockito.any())).thenReturn(expected);

        //then
        var actual = this.commandExecutor.testParser(Mockito.any());
        assertEquals(expected, actual);

    }

    @Test
    void emptyMap() throws IOException, InterruptedException {
        ImportTestDetailDto test1 = new ImportTestDetailDto(true, "echo Hello");
        //given
        var suite = new ImportSuiteTestDto(Map.of("Test1", test1));
        var suiteTests = new ImportSuiteTestDto[1];
        suiteTests[0] = suite;


        String suiteToRun = "BackEnd";
        Map<String, String> cmdMap = new HashMap<>();

        var returned = new TestDetailsInfoDto("some description", "output", "", Status.PASSED, LocalDateTime.now(), LocalDateTime.now().plusNanos(100));
        when(this.commandExecutor.testParser(test1)).thenReturn(returned);
        List<TestDto> expected = List.of(
                new TestDto("Test1", this.commandExecutor.testParser(test1))
        );
        verify(this.commandExecutor).testParser(test1);
    }


    @Test
    void test3() throws IOException, InterruptedException {
        //given
        var test1 = new ImportTestDetailDto(true, "echo Hello");
        var suite = new ImportSuiteTestDto(Map.of("Test1", test1));
        var suiteTests = new ImportSuiteTestDto[]{
                suite
        };

        String suiteToRun = "BackEnd";
        Map<String, String> cmdMap = new HashMap<>();

        var returned = new TestDetailsInfoDto("some description", "output", "", Status.PASSED, LocalDateTime.now(), LocalDateTime.now().plusNanos(100));
        when(this.commandExecutor.testParser(test1)).thenReturn(returned);

        this.commandExecutor.testParser(test1);

        //THEN
        verify(this.commandExecutor, times(1)).testParser(test1);
    }

    @Test
    void givenNonExistingSuiteAndValidTestShouldReturnSkipped() throws IOException, InterruptedException {
        //GIVEN
        var test1 = new ImportTestDetailDto(true, "echo Hello");
        var suiteTests = new ImportSuiteTestDto[]{
                new ImportSuiteTestDto(Map.of("Test1", test1))
        };

        String suiteToRun = "BackEnd";
        Map<String, String> cmdMap = Map.of(TEST_CMD, "Test1", SUITE_CMD, suiteToRun + 1);

        //WHEN
        when(this.commandExecutor.getSkippedTest(test1)).thenReturn(this.skippedTest);

        var returnedList = List.of(new TestDto("Test1", this.skippedTest));
        List<TestDto> testDtos = this.testGenerator.generateTests(suiteTests, suiteToRun, cmdMap);

        //THEN
        assertEquals(returnedList.size(), testDtos.size());
    }

    @Test
    void givenNonExistingSuiteShouldReturnSkipped() throws IOException, InterruptedException {
        //GIVEN
        var test1 = new ImportTestDetailDto(true, "echo Hello");
        var suiteTests = new ImportSuiteTestDto[]{
                new ImportSuiteTestDto(Map.of("Test1", test1))
        };

        String suiteToRun = "BackEnd";
        Map<String, String> cmdMap = Map.of(SUITE_CMD, suiteToRun + 1);

        //WHEN
        when(this.commandExecutor.getSkippedTest(test1)).thenReturn(this.skippedTest);

        var returnedList = List.of(new TestDto("Test1", this.skippedTest));
        List<TestDto> testDtos = this.testGenerator.generateTests(suiteTests, suiteToRun, cmdMap);

        //THEN
        assertEquals(returnedList.size(), testDtos.size());
        assertEquals(returnedList.contains(this.skippedTest), testDtos.contains(this.skippedTest));
    }

    @Test
    void givenNonExistingTestShouldReturnSkipped() throws IOException, InterruptedException {
        //GIVEN
        var test1 = new ImportTestDetailDto(true, "echo Hello");
        var suiteTests = new ImportSuiteTestDto[]{
                new ImportSuiteTestDto(Map.of("Test1", test1))
        };

        String suiteToRun = "BackEnd";
        Map<String, String> cmdMap = Map.of(TEST_CMD, "Test2");

        //WHEN
        when(this.commandExecutor.getSkippedTest(test1)).thenReturn(this.skippedTest);

        var returnedList = List.of(new TestDto("Test1", this.skippedTest));
        List<TestDto> testDtos = this.testGenerator.generateTests(suiteTests, suiteToRun, cmdMap);

        //THEN
        assertEquals(returnedList.size(), testDtos.size());
        assertEquals(returnedList.contains(this.skippedTest), testDtos.contains(this.skippedTest));
    }

    @Test
    void givenNonExistingTestAndValidSuiteShouldReturnSkipped() throws IOException, InterruptedException {
        //GIVEN
        var test1 = new ImportTestDetailDto(true, "echo Hello");
        var suiteTests = new ImportSuiteTestDto[]{
                new ImportSuiteTestDto(Map.of("Test1", test1))
        };

        String suiteToRun = "BackEnd";
        Map<String, String> cmdMap = Map.of(TEST_CMD, "Test2", SUITE_CMD, suiteToRun);

        //WHEN
        when(this.commandExecutor.getSkippedTest(test1)).thenReturn(this.skippedTest);

        var returnedList = List.of(new TestDto("Test1", this.skippedTest));
        List<TestDto> testDtos = this.testGenerator.generateTests(suiteTests, suiteToRun, cmdMap);

        //THEN
        assertEquals(returnedList.size(), testDtos.size());
        assertEquals(returnedList.contains(this.skippedTest), testDtos.contains(this.skippedTest));
    }

    @Test
    void givenTestAndSuiteShouldRunWhenBothAreValid() throws IOException, InterruptedException {
        //GIVEN
        var test1 = new ImportTestDetailDto(true, "echo Hello");
        var suiteTests = new ImportSuiteTestDto[]{
                new ImportSuiteTestDto(Map.of("Test1", test1))
        };

        String suiteToRun = "BackEnd";
        Map<String, String> cmdMap = Map.of(TEST_CMD, "Test1", SUITE_CMD, suiteToRun);

        //WHEN
        when(this.commandExecutor.testParser(test1)).thenReturn(this.passedTest);

        var returnedList = List.of(new TestDto("Test1", this.passedTest));
        List<TestDto> testDtos = this.testGenerator.generateTests(suiteTests, suiteToRun, cmdMap);

        //THEN
        assertEquals(returnedList.size(), testDtos.size());
        assertEquals(returnedList.contains(this.passedTest), testDtos.contains(this.passedTest));
    }

    @Test
    void givenTestShouldRunWhenIsValid() throws IOException, InterruptedException {
        //GIVEN
        var test1 = new ImportTestDetailDto(true, "echo Hello");
        var suiteTests = new ImportSuiteTestDto[]{
                new ImportSuiteTestDto(Map.of("Test1", test1))
        };

        String suiteToRun = "BackEnd";
        Map<String, String> cmdMap = Map.of(TEST_CMD, "Test1");

        //WHEN
        when(this.commandExecutor.testParser(test1)).thenReturn(this.passedTest);

        var returnedList = List.of(new TestDto("Test1", this.passedTest));
        List<TestDto> testDtos = this.testGenerator.generateTests(suiteTests, suiteToRun, cmdMap);

        //THEN
        assertEquals(returnedList.size(), testDtos.size());
        assertEquals(returnedList.contains(this.passedTest), testDtos.contains(this.passedTest));
    }

    @Test
    void givenSuiteShouldRunWhenIsValid() throws IOException, InterruptedException {
        //GIVEN
        var test1 = new ImportTestDetailDto(true, "echo Hello");
        var suiteTests = new ImportSuiteTestDto[]{
                new ImportSuiteTestDto(Map.of("Test1", test1))
        };

        String suiteToRun = "BackEnd";
        Map<String, String> cmdMap = Map.of(SUITE_CMD, suiteToRun);

        //WHEN
        when(this.commandExecutor.testParser(test1)).thenReturn(this.passedTest);
        var returnedList = List.of(new TestDto("Test1", this.passedTest));

        List<TestDto> testDtos = this.testGenerator.generateTests(suiteTests, suiteToRun, cmdMap);

        //THEN
        assertEquals(returnedList.size(), testDtos.size());
        assertEquals(returnedList.contains(this.passedTest), testDtos.contains(this.passedTest));
    }

    @Test
    void givenEmptyMapShouldRun() throws IOException, InterruptedException {
        //GIVEN
        var test1 = new ImportTestDetailDto(true, "echo Hello");
        var suiteTests = new ImportSuiteTestDto[]{
                new ImportSuiteTestDto(Map.of("Test1", test1))
        };

        String suiteToRun = "BackEnd";
        Map<String, String> cmdMap = new HashMap<>();

        //WHEN
        when(this.commandExecutor.testParser(test1)).thenReturn(this.passedTest);
        var returnedList = List.of(new TestDto("Test1", this.passedTest));

        List<TestDto> testDtos = this.testGenerator.generateTests(suiteTests, suiteToRun, cmdMap);

        //THEN
        assertEquals(returnedList.size(), testDtos.size());
        assertEquals(returnedList.contains(this.passedTest), testDtos.contains(this.passedTest));
    }
}
