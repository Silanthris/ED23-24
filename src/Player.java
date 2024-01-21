import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Player {
    List<Bot> bots;
    List<Algorithm> availableAlgorithms;

    public Player() {
        this.bots = new ArrayList<>();
        this.availableAlgorithms = new ArrayList<>();
        // Adicione seus algoritmos à lista de algoritmos disponíveis
        this.availableAlgorithms.add(new RandomMovementAlgorithm());
        this.availableAlgorithms.add(new ShortestPathAlgorithm());
        // Adicione outros algoritmos conforme necessário
    }

    public void setNumBots(int numBots) {
        if (numBots > availableAlgorithms.size()) {
            throw new IllegalArgumentException("Not enough available algorithms for the specified number of bots.");
        }

        for (int i = 0; i < numBots; i++) {
            Bot bot = new Bot();
            Algorithm selectedAlgorithm = availableAlgorithms.get(i);
            bot.setAlgorithm(selectedAlgorithm);
            bots.add(bot);
        }
    }
}