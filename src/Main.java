import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class Location {
    int id;
    double x;
    double y;

    public Location(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}

class Edge {
    Location source;
    Location destination;

    public Edge(Location source, Location destination) {
        this.source = source;
        this.destination = destination;
    }
}

class Graph {
    Map<Integer, Location> locations;
    List<Edge> edges;

    public Graph() {
        this.locations = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    public void addLocation(Location location) {
        locations.put(location.id, location);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }
}

class GameMap {
    Graph graph;
    boolean bidirectional;
    double density;

    public GameMap(boolean bidirectional, double density) {
        this.graph = new Graph();
        this.bidirectional = bidirectional;
        this.density = density;
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

        // Generate edges based on density
        for (int i = 1; i <= numLocations; i++) {
            for (int j = i + 1; j <= numLocations; j++) {
                if (random.nextDouble() < density) {
                    Location source = graph.locations.get(i);
                    Location destination = graph.locations.get(j);

                    Edge edge = new Edge(source, destination);
                    graph.addEdge(edge);

                    if (bidirectional) {
                        // Add bidirectional edge
                        Edge reverseEdge = new Edge(destination, source);
                        graph.addEdge(reverseEdge);
                    }
                }
            }
        }
    }

    // Add export functionality if needed
    // You can export the map in a format suitable for your game
}


