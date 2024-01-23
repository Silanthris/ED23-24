package ed.Utils.Inputs;

import java.util.Scanner;

public class getBooleanInput {

    /**
     * Prompts the user with the specified message to enter a boolean value.
     * Repeatedly prompts the user until a valid boolean input ('true' or 'false') is provided.
     *
     * @param prompt The message to prompt the user with.
     * @return The boolean value entered by the user.
     */
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
