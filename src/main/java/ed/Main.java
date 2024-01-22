package ed;

import ed.API.Edge.Edge;
import ed.API.Game.EntitiesLocation;
import ed.API.Game.GameMap;
import ed.Utils.Graph.Graph;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException{

//        Graph<EntitiesLocation> test = new Graph<>();
//
//        EntitiesLocation ver1 = new EntitiesLocation(1, 2, 3);
//        EntitiesLocation ver2 = new EntitiesLocation(2, 2, 3);
//        EntitiesLocation ver3 = new EntitiesLocation(3, 2, 3);
//        EntitiesLocation ver4 = new EntitiesLocation(4, 2, 3);
//
//        test.addVertex(ver1);
//        test.addVertex(ver2);
//        test.addVertex(ver3);
//        test.addVertex(ver4);
//
//        test.addEdge(ver1, ver2, true, 0.5, 12.0);
//        test.addEdge(ver1, ver3, true, 0.5, 12.0);
//        test.addEdge(ver2, ver4, true, 0.5, 10.0);
//        test.addEdge(ver3, ver2, true, 0.5, 2.0);
//        test.addEdge(ver3, ver4, true, 0.5, 15.0);
//
//        Iterator<EntitiesLocation> verticesIterator2 = test.getVertices();
//
//        while (verticesIterator2.hasNext()) {
//
//            EntitiesLocation current = verticesIterator2.next();
//
//            Iterator<EntitiesLocation> adjMatrix = test.getAdjacentVertices(current);
//
//            while (adjMatrix.hasNext()) {
//
//                System.out.println("Edge: (" + current.getId() + ") -> (" + adjMatrix.next().getId() + ")");
//
//            }
//
//        }
//
//        Iterator<EntitiesLocation> itr = test.iteratorShortestPath(ver1, ver4);
//        double distance = test.shortestPathLength(ver1, ver4);
//
//        while (itr.hasNext()){
//            System.out.println(itr.next().getId());
//        }
//
//        System.out.println("Distance:" + distance + "km");

        // Example usage
        int numLocations = 10;
        boolean bidirectional = true;
        double density = 0.4;
        double densityCalculated = ((numLocations * (numLocations - 1)) * 0.5) / 100;

        System.out.println(densityCalculated);

        GameMap gameMap = new GameMap(bidirectional, densityCalculated);
        gameMap.generateRandomGraph(numLocations,bidirectional);

        gameMap.printGraph();


        // Permita que os jogadores selecionem as localizações das bandeiras
        Scanner scanner = new Scanner(System.in);
        System.out.print("Jogador 1 - Selecione a localização da bandeira (1-" + numLocations + "): ");
        int player1Flag = scanner.nextInt();

        System.out.print("Jogador 2 - Selecione a localização da bandeira (1-" + numLocations + "): ");
        int player2Flag = scanner.nextInt();

        gameMap.selectFlagLocations(player1Flag, player2Flag);

        // Allow players to specify the number of bots
        System.out.print("Player 1 - Specify the number of bots: ");
        int numBotsPlayer1 = scanner.nextInt();
        gameMap.getPlayer1().setNumBots(numBotsPlayer1);

        System.out.print("Player 2 - Specify the number of bots: ");
        int numBotsPlayer2 = scanner.nextInt();
        gameMap.getPlayer2().setNumBots(numBotsPlayer2);

        // Start the game, deciding randomly which player begins and placing bots at flag locations
        gameMap.startGame();

        int counter = 0;

        while(gameMap.getCurrentRound() != 0){

            //gameMap.printCurrentPlayerTurn();
            //gameMap.printBotLocations();

            gameMap.playRound();

            counter++;

            if(counter > 2){
                gameMap.setCurrentRound(0);
            }


        }

    }

}