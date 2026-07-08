import bootstrap.MainInitializer;
import runner.MainRunner;

public class Main {

    public static void main(String[] args) {
        MainInitializer initializer = new MainInitializer();
        MainRunner runner = initializer.initialize();

        runner.run();
    }
}