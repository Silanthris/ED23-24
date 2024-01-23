package ed.Utils.Inputs;

import java.util.Scanner;

public class getIntegerInput {
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
