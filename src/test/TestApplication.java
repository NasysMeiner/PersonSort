package test;

import java.util.Arrays;
import test.tests.AppendResultTest;
import test.tests.FileDataPersisterTest;
import test.tests.RandomDataHolderTest;
import test.tests.SorterApplicationTest;

public class TestApplication {
    private Test[] allTests;
    private int testCount;

    private TestApplication() {
        allTests = new Test[10];
        testCount = 0;
    }

    public static void main(String[] args) {
        TestApplication application = new TestApplication();
        application.initializeAllTest();
        application.run();
    }

    private void addTest(Test test) {
        if (testCount == allTests.length) {
            allTests = Arrays.copyOf(allTests, allTests.length * 2);
        }
        allTests[testCount++] = test;
    }

    private void initializeAllTest() {
        addTest(new SorterApplicationTest());
        addTest(new RandomDataHolderTest());
        addTest(new FileDataPersisterTest());
        addTest(new AppendResultTest());
    }

    private void run() {
        StringBuilder stringBuilder = new StringBuilder("\n");

        for (int i = 0; i < testCount; i++) {
            Test test = allTests[i];
            test.run();

            if (test.getCode() != -1) {
                stringBuilder.append(test.getNameTest());
                stringBuilder.append(": ");
                stringBuilder.append(test.getStatus());

                if (test.getCode() == 0) {
                    stringBuilder.append("\n");
                    stringBuilder.append("\t");
                    stringBuilder.append(test.getResult());
                }

                System.out.println(stringBuilder.toString());
            }
        }
    }
}