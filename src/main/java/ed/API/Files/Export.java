package ed.API.Files;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import ed.API.Game.EntitiesLocation;
import ed.Utils.Graph.Graph;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static ed.Utils.Files.File.getPathFromResources;

public class Export {

    private final Graph<EntitiesLocation> graph;
    private final double density;
    private final boolean bidirectional;


    public Export(Graph<EntitiesLocation> newGraph, double density, boolean bidirectional){
        this.graph = newGraph;
        this.density = density;
        this.bidirectional = bidirectional;
    }

    public void exportGame() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        int graphSize = graph.size();

        String buildDir = getPathFromResources("");
        File file = new File(buildDir, "gameFile.json");

        JsonGenerator generator = mapper.createGenerator(file, JsonEncoding.UTF8);

        generator.writeStartObject();

            generator.writeFieldName("graph");

            // Start the nested object
            generator.writeStartObject();

                generator.writeBooleanField("bidirectional", bidirectional);
                generator.writeNumberField("density", density);

                generator.writeFieldName("vertices");

                // Start the array
                generator.writeStartArray();

                Iterator<EntitiesLocation> itr = graph.getVertices();
                // Add array elements
                while (itr.hasNext()){
                    generator.writeObject(itr.next());
                }
                // End the array
                generator.writeEndArray();

                generator.writeFieldName("adjMatrix");

                generator.writeStartArray();

                boolean[][] adjMatrix = graph.getAdjMatrix();
                for(int i = 0; i<graphSize; i++){
                    boolean[] rowWeight = adjMatrix[i];
                    generator.writeStartArray();
                    for (boolean b : rowWeight) {
                        generator.writeBoolean(b);
                    }
                    generator.writeEndArray();
                }

                // End the array
                generator.writeEndArray();

                generator.writeFieldName("adjWeightMatrix");

                generator.writeStartArray();

                double[][] AdjWeightMatrix = graph.getAdjWeightMatrix();
                for(int i = 0; i<graphSize; i++){
                    double[] rowWeight = AdjWeightMatrix[i];
                    generator.writeStartArray();
                    for (double d : rowWeight) {
                        generator.writeNumber(d);
                    }
                    generator.writeEndArray();
                }

                // End the array
                generator.writeEndArray();

            // End the nested object
            generator.writeEndObject();

        generator.writeEndObject();

        generator.close();
    }

}
