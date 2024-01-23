package ed.Utils.Inputs;

import java.util.Scanner;

public class getDoubleInput {

    /**
     * Prompts the user with the specified message to enter a decimal number within the specified range.
     * Repeatedly prompts the user until a valid decimal number within the given range is provided.
     *
     * @param prompt The message to prompt the user with.
     * @param min The minimum allowed value (inclusive).
     * @param max The maximum allowed value (inclusive).
     * @return The decimal number entered by the user within the specified range.
     */
    public static double getDoubleInput(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double userInput;

        do {
            System.out.print(prompt);
            while (!scanner.hasNextDouble()) {
                System.out.println("Please enter a valid decimal number.");
                System.out.print(prompt);
                scanner.next(); // Consume the invalid input
            }
            userInput = scanner.nextDouble();

            if (userInput < min || userInput > max) {
                System.out.println("Please enter a value between " + min + " and " + max + ".");
            }

        } while (userInput < min || userInput > max);

        return userInput;
    }

}
