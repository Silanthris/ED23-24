public class MapGenerator {
    public static void main(String[] args) {
        // Example usage
        int numLocations = 10;
        boolean bidirectional = true;
        double density = 0.5;

        GameMap gameMap = new GameMap(bidirectional, density);
        gameMap.generateMap(numLocations);

        // Access the graph to retrieve locations and edges
        Graph graph = gameMap.graph;

        for (Location location : graph.locations.values()) {
            System.out.println("Location " + location.id + ": (" + location.x + ", " + location.y + ")");
        }

        for (Edge edge : graph.edges) {
            System.out.println("Edge: " + edge.source.id + " -> " + edge.destination.id);
        }
    }
}