package ed.API.Edge;

import ed.API.Game.EntitiesLocation;

public class Edge {
    private final EntitiesLocation source;
    private final EntitiesLocation destination;
    private final double distance;

    public Edge(EntitiesLocation source, EntitiesLocation destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public EntitiesLocation getSource(){
        return source;
    }

    public EntitiesLocation getDestination(){
        return destination;
    }

    public double getDistance() {
        return distance;
    }
}