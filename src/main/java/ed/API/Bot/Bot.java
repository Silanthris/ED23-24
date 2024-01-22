package ed.API.Bot;

import ed.API.Algorithm.Algorithm;
import ed.API.Game.EntitiesLocation;
import ed.Utils.List.UnorderedArrayList;

public class Bot {
    private EntitiesLocation location;
    private Algorithm algorithm;
    private UnorderedArrayList<EntitiesLocation> verticesVisited;

    public Bot(){
        verticesVisited = new UnorderedArrayList<>();
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setLocation(EntitiesLocation location) {
        this.location = location;
    }

    public EntitiesLocation getLocation() {return location;}

    public void addVerticeVisited(EntitiesLocation location){
        verticesVisited.addToRear(location);
    }

    public UnorderedArrayList<EntitiesLocation> getVerticesVisited(){
        return verticesVisited;
    }

    // Implement other bot-related logic
}
