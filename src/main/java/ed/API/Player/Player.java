package ed.API.Player;

import ed.API.Algorithm.*;
import ed.API.Bot.Bot;

import ed.Utils.List.UnorderedArrayList;
import ed.Utils.List.ArrayList;

import java.util.Iterator;
import java.util.Scanner;

public class Player implements IPlayer {
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


    /**
     * Sets the number of bots and their corresponding algorithms based on user input.
     * Allows the user to choose an algorithm for each bot from a predefined list.
     * The selected algorithms are associated with the respective bots.
     *
     * @param numBots The number of bots to be created and associated with algorithms.
     * @throws IllegalArgumentException if the selected algorithm index is invalid.
     */
    public void setNumBots2(int numBots) {
        Scanner scanner = new Scanner(System.in);
        UnorderedArrayList<Algorithm> selectedAlgorithms = new UnorderedArrayList<>();

        for (int i = 0; i < numBots; i++) {
            Bot bot = new Bot();

            System.out.println("Choose an algorithm for Bot " + (i + 1) + ":");
            System.out.println("1. Shortest Path");
            System.out.println("2. Random Movement");
            System.out.println("3. Teleport, esta é a tatica dos deuses");
            System.out.println("4. Closest Vertex");

            int selectedAlgorithmIndex;
            do {
                System.out.print("Enter the number of the desired algorithm: ");
                selectedAlgorithmIndex = scanner.nextInt();
            } while (selectedAlgorithmIndex < 1 || selectedAlgorithmIndex > 4);

            Algorithm selectedAlgorithm = switch (selectedAlgorithmIndex) {
                case 1 -> new ShortestPathAlgorithm(); // Replace with your actual ShortestPathAlgorithm class
                case 2 -> new RandomMovementAlgorithm(); // Replace with your actual RandomMovementAlgorithm class
                case 3 -> new TeleportAlgorithm(); // Replace with your actual TeleportAlgorithm class
                case 4 -> new ClosestAlgorithm(); // Replace with your actual TestAlgorithm class

                default -> throw new IllegalArgumentException("Invalid algorithm index");
            };

            selectedAlgorithms.addToRear(selectedAlgorithm);
            bot.setAlgorithm(selectedAlgorithm);
            bots.addToRear(bot);
        }
    }

    public void setNumBots(int numBots) {
        Scanner scanner = new Scanner(System.in);
        UnorderedArrayList<Algorithm> availableAlgorithms = new UnorderedArrayList<>();
        availableAlgorithms.addToRear(new ShortestPathAlgorithm());
        availableAlgorithms.addToRear(new RandomMovementAlgorithm());
        //availableAlgorithms.addToRear(new TeleportAlgorithm());
        availableAlgorithms.addToRear(new ClosestAlgorithm());

        int botIndex = 1;

        for (int i = 0; i < numBots; i++) {
            Bot bot = new Bot();

            System.out.println("Choose an algorithm for Bot " + (i + 1) + ":");
            printAvailableAlgorithms(availableAlgorithms);

            Algorithm selectedAlgorithm = null;
            int selectedIndex = 0;
            int selectedAlgorithmIndex = 0;

            do {
                System.out.print("Enter the number of the desired algorithm: ");
                selectedAlgorithmIndex = scanner.nextInt();

                if(selectedAlgorithmIndex > availableAlgorithms.size()){
                    System.out.print("Algorithm doesn't exist");
                } else {
                    // Find the selected algorithm using the iterator
                    Iterator<Algorithm> iterator = availableAlgorithms.iterator();
                    for (int j = 1; j <= selectedAlgorithmIndex; j++) {
                        selectedAlgorithm = iterator.next();
                        selectedIndex = j;
                    }
                }


            } while (selectedIndex != selectedAlgorithmIndex);


            // Remove the selected algorithm from the list


            bot.setAlgorithm(selectedAlgorithm);
            availableAlgorithms.remove(selectedAlgorithm);
            bots.addToRear(bot);
            botIndex++;

            if(botIndex > 3){
                availableAlgorithms.addToRear(new ShortestPathAlgorithm());
                availableAlgorithms.addToRear(new RandomMovementAlgorithm());
                //availableAlgorithms.addToRear(new TeleportAlgorithm());
                availableAlgorithms.addToRear(new ClosestAlgorithm());
                botIndex = 1;
            }


        }
    }

    private void printAvailableAlgorithms(UnorderedArrayList<Algorithm> algorithms) {
        Iterator<Algorithm> iterator = algorithms.iterator();
        int index = 1;

        while (iterator.hasNext()) {
            Algorithm current = iterator.next();

            System.out.println(index + ". " + current.getName());
            index++;
        }
    }


}