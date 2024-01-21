import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
