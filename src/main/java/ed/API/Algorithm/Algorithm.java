package ed.API.Algorithm;

import ed.API.Bot.Bot;
import ed.API.Game.EntitiesLocation;
import ed.Utils.Graph.Graph;


public interface Algorithm {
    // Define the methods required for algorithms
    EntitiesLocation move(Bot bot, Graph<EntitiesLocation> graph, EntitiesLocation otherPlayerLocation);

}
