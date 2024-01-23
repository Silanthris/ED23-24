package ed.API.Game;

import ed.API.Bot.Bot;
import ed.API.Player.Player;

import java.util.Iterator;
import java.util.Random;

import ed.Utils.Graph.Graph;

public class GameMap {

    private Player player1;
    private Player player2;
    private final Graph<EntitiesLocation> graph;
    private final boolean bidirectional;
    private final double density;
    private int currentRound;
    private int roundsWithoutMove;
    private Player currentPlayer;

    public GameMap(boolean bidirectional, double density) {
        this.graph = new Graph<>();
        this.bidirectional = bidirectional;
        this.density = density;
        this.player1 = new Player();
        this.player2 = new Player();
    }

    public Graph<EntitiesLocation> getGraph() {
        return graph;
    }
    public int getRoundsWithoutMove() {
        return roundsWithoutMove;
    }
    public void setRoundsWithoutMove(int roundsWithoutMove) {
        this.roundsWithoutMove = roundsWithoutMove;
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


    public void generateRandomGraph(int numLocations, boolean bidirectional) {
        // Validate input parameters
        if (numLocations <= 0 || density < 0 || density > 1) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        // Clear existing graph data
        /*
        this.numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new EntitiesLocation[DEFAULT_CAPACITY]);
        */

        // Generate random locations
        Random random = new Random();
        for (int i = 0; i < numLocations; i++) {
            graph.addVertex(new EntitiesLocation(i, random.nextDouble(), random.nextDouble()));
        }

        Iterator<EntitiesLocation> resultList = graph.getVertices();
        // Generate random edges based on edge density
        while (resultList.hasNext()) {

            EntitiesLocation current = resultList.next();

            Iterator<EntitiesLocation> resultListTemporary = graph.getVertices();

            while (resultListTemporary.hasNext()) {

                EntitiesLocation currentTemporary = resultListTemporary.next();

                if (random.nextDouble() <= density && current.getId() != currentTemporary.getId()) {

                    Random randomWeight = new Random();
                    randomWeight.nextDouble(15);

                    graph.addEdge(current, currentTemporary, bidirectional, density, randomWeight.nextDouble());

                }

            }

        }

    }

    public void printGraph() {
        System.out.println("Vertices:");
        Iterator<EntitiesLocation> verticesIterator = graph.getVertices();
        while (verticesIterator.hasNext()) {
            EntitiesLocation vertex = verticesIterator.next();
            System.out.println("Vertex " + vertex.getId() + ": (" + vertex.getX() + ", " + vertex.getY() + ")");
        }

        System.out.println("\nEdges (Adjacency Matrix):");

        Iterator<EntitiesLocation> verticesIterator2 = graph.getVertices();

        while (verticesIterator2.hasNext()) {

            EntitiesLocation current = verticesIterator2.next();

            Iterator<EntitiesLocation> adjMatrix = graph.getAdjacentVertices(current);

            while (adjMatrix.hasNext()) {

                System.out.println("Edge: (" + current.getId() + ") -> (" + adjMatrix.next().getId() + ")");

            }

        }

    }

    public void selectFlagLocations(int player1Flag, int player2Flag) {

        Iterator<EntitiesLocation> verticesIterator = graph.getVertices();
        while (verticesIterator.hasNext()) {
            EntitiesLocation vertex = verticesIterator.next();

            if (player1Flag == vertex.getId()) {
                vertex.setPlayer1Flag(true);
            }

            if (player2Flag == vertex.getId()) {
                vertex.setPlayer2Flag(true);
            }

        }

    }

    public void startGame() {
        Random random = new Random();
        currentRound = 1;
        currentPlayer = random.nextBoolean() ? player1 : player2; // Randomly decide the starting player
        moveBotsToFlag(player1);
        moveBotsToFlag(player2);
    }

    private void moveBotsToFlag(Player player) {
        EntitiesLocation flagLocation = findPlayerFlagLocation(player);

        for (Bot bot : player.getBots()) {
            bot.setLocation(flagLocation);
        }
    }

    private EntitiesLocation findPlayerFlagLocation(Player player) {

        Iterator<EntitiesLocation> verticesIterator = graph.getVertices();
        while (verticesIterator.hasNext()) {
            EntitiesLocation vertex = verticesIterator.next();

            if ((player == player1 && vertex.isPlayer1Flag()) || (player == player2 && vertex.isPlayer2Flag())) {
                return vertex;
            }

        }

        return null; // Handle the case where the flag location is not found

    }

    public void playRound() {

        EntitiesLocation otherPlayerLocation;

        System.out.println("Round " + currentRound + ": Player " + (currentPlayer == player1 ? 1 : 2) + "'s turn");
        int currentPlayerInt = (currentPlayer == player1 ? 1 : 2);
        int noMoveCounter = 0;

        int botIndex = 0;
        for (Bot bot : currentPlayer.getBots()) {


            botIndex++;

            otherPlayerLocation = findPlayerFlagLocation((currentPlayer == player1) ? player2 : player1);


            EntitiesLocation move = bot.getAlgorithm().move(bot, graph, otherPlayerLocation);


            if (move != null) {

                boolean moveCheck = checkLocationForBot(move.getId());

                EntitiesLocation initialPosition = bot.getLocation();
                System.out.println("Bot " + botIndex + " (Player " + ": Player " + currentPlayerInt + "'s turn" + ") is at: " +
                        "(" + initialPosition.getX() + ", " + initialPosition.getY() + "  " + initialPosition.getId() + ")");

                if (!moveCheck) {

                    bot.addVerticeVisited(bot.getLocation());

                    bot.setLocation(move);

                    EntitiesLocation newPosition = bot.getLocation();
                    System.out.println("Bot " + botIndex + " (Player " + ": Player " + currentPlayerInt + "'s turn" + ") moved to: " +
                            "(" + newPosition.getX() + ", " + newPosition.getY() + "  " + newPosition.getId() + ")");




                } else {
                    System.out.println("Bot"+ botIndex + " " + "Posição ocupada");
                    noMoveCounter++;
                }
            } else {
                System.out.println("Bot " + botIndex + " move is null");
                noMoveCounter++;
                // Handle the case where the move is null
            }




        }

        if(noMoveCounter == currentPlayer.getBots().size()){
            roundsWithoutMove++;
        }

        // Alternar para o próximo jogador na próxima ronda
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        currentRound++;
    }

    public boolean checkVictoryCondition() {

        for (Bot bot : player1.getBots()) {

            if (bot.getLocation().isPlayer2Flag()) {

                return true;

            }

        }

        for (Bot bot : player2.getBots()) {

            if (bot.getLocation().isPlayer1Flag()) {

                return true;

            }

        }


        return false;
    }

    public boolean checkLocationForBot(int location) {

        for (Bot bot : player1.getBots()) {

            if (bot.getLocation().getId() == location) {

                if (bot.getLocation().isPlayer1Flag()) {
                    return false;

                } else {
                    return true;
                }

            }

        }

        for (Bot bot : player2.getBots()) {

            if (bot.getLocation().getId() == location) {

                if (bot.getLocation().isPlayer2Flag()) {
                    return false;
                } else{
                    return true;
                }


            }

        }


        return false;
    }

}