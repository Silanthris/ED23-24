package ed;

import ed.API.Edge.Edge;
import ed.API.Game.EntitiesLocation;
import ed.API.Game.GameMap;
import ed.Utils.Graph.Graph;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException{

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