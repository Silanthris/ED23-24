class Location {
    int id;
    double x;
    double y;
    boolean isPlayer1Flag;
    boolean isPlayer2Flag;

    public Location(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isPlayer1Flag = false;
        this.isPlayer2Flag = false;
    }
}
