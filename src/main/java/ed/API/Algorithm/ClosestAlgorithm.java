package ed.API.Algorithm;

import ed.API.Bot.Bot;
import ed.API.Game.EntitiesLocation;
import ed.API.Player.Player;
import ed.Utils.Graph.Graph;
import ed.Utils.List.UnorderedArrayList;

import java.util.Iterator;


public class ClosestAlgorithm implements Algorithm {



    private final String name;

    public ClosestAlgorithm() {
        this.name = "ClosestAlgorithm";

    }

    public String getName() {
        return name;
    }

    /**
     * Moves the given bot to the adjacent vertex with the closest ID based on the provided graph.
     * The algorithm aims to choose the vertex with the closest ID to the current location of the bot.
     * If the graph does not contain any adjacent vertices, the bot remains in its current location.
     *
     * @param bot The bot to be moved.
     * @param graph The graph containing vertices and edges.
     * @param otherPlayerLocation The location of the other player, which may influence the bot's decision.
     * @return The new location for the bot after the move.
     */
    @Override
    public EntitiesLocation move(Bot bot, Graph<EntitiesLocation> graph, EntitiesLocation otherPlayerLocation) {
        // Get the ID of the adjacent vertex with the closest ID
        int idVertex = graph.getAdjacentClosestVertices(bot.getLocation());

        // Iterate through the vertices to find the one with the specified ID
        Iterator<EntitiesLocation> vertices = graph.getVertices();
        while (vertices.hasNext()) {
            EntitiesLocation currentVertex = vertices.next();

            // Assuming getId() returns the ID of the vertex
            if (currentVertex.getId() == idVertex) {
                return currentVertex; // Found the vertex with the specified ID
            }
        }

        // Return the current location if the specified ID is not found in the graph
        return bot.getLocation();
    }
}