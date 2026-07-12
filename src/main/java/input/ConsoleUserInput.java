package input;

import java.util.Scanner;

public class ConsoleUserInput implements UserInput {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getInput() {
        return scanner.nextLine();
    }
}