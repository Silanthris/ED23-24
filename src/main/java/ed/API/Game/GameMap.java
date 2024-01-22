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

        /*
        // Assign random distances to edges (1 to 15 kilometers)
        for (int i = 0; i <  graph.size(); i++) {
            for (int j = 0; j <  graph.size(); j++) {
                if ( graph.adjMatrix[i][j]) {
                    // Assign a random distance between 1 and 15 kilometers
                    int distance = random.nextInt(15) + 1;
                    // You might want to store or display these distances as needed
                }
            }
        }
        */

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

        EntitiesLocation otherPlayerLocation ;

        System.out.println("Round " + currentRound + ": Player " + (currentPlayer == player1 ? 1 : 2) + "'s turn");
        int currentPlayerInt = (currentPlayer == player1 ? 1 : 2);

        int botIndex = 0;
        for (Bot bot : currentPlayer.getBots()) {

            otherPlayerLocation = findPlayerFlagLocation((currentPlayer == player1) ? player2 : player1 );

            if (otherPlayerLocation != null) {
                //System.out.println(otherPlayerLocation.getId());
            }

            bot.getAlgorithm().move(bot, graph, otherPlayerLocation);


        }


        // Alternar para o próximo jogador na próxima ronda
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        currentRound++;
    }

/*
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

 */

    /*
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

     */
/*
    private void moveBotsToFlag(Player player) {
        EntitiesLocation flagLocation = findPlayerFlagLocation(player);

        for (Bot bot : player.getBots()) {
            bot.setLocation(flagLocation);
        }
    }


 */

    /*
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

     */
/*
    private EntitiesLocation findPlayerFlagLocation(Player player) {
        for (EntitiesLocation location : graph.getLocations().getValues()) {
            if ((player == player1 && location.isPlayer1Flag()) || (player == player2 && location.isPlayer2Flag())) {
                return location;
            }
        }
        return null; // Handle the case where the flag location is not found
    }

 */
/*
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
    */

/*
    public void generateMap(int numLocations, boolean bidirectional, double edgeDensity) {
        // Clear existing graph
        graph.numVertices = 0;
        graph.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        graph.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);

        // Generate vertices
        for (int i = 0; i < numLocations; i++) {
            graph.addVertex((T) ("Location " + (i + 1)));
        }

        // Generate edges based on edgeDensity
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (Math.random() < edgeDensity) {
                    addEdge(vertices[i], vertices[j]);
                    if (bidirectional) {
                        addEdge(vertices[j], vertices[i]);
                    }
                }
            }
        }
    }
*/
    // Add export functionality if needed
    // You can export the map in a format suitable for your game
}