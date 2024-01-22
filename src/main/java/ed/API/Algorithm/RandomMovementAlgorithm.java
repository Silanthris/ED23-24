package ed.API.Algorithm;

import ed.API.Bot.Bot;
import ed.API.Game.EntitiesLocation;
import ed.Utils.Graph.Graph;
import ed.Utils.List.UnorderedArrayList;

import java.util.Iterator;


public class RandomMovementAlgorithm implements Algorithm {

    @Override
    public EntitiesLocation move(Bot bot, Graph<EntitiesLocation> graph, EntitiesLocation otherPlayerLocation) {

        UnorderedArrayList<EntitiesLocation> placesVisited = bot.getVerticesVisited();
        Iterator<EntitiesLocation> itr = graph.getVertices();
        EntitiesLocation goToNext = null;

        while (itr.hasNext()){
            EntitiesLocation current = itr.next();
            if(!placesVisited.contains(current)){
                goToNext = current;
                break;
            }
        }

        if(goToNext==null) return goToNext;

        Iterator<EntitiesLocation> shortPath = graph.iteratorShortestPath(bot.getLocation(), goToNext);

        if(!shortPath.hasNext()) return null;

        //skip current position
        shortPath.next();

        if(!shortPath.hasNext()) return null;

        return shortPath.next();

    }
}