package ed.API.Bot;

import ed.API.Algorithm.Algorithm;
import ed.API.Game.EntitiesLocation;

public class Bot {
    private EntitiesLocation location;
    private Algorithm algorithm;

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setLocation(EntitiesLocation location) {
        this.location = location;
    }

    public EntitiesLocation getLocation() {
        return location;
    }

    // Implement other bot-related logic
}
