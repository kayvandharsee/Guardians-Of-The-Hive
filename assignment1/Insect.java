package assignment1;

public abstract class Insect {
    private Tile tile;
    private int hp;
    public Insect(Tile tile, int hp) {
        this.tile = tile;
        this.hp = hp;
        if (this instanceof HoneyBee) {
            if (this.tile.getBee() != null) {
                throw new IllegalArgumentException("There is already a bee occupying this tile");
            }
            else if (this.tile.isNest()) {
                throw new IllegalArgumentException("Cannot place a bee on the assignment1.Hornet Nest");
            }
            else {
                this.tile.addInsect(this);
            }
        }
        else if (this instanceof Hornet) {
            if (!this.tile.isOnThePath()) {
                throw new IllegalArgumentException("Hornets must be placed on the path from the assignment1.Hornet Nest to the Beehive");
            }
            else {
                this.tile.addInsect(this);
            }
        }
    }

    // Final methods cannot be overridden by subclasses
    public final Tile getPosition() {
        return this.tile;
    }

    public final int getHealth() {
        return this.hp;
    }

    public void setPosition(Tile tile) {
        this.tile = tile;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            this.tile.removeInsect(this);
            this.tile = null;
        }
    }

    public abstract boolean takeAction();

    public boolean equals(Object obj) {
        // We avoid the dangers of down casting by checking if it is the same class first
        return this.getClass() == obj.getClass() && this.tile == ((Insect) obj).tile && this.hp == ((Insect) obj).hp;
    }

    public void regenerateHealth(double regen) {
        this.hp += (int) (this.hp * regen);
    }
}
