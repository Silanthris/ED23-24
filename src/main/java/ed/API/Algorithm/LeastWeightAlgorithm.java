package ed.API.Algorithm;

import ed.API.Bot.Bot;
import ed.API.Game.EntitiesLocation;
import ed.Utils.Graph.Graph;

import java.util.Iterator;

public class LeastWeightAlgorithm implements Algorithm {
    @Override
    public EntitiesLocation move(Bot bot, Graph<EntitiesLocation> graph, EntitiesLocation otherPlayerLocation) {

        Iterator<EntitiesLocation> iterator = graph.iteratorLeastWeightPath(bot.getLocation(), otherPlayerLocation);

        if(!iterator.hasNext()) return null;

        iterator.next(); //skip his location

        if(!iterator.hasNext()) return null;

        return iterator.next();
    }


}