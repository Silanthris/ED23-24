package ed.API.Player;

import ed.API.Algorithm.Algorithm;
import ed.API.Algorithm.RandomMovementAlgorithm;
import ed.API.Algorithm.ShortestPathAlgorithm;
import ed.API.Bot.Bot;

import pt.ipp.estg.data.structures.List.ArrayList;

public class Player {
    private final ArrayList<Bot> bots;
    private final ArrayList<Algorithm> availableAlgorithms;

    public Player() {
        this.bots = new ArrayList<Bot>();
        this.availableAlgorithms = new ArrayList<Algorithm>();
        // Adicione seus algoritmos à lista de algoritmos disponíveis
//        this.availableAlgorithms.add(new RandomMovementAlgorithm());
//        this.availableAlgorithms.add(new ShortestPathAlgorithm());
        // Adicione outros algoritmos conforme necessário
    }

    public ArrayList<Bot> getBots(){
        return bots;
    }

    public ArrayList<Algorithm> getAvailableAlgorithms(){
        return availableAlgorithms;
    }

    public void setNumBots(int numBots) {
        if (numBots > availableAlgorithms.size()) {
            throw new IllegalArgumentException("Not enough available algorithms for the specified number of bots.");
        }

        for (int i = 0; i < numBots; i++) {
//            Bot bot = new Bot();
//            Algorithm selectedAlgorithm = availableAlgorithms.get(i);
//            bot.setAlgorithm(selectedAlgorithm);
//            bots.add(bot);
        }
    }
}