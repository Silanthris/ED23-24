package ed.API.Algorithm;

import ed.API.Bot.Bot;
import ed.API.Game.EntitiesLocation;
import ed.Utils.Graph.Graph;

import java.util.Iterator;

public class ShortestPathAlgorithm implements Algorithm {
    @Override
    public EntitiesLocation move(Bot bot, Graph<EntitiesLocation> graph, EntitiesLocation otherPlayerLocation) {

        Iterator<EntitiesLocation> iterator = graph.iteratorShortestPath(bot.getLocation(), otherPlayerLocation);

        if(!iterator.hasNext()) return null;

        iterator.next(); //skip his location

        return iterator.next();
    }


}