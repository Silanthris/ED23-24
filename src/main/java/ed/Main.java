package ed;

import ed.API.Edge.Edge;
import ed.API.Game.EntitiesLocation;
import ed.API.Game.GameMap;
import ed.API.Game.Graph;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException{

        // Example usage
        int numLocations = 10;
        boolean bidirectional = true;
        double density = 0.5;

        GameMap gameMap = new GameMap(bidirectional, density);
        gameMap.generateMap(numLocations);

        // Access the graph to retrieve locations, edges, and distances
        Graph graph = gameMap.getGraph();

        for (EntitiesLocation location : graph.getLocations().getValues()) {
            System.out.println("Location " + location.getId() + ": (" + location.getX() + ", " + location.getY() + ")");
        }

        for (Edge edge : graph.getEdges()) {
            System.out.println("Edge: " + edge.getSource().getId() + " -> " + edge.getDestination().getId() + " (Distance: " + edge.getDistance() + " km)");
        }

        // Permita que os jogadores selecionem as localizações das bandeiras
        Scanner scanner = new Scanner(System.in);
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

        // ... (rest of the code for accessing the map, exporting, etc.)

        // Start the game, deciding randomly which player begins and placing bots at flag locations
        gameMap.startGame();
    }

}