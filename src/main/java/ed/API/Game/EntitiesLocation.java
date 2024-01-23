package ed.API.Game;

public class EntitiesLocation implements IEntitiesLocation {
    private final int id;
    private final double x;
    private final double y;
    private boolean isPlayer1Flag;
    private boolean isPlayer2Flag;

    public EntitiesLocation(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isPlayer1Flag = false;
        this.isPlayer2Flag = false;
    }

    public boolean isPlayer1Flag() {
        return isPlayer1Flag;
    }

    public boolean isPlayer2Flag() {
        return isPlayer2Flag;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPlayer1Flag(boolean player1Flag) {
        isPlayer1Flag = player1Flag;
    }

    public void setPlayer2Flag(boolean player2Flag) {
        isPlayer2Flag = player2Flag;
    }
}
