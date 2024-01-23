package ed.API.Algorithm;

import ed.API.Bot.Bot;
import ed.API.Game.EntitiesLocation;
import ed.Utils.Graph.Graph;
import ed.Utils.List.UnorderedArrayList;

import java.util.Iterator;


public class ClosestAlgorithm implements Algorithm {

    @Override
    public EntitiesLocation move(Bot bot, Graph<EntitiesLocation> graph, EntitiesLocation otherPlayerLocation) {


        int idVertex = graph.getAdjacentClosestVertices(bot.getLocation());
        System.out.println(idVertex);

        Iterator<EntitiesLocation> vertices = graph.getVertices();

        while (vertices.hasNext()) {
            EntitiesLocation currentVertex = vertices.next();

            // Assuming getId() returns the ID of the vertex
            if (currentVertex.getId() == idVertex) {
                return currentVertex; // Found the vertex with the specified ID
            }
        }

        return bot.getLocation();


    }
}