package ed.API.Files;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ed.API.Game.EntitiesLocation;
import ed.Utils.Graph.Graph;
import ed.Utils.List.UnorderedArrayList;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static ed.Utils.Files.File.getFileFromResources;

public class Import {

    private final Graph<EntitiesLocation> newGraph;

    public Import() {
        this.newGraph = new Graph<>();
    }

    public Graph<EntitiesLocation> importGame() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = getFileFromResources("gameFile.json");

        JsonNode root = mapper.readTree(file);

        extractGame(root);

        return newGraph;
    }

    private void extractGame(JsonNode node) {

        JsonNode graphJN = node.get("graph");

        if (Objects.isNull(graphJN)) throw new NullPointerException("Graph not found!");

        boolean bidirectional = graphJN.get("bidirectional").asBoolean();
        double density = graphJN.get("density").asDouble();

        JsonNode vertices = graphJN.get("vertices");
        JsonNode adjMatrix = graphJN.get("adjMatrix");
        JsonNode adjWeightMatrix = graphJN.get("adjWeightMatrix");

        if (Objects.isNull(vertices) || Objects.isNull(adjMatrix) || Objects.isNull(adjWeightMatrix)) throw new NullPointerException("Invalid elements!");

        UnorderedArrayList<EntitiesLocation> verticesList = new UnorderedArrayList<>();

        for (JsonNode element : vertices) {
            if (Objects.isNull(element)) continue;

            int id = element.get("id").asInt();
            int x = element.get("x").asInt();
            int y = element.get("y").asInt();

            EntitiesLocation vertex = new EntitiesLocation(id, x, y);

            newGraph.addVertex(vertex);
            verticesList.addToRear(vertex);
        }

        if(adjMatrix.size()!=adjWeightMatrix.size()) throw new NullPointerException("Invalid edges!");

        for(EntitiesLocation current : verticesList){

            JsonNode connections = adjMatrix.get(current.getId());
            JsonNode weights = adjWeightMatrix.get(current.getId());

            if (Objects.isNull(connections) || !connections.isArray()) continue;
            else if (Objects.isNull(weights) || !weights.isArray()) continue;

            int connectionsIndex = 0;

            for (JsonNode element : connections){

                if(!element.isBoolean() || connectionsIndex==current.getId()) {
                    connectionsIndex++;
                    continue;
                }

                boolean value = element.asBoolean();

                if(value) newGraph.addEdge(current, newGraph.getVerticeByIndex(connectionsIndex), bidirectional, density, adjWeightMatrix.get(connectionsIndex).asDouble());

                connectionsIndex++;
            }

        }
    }
}
