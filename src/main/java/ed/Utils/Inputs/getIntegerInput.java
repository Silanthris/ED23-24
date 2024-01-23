package ed.Utils.Inputs;

import java.util.Scanner;

public class getIntegerInput {
    /**
     * Prompts the user with the specified message to enter an integer within the specified range.
     * Repeatedly prompts the user until a valid integer within the given range is provided.
     *
     * @param prompt The message to prompt the user with.
     * @param min The minimum allowed value (inclusive).
     * @param max The maximum allowed value (inclusive).
     * @return The integer entered by the user within the specified range.
     */
    public static int getIntegerInput(String prompt, int min, int max) {
        Scanner scanner = new Scanner(System.in);
        int userInput;

        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                System.out.print(prompt);
                scanner.next(); // Consume the invalid input
            }
            userInput = scanner.nextInt();

            if (userInput < min || userInput > max) {
                System.out.println("Please enter a value between " + min + " and " + max + ".");
            }

        } while (userInput < min || userInput > max);

        return userInput;
    }
}
