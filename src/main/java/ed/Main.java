package ed;

import ed.API.Files.Export;
import ed.API.Files.Import;
import ed.API.Game.EntitiesLocation;
import ed.API.Game.GameMap;
import ed.Utils.Graph.Graph;

import java.io.IOException;
import java.util.Scanner;

import static ed.Utils.Inputs.getBooleanInput.getBooleanInput;
import static ed.Utils.Inputs.getDoubleInput.getDoubleInput;
import static ed.Utils.Inputs.getIntegerInput.getIntegerInput;

public class Main {

    private static boolean appRunning = true;
    private static boolean gameRunning = true;

    public static void main(String[] args) throws IOException {


        Scanner scanner = new Scanner(System.in);
        //double densityCalculated = ((numLocations * (numLocations - 1)) * 0.5) / 100;

        int numLocations = 0;
        boolean bidirectional = true;
        double density = 1;

        while (appRunning) {


            System.out.println("Welcome to Map Creation!");

            // Ask the user to choose between creating a new map or importing an existing one
            System.out.println("Do you want to create a new map or import an existing one?");
            System.out.println("1. Create a new map");
            System.out.println("2. Import an existing map");
            System.out.println("3. Exit Game");

            int choice = getUserChoice(scanner);
            GameMap gameMap = new GameMap(bidirectional, density);

            if (choice == 1) {

                numLocations = getIntegerInput("Enter the number of locations (max 45): ", 1, 45);
                bidirectional = getBooleanInput("Should paths be bidirectional? (true/false): ");
                density = getDoubleInput("Enter the density (max 1.0): ", 0.0, 1.0);


                gameMap.generateRandomGraph(numLocations, bidirectional);
                //gameMap.generateRandomGraphWithRules(numLocations, bidirectional,density);

                gameMap.printGraph();
                //gameMap.printMatrix(gameMap.getGraph().getAdjMatrix(),numLocations);
                //gameMap.printMatrixWeight(gameMap.getGraph().getAdjWeightMatrix(),numLocations);

            } else if(choice == 2) {

                Import fileImport = new Import();

                Graph<EntitiesLocation> graphImport = fileImport.importGame();

                gameMap.setGraph(graphImport);
                numLocations = graphImport.size();
            } else {

                appRunning = false;

            }


            // Permita que os jogadores selecionem as localizações das bandeiras
            System.out.print("Jogador 1 - Selecione a localização da bandeira (1-" + numLocations + "): ");
            int player1Flag = scanner.nextInt();

            System.out.print("Jogador 2 - Selecione a localização da bandeira (1-" + numLocations + "): ");
            int player2Flag = scanner.nextInt();

            gameMap.selectFlagLocations(player1Flag - 1, player2Flag - 1);

            // Allow players to specify the number of bots
            System.out.print("Player 1 - Specify the number of bots: ");
            int numBotsPlayer1 = scanner.nextInt();
            gameMap.getPlayer1().setNumBots(numBotsPlayer1);

            System.out.print("Player 2 - Specify the number of bots: ");
            int numBotsPlayer2 = scanner.nextInt();
            gameMap.getPlayer2().setNumBots(numBotsPlayer2);

            // Start the game, deciding randomly which player begins and placing bots at flag locations
            gameMap.startGame();


            while (gameRunning) {

                gameMap.playRound();

                boolean victoryCheck = gameMap.checkVictoryCondition();

                if (victoryCheck) {
                    System.out.println("Vitoria do Player corrente");
                    showMenuAndProcessChoice(gameMap, density, bidirectional);
                    gameMap.setCurrentRound(0);
                }

                if (gameMap.getRoundsWithoutMove() >= 5) {
                    showMenuAndProcessChoice(gameMap, density, bidirectional);
                    System.out.println("Empate");
                    gameMap.setCurrentRound(0);
                }


            }


        }


    }

    /**
     * Displays a menu with options to the user and processes their choice.
     * The menu includes the following options:
     * 1. Export Map
     * 2. End Game
     * The method continuously prompts the user to choose an option until a valid option is selected.
     * Invalid inputs (non-numeric or out-of-range) are handled, and the user is prompted to enter a valid option.
     * <p>
     * Upon choosing an option:
     * - If the user chooses 1, the method calls the exportMap() method (you may uncomment the method call).
     * - If the user chooses 2, the method calls the endGame() method and returns from the method or loop depending on your needs.
     * - If the user enters an invalid choice, an error message is displayed, and the user is prompted to enter 1 or 2 again.
     */
    public static void showMenuAndProcessChoice(GameMap gameMap, double density, boolean bidirectional) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Print menu
            System.out.println("1. Export Map");
            System.out.println("2. Play another Round");
            System.out.println("3. End Game");
            System.out.print("Choose an option (1 or 3): ");

            // Read user input
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue; // Continue the loop to ask again
            }

            // Process user choice
            switch (choice) {
                case 1:
                    Export fileExport = new Export(gameMap.getGraph(), density, bidirectional);
                    fileExport.exportGame();
                    System.out.println("Game has been exported");
                    break;
                case 2:
                    gameRunning = false;
                    return; // Exit the method or loop depending on your needs
                case 3:
                    appRunning = false;
                    return; // Exit the method or loop depending on your needs
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 3.");
            }
        }
    }


    /**
     * Prompts the user to enter a numeric choice within the specified range.
     * Continuously asks for input until a valid numeric choice within the specified range is provided.
     * Handles invalid inputs, such as non-numeric entries or choices outside the specified range.
     *
     * @param scanner The Scanner object used to read user input.
     * @return The user's valid numeric choice within the specified range.
     * @throws java.util.InputMismatchException If the user enters a non-numeric value.
     */
    private static int getUserChoice(Scanner scanner) {
        int userChoice;

        do {
            System.out.print("Enter your choice (" + 1 + "-" + 2 + "-"+ 3 + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print("Enter your choice (" + 1 + "-" + 2 + "-" + 3 + "): ");
                scanner.next(); // Consume the invalid input
            }
            userChoice = scanner.nextInt();

            if (userChoice < 1 || userChoice > 3) {
                System.out.println("Invalid choice. Please enter a number between " + 1 + " and " + 2 + "or" + 3 + ".");
            }
        } while (userChoice < 1 || userChoice > 3);

        return userChoice;
    }


}