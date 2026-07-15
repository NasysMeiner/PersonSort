package test;

import java.util.ArrayList;
import java.util.List;

import test.tests.EvenOddSorterDecoratorTest;
import test.tests.FileDataPersisterTest;
import test.tests.MergeSortTest;
import test.tests.RandomDataHolderTest;
import test.tests.SorterApplicationTest;
import test.tests.SorterSelectionTest;

public class TestApplication {

    private final List<Test> allTests;

    private TestApplication() {
        allTests = new ArrayList<>();
    }

    public static void main(String[] args) {
        TestApplication application = new TestApplication();
        application.initializeAllTest();
        application.run();
    }

    private void initializeAllTest() {
        allTests.add(new MergeSortTest());
        allTests.add(new EvenOddSorterDecoratorTest());
        allTests.add(new SorterSelectionTest());
        allTests.add(new SorterApplicationTest());
        allTests.add(new RandomDataHolderTest());
        allTests.add(new FileDataPersisterTest());
    }

    private void run() {
        for (Test test : allTests) {
            test.run();

            if (test.getCode() != -1) {
                System.out.println(
                        test.getNameTest()
                                + ": "
                                + test.getStatus()
                );

                if (test.getCode() == 0) {
                    System.out.println("\t" + test.getResult());
                }

                System.out.println();
            }
        }
    }
}