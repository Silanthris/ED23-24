package ed.API.Game;

public interface IGameMap {

    void generateRandomGraphWithRules(int numLocations, boolean bidirectional, double density);
    void generateRandomGraph(int numLocations, boolean bidirectional);
    void printGraph();
    void printMatrix(boolean[][] matrix, int numlocations);
    void printMatrixWeight(double[][] matrix, int numlocations);
    void selectFlagLocations(int player1Flag, int player2Flag);
    void startGame();
    void playRound();
    boolean checkVictoryCondition();
    boolean checkLocationForBot(int location);

}
