package ed.API.Player;

import ed.API.Algorithm.*;
import ed.API.Bot.Bot;

import ed.Utils.List.UnorderedArrayList;
import ed.Utils.List.ArrayList;

import java.util.Scanner;

public class Player {
    private final UnorderedArrayList<Bot> bots;
    private final UnorderedArrayList<Algorithm> availableAlgorithms;

    public Player() {
        this.bots = new UnorderedArrayList<>();
        this.availableAlgorithms = new UnorderedArrayList<Algorithm>();
        this.availableAlgorithms.addToRear(new RandomMovementAlgorithm());
        this.availableAlgorithms.addToRear(new ShortestPathAlgorithm());
    }

    public ArrayList<Bot> getBots() {
        return bots;
    }

    public ArrayList<Algorithm> getAvailableAlgorithms() {
        return availableAlgorithms;
    }


    public void setNumBots(int numBots) {

        Scanner scanner = new Scanner(System.in);
        UnorderedArrayList<Algorithm> selectedAlgorithms = new UnorderedArrayList<>();

        for (int i = 0; i < numBots; i++) {
            Bot bot = new Bot();

            System.out.println("Choose an algorithm for Bot " + (i + 1) + ":");
            System.out.println("1. Shortest Path");
            System.out.println("2. Random Movement");
            System.out.println("3. Teleport, esta é a tatica dos deuses");
            System.out.println("4. Test");


            int selectedAlgorithmIndex;
            do {
                System.out.print("Enter the number of the desired algorithm: ");
                selectedAlgorithmIndex = scanner.nextInt();
            } while (selectedAlgorithmIndex < 1 || selectedAlgorithmIndex > 4);

            Algorithm selectedAlgorithm = switch (selectedAlgorithmIndex) {
                case 1 -> new ShortestPathAlgorithm(); // Replace with your actual ShortestPathAlgorithm class
                case 2 -> new RandomMovementAlgorithm(); // Replace with your actual RandomMovementAlgorithm class
                case 3 -> new TeleportAlgorithm(); // Replace with your actual LeastWeightAlgorithm class
                case 4 -> new ClosestAlgorithm(); // Replace with your actual LeastWeightAlgorithm class

                default -> throw new IllegalArgumentException("Invalid algorithm index");
            };

            selectedAlgorithms.addToRear(selectedAlgorithm);
            bot.setAlgorithm(selectedAlgorithm);
            bots.addToRear(bot);


        }
    }
}