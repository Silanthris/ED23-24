package ed.Utils.Inputs;

import java.util.Scanner;

public class getDoubleInput {

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
