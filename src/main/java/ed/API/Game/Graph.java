package ed.API.Game;

import ed.API.Edge.Edge;
import ed.Utils.Map.HashMap;
import ed.Utils.List.UnorderedArrayList;


public class Graph {
    private final HashMap<Integer, EntitiesLocation> locations;
    private final UnorderedArrayList<Edge> edges;

    public Graph() {
        this.locations = new HashMap<Integer, EntitiesLocation>();
        this.edges = new UnorderedArrayList<Edge>();
    }

    public HashMap<Integer, EntitiesLocation> getLocations(){
        return locations;
    }

    public UnorderedArrayList<Edge> getEdges() {
        return edges;
    }

    public void addLocation(EntitiesLocation location) {
        locations.put(location.getId(), location);
    }

    public void addEdge(Edge edge) {
        edges.addToRear(edge);
    }
}