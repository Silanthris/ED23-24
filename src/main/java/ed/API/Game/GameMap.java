package ed.API.Game;

import ed.API.Bot.Bot;
import ed.API.Player.Player;

import java.util.Iterator;
import java.util.Random;

import ed.Utils.Graph.Graph;

public class GameMap implements IGameMap {

    private Player player1;
    private Player player2;
    private Graph<EntitiesLocation> graph;
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

    public void setGraph(Graph<EntitiesLocation> newGraph) {
        this.graph = newGraph;
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


    /**
     * Generates a random graph with the specified number of locations, bidirectionality, and density, following specific rules.
     * Random locations are created with random coordinates, and edges between locations are added based on the given density.
     * Additionally, edges are only added between locations with an ID difference of either 5 or -5.
     *
     * @param numLocations  The number of locations (vertices) to generate in the graph.
     * @param bidirectional Flag indicating whether the edges should be bidirectional.
     * @param density       The connectivity density, indicating the probability of adding an edge between two locations.
     *                      Must be in the range [0, 1].
     * @throws IllegalArgumentException if the input parameters are invalid (numLocations <= 0, density < 0, or density > 1).
     */
    public void generateRandomGraphWithRules(int numLocations, boolean bidirectional, double density) {
        // Validate input parameters
        if (numLocations <= 0 || density < 0 || density > 1) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        Random random = new Random();
        for (int i = 0; i < numLocations; i++) {
            graph.addVertex(new EntitiesLocation(i, random.nextDouble(), random.nextDouble()));
        }

        Iterator<EntitiesLocation> resultList = graph.getVertices();
        // Generate random edges based on edge density and specific rules
        while (resultList.hasNext()) {
            EntitiesLocation current = resultList.next();
            Iterator<EntitiesLocation> resultListTemporary = graph.getVertices();

            while (resultListTemporary.hasNext()) {
                EntitiesLocation currentTemporary = resultListTemporary.next();

                // Ensure the difference in IDs is either 5 or -5
                int idDifference = Math.abs(current.getId() - currentTemporary.getId());
                if (idDifference == 5 && random.nextDouble() <= density) {
                    // Assign a random weight between 1 and 15 kilometers
                    double randomWeight = 1 + random.nextDouble() * 14;

                    graph.addEdge(current, currentTemporary, bidirectional, density, randomWeight);
                }
            }
        }
    }

    /**
     * Generates a random graph with the specified number of locations, connectivity density, and bidirectionality.
     * Random locations are created with random coordinates, and edges between locations are randomly added based on density.
     *
     * @param numLocations  The number of locations (vertices) to generate in the graph.
     * @param bidirectional Flag indicating whether the edges should be bidirectional.
     * @throws IllegalArgumentException if the input parameters are invalid (numLocations <= 0, density < 0, or density > 1).
     */
    public void generateRandomGraph(int numLocations, boolean bidirectional) {
        // Validate input parameters
        if (numLocations <= 0 || density < 0 || density > 1) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

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

                // Add a random edge based on density and check for self-loops
                if (random.nextDouble() <= density && current.getId() != currentTemporary.getId()) {
                    Random randomWeight = new Random();
                    graph.addEdge(current, currentTemporary, bidirectional, density, randomWeight.nextDouble(15));
                }
            }
        }
    }

    /**
     * Prints the vertices and edges (adjacency matrix) of the game graph to the console.
     * Vertices are printed with their IDs and coordinates.
     * Edges are printed as connections between vertices, representing the adjacency matrix.
     */
    public void printGraph() {
        System.out.println("Vertices:");
        Iterator<EntitiesLocation> verticesIterator = graph.getVertices();

        while (verticesIterator.hasNext()) {
            EntitiesLocation vertex = verticesIterator.next();
            System.out.println("Vertex " + (vertex.getId() + 1) + ": (" + vertex.getX() + ", " + vertex.getY() + ")");
        }

        System.out.println("\nEdges (Adjacency Matrix):");

        Iterator<EntitiesLocation> verticesIterator2 = graph.getVertices();

        while (verticesIterator2.hasNext()) {
            EntitiesLocation current = verticesIterator2.next();
            Iterator<EntitiesLocation> adjMatrix = graph.getAdjacentVertices(current);

            while (adjMatrix.hasNext()) {
                EntitiesLocation adjacent = adjMatrix.next();
                System.out.println("Edge: (" + (current.getId()+ 1) + ") -> (" + (adjacent.getId() + 1) + ")");
            }
        }
    }

    public void printMatrix(boolean[][] matrix, int numlocations) {

        System.out.print("[");
        for (int i = 0; i < numlocations; i++) {
            System.out.print("[");
            for (int j = 0; j < numlocations; j++) {
                if (j + 1 == numlocations) {
                    System.out.print(matrix[i][j]);
                } else {
                    System.out.print(matrix[i][j] + ",");
                }

            }
            System.out.print("]");  // Move to the next line after printing a row
            System.out.println();
        }
        System.out.print("]");
    }

    public void printMatrixWeight(double[][] matrix, int numlocations) {

        System.out.print("[");
        for (int i = 0; i < numlocations; i++) {
            System.out.print("[");
            for (int j = 0; j < numlocations; j++) {
                if (j + 1 == numlocations) {
                    System.out.print(matrix[i][j]);
                } else {
                    System.out.print(matrix[i][j] + ",");
                }

            }
            System.out.print("]");  // Move to the next line after printing a row
            System.out.println();
        }
        System.out.print("]");
    }


    /**
     * Sets flag locations for both players in the game graph based on the specified vertex IDs.
     * The player1Flag parameter designates the vertex ID for Player 1's flag location,
     * and the player2Flag parameter designates the vertex ID for Player 2's flag location.
     *
     * @param player1Flag The vertex ID representing Player 1's flag location.
     * @param player2Flag The vertex ID representing Player 2's flag location.
     */
    public void selectFlagLocations(int player1Flag, int player2Flag) {
        Iterator<EntitiesLocation> verticesIterator = graph.getVertices();

        while (verticesIterator.hasNext()) {
            EntitiesLocation vertex = verticesIterator.next();

            // Set Player 1's flag location
            if (player1Flag == vertex.getId()) {
                vertex.setPlayer1Flag(true);
            }

            // Set Player 2's flag location
            if (player2Flag == vertex.getId()) {
                vertex.setPlayer2Flag(true);
            }
        }
    }

    /**
     * Initializes and starts a new game. Randomly decides the starting player and moves all bots to their respective flag locations.
     * This method is typically called at the beginning of a game to set up the initial game state.
     */
    public void startGame() {
        Random random = new Random();
        currentRound = 1;
        currentPlayer = random.nextBoolean() ? player1 : player2; // Randomly decide the starting player

        // Move all bots to their respective flag locations
        moveBotsToFlag(player1);
        moveBotsToFlag(player2);
    }

    /**
     * Moves all bots belonging to the specified player to the location of the player's flag.
     * If the player's flag location is not found, no movement is performed.
     *
     * @param player The player whose bots are to be moved to the flag location.
     */
    private void moveBotsToFlag(Player player) {
        EntitiesLocation flagLocation = findPlayerFlagLocation(player);

        // Move all bots belonging to the specified player to the flag location
        if (flagLocation != null) {
            for (Bot bot : player.getBots()) {
                bot.setLocation(flagLocation);
            }
        }
    }

    /**
     * Finds and returns the location of the flag belonging to the specified player in the game graph.
     *
     * @param player The player whose flag location is to be found.
     * @return The EntitiesLocation object representing the location of the player's flag, or {@code null}
     * if the flag location is not found in the game graph.
     */
    private EntitiesLocation findPlayerFlagLocation(Player player) {
        Iterator<EntitiesLocation> verticesIterator = graph.getVertices();

        while (verticesIterator.hasNext()) {
            EntitiesLocation vertex = verticesIterator.next();

            if ((player == player1 && vertex.isPlayer1Flag()) || (player == player2 && vertex.isPlayer2Flag())) {
                return vertex; // Found the location of the player's flag
            }
        }

        return null; // Handle the case where the flag location is not found
    }

    /**
     * Executes a round of the game where each bot belonging to the current player makes a move.
     * Bots attempt to move towards the other player's flag, and their movements are printed to the console.
     * If a bot's chosen move is occupied, the bot remains in its current position, and a message is printed.
     * The method also handles cases where a bot's move is null or all bots fail to move, incrementing a counter.
     * After each round, the current player is alternated, and the round counter is incremented.
     */
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
                System.out.println("Bot " + botIndex + " (Player " + currentPlayerInt + "'s turn) is at: " +
                        "(" + initialPosition.getX() + ", " + initialPosition.getY() + "  " + initialPosition.getId() + ")");

                if (!moveCheck) {
                    bot.addVerticeVisited(bot.getLocation());
                    bot.setLocation(move);

                    EntitiesLocation newPosition = bot.getLocation();
                    System.out.println("Bot " + botIndex + " (Player " + currentPlayerInt + "'s turn) moved to: " +
                            "(" + newPosition.getX() + ", " + newPosition.getY() + "  " + newPosition.getId() + ")");
                } else {
                    System.out.println("Bot " + botIndex + " Position occupied");
                    noMoveCounter++;
                }
            } else {
                System.out.println("Bot " + botIndex + " Move is null");
                noMoveCounter++;
                // Handle the case where the move is null
            }
        }

        if (noMoveCounter == currentPlayer.getBots().size()) {
            roundsWithoutMove++;
        }

        // Alternating to the next player in the next round
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        currentRound++;
    }

    /**
     * Checks for the victory condition in the game.
     * The victory condition is met if any bot owned by Player 1 reaches a location containing Player 2's flag,
     * or if any bot owned by Player 2 reaches a location containing Player 1's flag.
     *
     * @return {@code true} if the victory condition is met, {@code false} otherwise.
     */
    public boolean checkVictoryCondition() {
        // Check if any bot owned by Player 1 has reached a location containing Player 2's flag
        for (Bot bot : player1.getBots()) {
            if (bot.getLocation().isPlayer2Flag()) {
                return true; // Victory condition for Player 1
            }
        }

        // Check if any bot owned by Player 2 has reached a location containing Player 1's flag
        for (Bot bot : player2.getBots()) {
            if (bot.getLocation().isPlayer1Flag()) {
                return true; // Victory condition for Player 2
            }
        }

        return false; // No victory condition met
    }

    /**
     * Checks if there is a bot located at the specified location and returns whether it is eligible for movement.
     *
     * @param location The ID of the location to check for the presence of a bot.
     * @return {@code true} if a bot is present at the specified location and is eligible for movement,
     * {@code false} otherwise.
     */
    public boolean checkLocationForBot(int location) {
        // Check player1's bots
        for (Bot bot : player1.getBots()) {
            if (bot.getLocation().getId() == location) {
                if (bot.getLocation().isPlayer1Flag()) {
                    return false; // Bot is at the location, but it has player1's flag
                } else {
                    return true; // Bot is at the location and eligible for movement
                }
            }
        }

        // Check player2's bots
        for (Bot bot : player2.getBots()) {
            if (bot.getLocation().getId() == location) {
                if (bot.getLocation().isPlayer2Flag()) {
                    return false; // Bot is at the location, but it has player2's flag
                } else {
                    return true; // Bot is at the location and eligible for movement
                }
            }
        }

        return false; // No bot found at the specified location
    }

}