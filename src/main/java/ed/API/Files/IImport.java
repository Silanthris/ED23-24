package ed.API.Files;

import com.fasterxml.jackson.databind.JsonNode;
import ed.API.Game.EntitiesLocation;
import ed.Utils.Graph.Graph;

import java.io.IOException;

public interface IImport {
    Graph<EntitiesLocation> importGame() throws IOException;
}
