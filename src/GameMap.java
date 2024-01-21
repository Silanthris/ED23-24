import java.util.Random;

class GameMap {

    private Player player1;
    private Player player2;
    // ... (other code)

    Graph graph;
    boolean bidirectional;
    double density;

    private int currentRound;
    private Player currentPlayer;

    public GameMap(boolean bidirectional, double density) {
        this.graph = new Graph();
        this.bidirectional = bidirectional;
        this.density = density;
        this.player1 = new Player();
        this.player2 = new Player();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void startGame() {
        Random random = new Random();
        currentRound = 1;
        currentPlayer = random.nextBoolean() ? player1 : player2; // Randomly decide the starting player
    }

    public void playRound() {
        System.out.println("Round " + currentRound + ": Player " + (currentPlayer == player1 ? 1 : 2) + "'s turn");

        for (Bot bot : currentPlayer.bots) {
            // Movimenta o bot de acordo com a lógica específica (não fornecida)
            bot.getAlgorithm().move(bot);
        }

        // Alternar para o próximo jogador na próxima ronda
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        currentRound++;
    }

    private void moveBotsToFlag(Player player) {
        Location flagLocation = findPlayerFlagLocation(player);

        for (Bot bot : player.bots) {
            bot.setLocation(flagLocation);
        }
    }

    private Location findPlayerFlagLocation(Player player) {
        for (Location location : graph.locations.values()) {
            if ((player == player1 && location.isPlayer1Flag) || (player == player2 && location.isPlayer2Flag)) {
                return location;
            }
        }
        return null; // Handle the case where the flag location is not found
    }

    public void selectFlagLocations(int player1Flag, int player2Flag) {
        Location player1FlagLocation = graph.locations.get(player1Flag);
        Location player2FlagLocation = graph.locations.get(player2Flag);

        if (player1FlagLocation != null && player2FlagLocation != null) {
            // Define as localizações das bandeiras para os jogadores
            player1FlagLocation.isPlayer1Flag = true;
            player2FlagLocation.isPlayer2Flag = true;
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
            Location location = new Location(i, x, y);
            graph.addLocation(location);
        }

        // Generate edges with distances based on density
        for (int i = 1; i <= numLocations; i++) {
            for (int j = i + 1; j <= numLocations; j++) {
                if (random.nextDouble() < density) {
                    Location source = graph.locations.get(i);
                    Location destination = graph.locations.get(j);
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