package ed.Utils.Inputs;

import java.util.Scanner;

public class getBooleanInput {

    public static boolean getBooleanInput(String prompt) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(prompt);
        while (!scanner.hasNextBoolean()) {
            System.out.println("Please enter 'true' or 'false'.");
            System.out.print(prompt);
            scanner.next(); // Consume the invalid input
        }

        return scanner.nextBoolean();
    }


}
