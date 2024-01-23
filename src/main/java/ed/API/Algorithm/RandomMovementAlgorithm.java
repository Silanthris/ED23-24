package ed.API.Algorithm;

import ed.API.Bot.Bot;
import ed.API.Game.EntitiesLocation;
import ed.Utils.Graph.Graph;
import ed.Utils.List.UnorderedArrayList;

import java.util.Iterator;


public class RandomMovementAlgorithm implements Algorithm {


    private final String name;

    public RandomMovementAlgorithm() {
        this.name = "RandomMovementAlgorithm";

    }

    public String getName() {
        return name;
    }

    /**
     * Moves the given bot to an unvisited adjacent vertex using the shortest path algorithm based on the provided graph.
     * The algorithm selects the next unvisited vertex as the destination and calculates the shortest path from the
     * current location of the bot to the destination. The bot moves along the calculated shortest path, skipping the
     * current position. If there is no unvisited vertex or the calculated shortest path is not feasible, null is returned,
     * indicating that the bot stays in its current location.
     *
     * @param bot The bot to be moved.
     * @param graph The graph containing vertices and edges.
     * @param otherPlayerLocation The location of the other player, which may influence the bot's decision.
     * @return The new location for the bot after the move, or null if the bot stays in its current location.
     */
    @Override
    public EntitiesLocation move(Bot bot, Graph<EntitiesLocation> graph, EntitiesLocation otherPlayerLocation) {
        UnorderedArrayList<EntitiesLocation> placesVisited = bot.getVerticesVisited();
        Iterator<EntitiesLocation> itr = graph.getVertices();
        EntitiesLocation goToNext = null;

        // Find the next unvisited vertex
        while (itr.hasNext()) {
            EntitiesLocation current = itr.next();
            if (!placesVisited.contains(current)) {
                goToNext = current;
                break;
            }
        }

        // Return null if there is no unvisited vertex
        if (goToNext == null) {
            return null;
        }

        // Calculate the shortest path from the current location to the destination
        Iterator<EntitiesLocation> shortPath = graph.iteratorShortestPath(bot.getLocation(), goToNext);

        // Skip the current position
        if (shortPath.hasNext()) {
            shortPath.next();
        }

        // Return null if the calculated shortest path is not feasible
        if (!shortPath.hasNext()) {
            return null;
        }

        // Return the next location along the calculated shortest path
        return shortPath.next();
    }
}