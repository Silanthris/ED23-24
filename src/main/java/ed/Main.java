package ed;

import ed.API.Game.EntitiesLocation;
import ed.API.Game.GameMap;
import ed.Utils.Graph.Graph;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        //double densityCalculated = ((numLocations * (numLocations - 1)) * 0.5) / 100;

        int numLocations = 0;
        boolean bidirectional = true;
        double density = 1;


        System.out.println("Welcome to Map Creation!");

        // Ask the user to choose between creating a new map or importing an existing one
        System.out.println("Do you want to create a new map or import an existing one?");
        System.out.println("1. Create a new map");
        System.out.println("2. Import an existing map");

        int choice = getUserChoice(scanner, 1, 2);

        if (choice == 1) {

            numLocations = getIntegerInput("Enter the number of locations (max 45): ", 1, 45);
            bidirectional = getBooleanInput("Should paths be bidirectional? (true/false): ");
            density = getDoubleInput("Enter the density (max 1.0): ", 0.0, 1.0);

        } else {
            // User chose to import an existing map
            //importExistingMap();
        }


        GameMap gameMap = new GameMap(bidirectional, density);
        gameMap.generateRandomGraph(numLocations, bidirectional);

        gameMap.printGraph();

        // Permita que os jogadores selecionem as localizações das bandeiras
        System.out.print("Jogador 1 - Selecione a localização da bandeira (1-" + numLocations + "): ");
        int player1Flag = scanner.nextInt();

        System.out.print("Jogador 2 - Selecione a localização da bandeira (1-" + numLocations + "): ");
        int player2Flag = scanner.nextInt();

        gameMap.selectFlagLocations(player1Flag, player2Flag);

        // Allow players to specify the number of bots
        System.out.print("Player 1 - Specify the number of bots: ");
        int numBotsPlayer1 = scanner.nextInt();
        gameMap.getPlayer1().setNumBots(numBotsPlayer1);

        System.out.print("Player 2 - Specify the number of bots: ");
        int numBotsPlayer2 = scanner.nextInt();
        gameMap.getPlayer2().setNumBots(numBotsPlayer2);

        // Start the game, deciding randomly which player begins and placing bots at flag locations
        gameMap.startGame();


        while (gameMap.getCurrentRound() != 0) {

            gameMap.playRound();

            Boolean victoryCheck = gameMap.checkVictoryCondition();

            if (victoryCheck) {
                System.out.println("MAN GANHOU");
                break;
            }

            if (gameMap.getRoundsWithoutMove() >= 5) {
                System.out.println("Empate");
                break;
            }


        }

    }

    public void createNewMap() {


    }

    public void startMapCreation() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Map Creation!");

        // Ask the user to choose between creating a new map or importing an existing one
        System.out.println("Do you want to create a new map or import an existing one?");
        System.out.println("1. Create a new map");
        System.out.println("2. Import an existing map");

        int choice = getUserChoice(scanner, 1, 2);

        if (choice == 1) {
            // User chose to create a new map
            createNewMap();
        } else {
            // User chose to import an existing map
            //importExistingMap();
        }

        // Close the scanner
        scanner.close();
    }

    private static int getUserChoice(Scanner scanner, int min, int max) {
        int userChoice;

        do {
            System.out.print("Enter your choice (" + min + "-" + max + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print("Enter your choice (" + min + "-" + max + "): ");
                scanner.next(); // Consume the invalid input
            }
            userChoice = scanner.nextInt();

            if (userChoice < min || userChoice > max) {
                System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
            }
        } while (userChoice < min || userChoice > max);

        return userChoice;
    }

    private static int getIntegerInput(String prompt, int min, int max) {
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


    private static boolean getBooleanInput(String prompt) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(prompt);
        while (!scanner.hasNextBoolean()) {
            System.out.println("Please enter 'true' or 'false'.");
            System.out.print(prompt);
            scanner.next(); // Consume the invalid input
        }

        return scanner.nextBoolean();
    }

    private static double getDoubleInput(String prompt, double min, double max) {
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