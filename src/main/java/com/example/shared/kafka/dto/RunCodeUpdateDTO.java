package com.example.shared.kafka.dto;

import java.util.ArrayList;
import java.util.Date;

import com.example.shared.kafka.dto.RunCodeUpdateDTO.TestCase.TestCaseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RunCodeUpdateDTO {
    private Date updateTime;
    private String uuid;

    public RunCodeUpdateDTO() {
        updateTime = new Date();
    }

    private Preprocessing preprocessing;

    private ArrayList<TestCase> testCases;
    private Judgement judgement = Judgement.Pending;

    public void addTestCase(TestCase testCase) {
        if (this.testCases == null)
            this.testCases = new ArrayList<>();
        testCase.setId(Long.valueOf(this.testCases.size()));
        this.testCases.add(testCase);
    }

    public void addTestCase(TestCaseStatus status, String output) {
        TestCase testCase = new TestCase();
        testCase.setStatus(status);
        testCase.setOutput(output);

        addTestCase(testCase);
    }

    public void addTestCaseWithRuntimeError(String content) {
        TestCase testCase = new TestCase();
        testCase.setContent(content);
        testCase.setStatus(TestCaseStatus.RunTimeError);

        addTestCase(testCase);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestCase {
        private Long id;
        private TestCaseStatus status;
        private String output;

        private String content;

        public static enum TestCaseStatus {
            Accepted,
            NotAccepted,
            RunTimeError,
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Preprocessing {
        private PreprocessingStatus status;
        private String header;
        private String content;

        public static enum PreprocessingStatus {
            Skipped,
            Accepted,
            Error,
        }
    }

    public static enum Judgement {
        Accepted,
        CompilationError,
        NotAccepted,
        Pending,
    }
}
