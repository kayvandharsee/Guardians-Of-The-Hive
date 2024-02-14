package assignment1;

public class FireBee extends HoneyBee{
    private int range;
    public static int BASE_HEALTH;
    public static int BASE_COST;

    public FireBee(Tile tile, int range) {
        super(tile, BASE_HEALTH, BASE_COST);
        this.range = range;
    }

    public boolean takeAction() {
        Tile current = this.getPosition();
        if (this.getPosition().isOnThePath()) {
            for (int i = 0; i < this.range; i++) {
                current = current.towardTheNest();
                if (current.getHornet() != null && !current.isOnFire() && !current.isNest()) {
                    current.setOnFire();
                    return true;
                }
            }
        }
        // Catches all false conditions
        return false;
    }
}
