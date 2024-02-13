public class SniperBee extends HoneyBee{
    private int attack;
    private int pierce;
    public static int BASE_HEALTH;
    public static int BASE_COST;
    private boolean shoot = false;

    public SniperBee(Tile tile, int attack, int pierce) {
        super(tile, BASE_HEALTH, BASE_COST);
        this.attack = attack;
        this.pierce = pierce;
    }

    public boolean takeAction() {
        Tile current = this.getPosition();
        if (this.getPosition().isOnThePath()) {
            if (this.shoot) {
                while (!current.towardTheNest().isNest()) {
                    current = current.towardTheNest();
                    if (current.getHornet() != null) {
                        int count = 0;
                        for (Hornet hornet : current.getHornets()) {
                            hornet.takeDamage(this.attack);
                            count++;
                            if (count == this.pierce) {
                                break;
                            }
                        }
                        this.shoot = false;
                        return true;
                    }
                }
                this.shoot = false;
                return false;
            }
            else {
                this.shoot = true;
                return false;
            }
        }
        // Handles if bee is not on tile
        return false;
    }
}
