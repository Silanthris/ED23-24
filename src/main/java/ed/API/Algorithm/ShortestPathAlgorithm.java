package ed.API.Algorithm;

import ed.API.Bot.Bot;
import ed.API.Game.EntitiesLocation;
import ed.Utils.Graph.Graph;

import java.util.Iterator;

public class ShortestPathAlgorithm implements Algorithm {


    private final String name;

    public ShortestPathAlgorithm() {
        this.name = "ShortestPathAlgorithm";

    }

    public String getName() {
        return name;
    }


    /**
     * Moves the given bot towards the specified destination using the shortest path algorithm based on the provided graph.
     * The algorithm calculates the shortest path from the current location of the bot to the destination (excluding the
     * current location) and returns the next location along the calculated shortest path. If the calculated shortest path
     * is not feasible or there is an issue with the iterator, null is returned, indicating that the bot stays in its
     * current location.
     *
     * @param bot The bot to be moved.
     * @param graph The graph containing vertices and edges.
     * @param otherPlayerLocation The destination location towards which the bot moves.
     * @return The new location for the bot after the move, or null if the bot stays in its current location.
     */
    @Override
    public EntitiesLocation move(Bot bot, Graph<EntitiesLocation> graph, EntitiesLocation otherPlayerLocation) {
        // Calculate the shortest path from the current location to the destination
        Iterator<EntitiesLocation> iterator = graph.iteratorShortestPath(bot.getLocation(), otherPlayerLocation);

        // Skip the current position
        iterator.next();

        // Return null if the calculated shortest path is not feasible or there is an issue with the iterator
        if (!iterator.hasNext()) {
            return null;
        }

        // Return the next location along the calculated shortest path
        return iterator.next();
    }


}