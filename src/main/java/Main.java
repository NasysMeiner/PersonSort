import bootstrap.MainInitializer;
import runner.MainRunner;

public class Main {

    public static void main(String[] args) {
        MainInitializer initializer = new MainInitializer();

        initializer.initialize();

        MainRunner runner = new MainRunner();

        runner.run();
    }
}