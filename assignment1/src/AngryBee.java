public class AngryBee extends HoneyBee {
    private int attack;
    public static int BASE_HEALTH;
    public static int BASE_COST;

    public AngryBee(Tile tile, int attack) {
        super(tile, BASE_HEALTH, BASE_COST);
        this.attack = attack;
    }

    public boolean takeAction() {
        if (super.getPosition().isOnThePath()) {
            // getHornet will return null if there is no swarm, and get the first hornet if there is a swarm
            if (super.getPosition().getHornet() != null) {
                super.getPosition().getHornet().takeDamage(this.attack);
                return true;
            }
            else if (super.getPosition().towardTheNest().getHornet() != null && !super.getPosition().towardTheNest().isNest()) {
                super.getPosition().towardTheNest().getHornet().takeDamage(this.attack);
                return true;
            }
        }
        // Catches all false outcomes
        return false;
    }
}
