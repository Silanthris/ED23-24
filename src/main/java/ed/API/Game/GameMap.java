package ed.API.Game;

import ed.API.Bot.Bot;
import ed.API.Edge.Edge;
import ed.API.Player.Player;
import pt.ipp.estg.data.structures.List.ArrayList;
import pt.ipp.estg.data.structures.List.LinkedList;

import java.util.Random;

public class GameMap {

    private Player player1;
    private Player player2;
    // ... (other code)

    private final Graph graph;
    private final boolean bidirectional;
    private final double density;

    private int currentRound;
    private Player currentPlayer;

    public GameMap(boolean bidirectional, double density) {
        this.graph = new Graph();
        this.bidirectional = bidirectional;
        this.density = density;
        this.player1 = new Player();
        this.player2 = new Player();
    }

    public Graph getGraph() {
        return graph;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public void startGame() {
        Random random = new Random();
        currentRound = 1;
        currentPlayer = random.nextBoolean() ? player1 : player2; // Randomly decide the starting player
        moveBotsToFlag(player1);
        moveBotsToFlag(player2);
    }

    public void printCurrentPlayerTurn() {
        System.out.println("Round " + currentRound + ": Player " + (currentPlayer == player1 ? 1 : 2) + "'s turn");
    }

    public void playRound() {
        System.out.println("Round " + currentRound + ": Player " + (currentPlayer == player1 ? 1 : 2) + "'s turn");
        int currentPlayerInt = (currentPlayer == player1 ? 1 : 2);

        int botIndex = 0;
        for (Bot bot : currentPlayer.getBots()) {


            EntitiesLocation initialPosition = bot.getLocation();
            System.out.println("Bot " + botIndex + " (Player " + ": Player " + currentPlayerInt + "'s turn" + ") is at: " +
                    "(" + initialPosition.getX() + ", " + initialPosition.getY() + "  " + initialPosition.getId() + ")");


            bot.getAlgorithm().move(bot, graph);

            EntitiesLocation newPosition = bot.getLocation();
            System.out.println("Bot " + botIndex + " (Player " + ": Player " + currentPlayerInt + "'s turn" + ") moved to: " +
                    "(" + newPosition.getX() + ", " + newPosition.getY()  + "  " + newPosition.getId() + ")");


        }


        // Alternar para o próximo jogador na próxima ronda
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        currentRound++;
    }

    private void moveBotsToFlag(Player player) {
        EntitiesLocation flagLocation = findPlayerFlagLocation(player);

        for (Bot bot : player.getBots()) {
            bot.setLocation(flagLocation);
        }
    }

    public void printBotLocations() {
        System.out.println("Bot Locations:");

        for (int playerNum = 1; playerNum <= 2; playerNum++) {
            Player currentPlayer = (playerNum == 1) ? player1 : player2;

            int botIndex = 0;
            for (Bot bot : currentPlayer.getBots()) {
                EntitiesLocation location = bot.getLocation();
                System.out.println("Bot " + (botIndex + 1) + " (Player " + playerNum + "): " +
                        "(" + location.getX() + ", " + location.getY() + ")" + "  " + "Location" + location.getId());
                botIndex++;
            }
        }
    }

    private EntitiesLocation findPlayerFlagLocation(Player player) {
        for (EntitiesLocation location : graph.getLocations().getValues()) {
            if ((player == player1 && location.isPlayer1Flag()) || (player == player2 && location.isPlayer2Flag())) {
                return location;
            }
        }
        return null; // Handle the case where the flag location is not found
    }

    public void selectFlagLocations(int player1Flag, int player2Flag) {
        EntitiesLocation player1FlagLocation = graph.getLocations().get(player1Flag);
        EntitiesLocation player2FlagLocation = graph.getLocations().get(player2Flag);

        if (player1FlagLocation != null && player2FlagLocation != null) {
            // Define as localizações das bandeiras para os jogadores
            player1FlagLocation.setPlayer1Flag(true);
            player2FlagLocation.setPlayer2Flag(true);
        } else {
            System.out.println("Erro: As localizações das bandeiras não são válidas.");
        }
    }

    public void generateMap(int numLocations) {
        Random random = new Random();

        // Generate locations with random coordinates
        for (int i = 1; i <= numLocations; i++) {
            double x = random.nextDouble() * 100; // Adjust as needed
            double y = random.nextDouble() * 100; // Adjust as needed
            EntitiesLocation location = new EntitiesLocation(i, x, y);
            graph.addLocation(location);
        }

        // Generate edges with distances based on density
        for (int i = 1; i <= numLocations; i++) {
            for (int j = i + 1; j <= numLocations; j++) {
                if (random.nextDouble() < density) {
                    EntitiesLocation source = graph.getLocations().get(i);
                    EntitiesLocation destination = graph.getLocations().get(j);
                    double distance = random.nextDouble() * 15 + 1; // Distance between 1 and 15 km

                    Edge edge = new Edge(source, destination, distance);
                    graph.addEdge(edge);

                    if (bidirectional) {
                        // Add bidirectional edge
                        Edge reverseEdge = new Edge(destination, source, distance);
                        graph.addEdge(reverseEdge);
                    }
                }
            }
        }

    }

    // Add export functionality if needed
    // You can export the map in a format suitable for your game
}