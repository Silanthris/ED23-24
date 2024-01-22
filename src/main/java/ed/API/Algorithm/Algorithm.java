package ed.API.Algorithm;

import ed.API.Bot.Bot;
import ed.API.Game.EntitiesLocation;
import ed.API.Game.GameMap;
import ed.Utils.Graph.Graph;


public interface Algorithm {
    // Define the methods required for algorithms
    void move(Bot bot, Graph<EntitiesLocation> graph, EntitiesLocation otherPlayerLocation);

}
