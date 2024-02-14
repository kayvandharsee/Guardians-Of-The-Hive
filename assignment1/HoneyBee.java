package assignment1;

public abstract class HoneyBee extends Insect {
    private int cost;
    public static double HIVE_DMG_REDUCTION;

    public HoneyBee(Tile tile, int hp, int cost) {
        super(tile, hp);
        this.cost = cost;
    }

    public int getCost() {
        return this.cost;
    }

    public void takeDamage(int damage) {
        if (this.getPosition().isHive()) {
            super.takeDamage((int) (damage - damage * HIVE_DMG_REDUCTION));
        }
        else {
            super.takeDamage(damage);
        }
    }
}
