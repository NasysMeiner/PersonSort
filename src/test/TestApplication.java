package test;

import java.util.ArrayList;
import java.util.List;
import test.tests.FileDataPersisterTest;
import test.tests.RandomDataHolderTest;
import test.tests.SorterApplicationTest;

public class TestApplication {
    private List<Test> allTests;

    private  TestApplication() {
        allTests = new ArrayList<>();
    }

    public static void main() {
        TestApplication application = new TestApplication();
        application.initializeAllTest();
        application.run();
    }

    private void initializeAllTest() {
        allTests.add(new SorterApplicationTest());
        allTests.add(new RandomDataHolderTest());
        allTests.add(new FileDataPersisterTest());
    }

    private void run() {
        StringBuilder stringBuilder = new StringBuilder("\n");

        for(Test test : allTests) {
            test.run();

            if(test.getCode() != -1) {
                stringBuilder.append(test.getNameTest());
                stringBuilder.append(": ");
                stringBuilder.append(test.getStatus());

                if(test.getCode() == 0) {
                    stringBuilder.append("\n");
                    stringBuilder.append("\t");
                    stringBuilder.append(test.getResult());
                }

                System.out.println(stringBuilder.toString());
            }
        }
    }
}