package assignment1;

public class Tile {
    private int food;
    private boolean hive;
    private boolean nest;
    private boolean path;
    private Tile toHive;
    private Tile toNest;
    private HoneyBee bee;
    private SwarmOfHornets hornets;
    private boolean onFire = false;

    public Tile() {
        this.food = 0;
        this.hive = false;
        this.nest = false;
        this.path = false;
        this.hornets = new SwarmOfHornets();
        // Everything else is kept as null
    }

    public Tile(int food, boolean hive, boolean nest, boolean path, Tile toHive, Tile toNest, HoneyBee bee, SwarmOfHornets hornets) {
        this.food = food;
        this.hive = hive;
        this.nest = nest;
        this.path = path;
        this.toHive = toHive;
        this.toNest = toNest;
        this.bee = bee;
        this.hornets = hornets;
    }

    // Getters and Setters
    public boolean isHive() {
        return this.hive;
    }

    public boolean isNest() {
        return this.nest;
    }

    public void buildHive() {
        this.hive = true;
    }

    public void buildNest() {
        this.nest = true;
    }

    public boolean isOnThePath() {
        return this.path;
    }

    public Tile towardTheHive() {
        return this.toHive;
    }

    public Tile towardTheNest() {
        return this.toNest;
    }

    public void createPath(Tile tileHive, Tile tileNest) {
        if (tileHive == null) {
            if (!this.hive) {
                throw new IllegalArgumentException("Path must end on a hive");
            }
        }
        if (tileNest == null) {
            if (!this.nest) {
                throw new IllegalArgumentException("Path must end on a nest");
            }
        }
        this.toHive = tileHive;
        this.toNest = tileNest;
        this.path = true;
    }

    public int collectFood() {
        int collect = this.food;
        this.food = 0;
        return collect;
    }

    public void storeFood(int food) {
        this.food += food;
    }

    public int getNumOfHornets() {
        return this.hornets.sizeOfSwarm();
    }

    public HoneyBee getBee() {
        return this.bee;
    }

    public Hornet getHornet() {
        return this.hornets.getFirstHornet();
    }

    public Hornet[] getHornets() {
        return this.hornets.getHornets();
    }

    public boolean addInsect(Insect insect) {
        if (insect instanceof HoneyBee) {
            if (this.bee != null || this.nest) {
                return false;
            }
            this.bee = (HoneyBee) insect;
            insect.setPosition(this);
            return true;
        }
        else if (insect instanceof Hornet) {
            if (!this.path) {
                return false;
            }
            for (Hornet hornet : this.hornets.getHornets()) {
                if (insect == hornet) {
                    return false;
                }
            }
            this.hornets.addHornet((Hornet) insect);
            insect.setPosition(this);
            return true;
        }
        // Keeping code expandable in case new insects are added to the game
        return false;
    }

    public boolean removeInsect(Insect insect) {
        if (insect instanceof HoneyBee) {
            if (insect == this.bee) {
                this.bee = null;
                insect.setPosition(null);
                return true;
            }
            else {
                return false;
            }
        }
        else if (insect instanceof Hornet) {
            boolean removed = this.hornets.removeHornet((Hornet) insect);
            if (removed) {
                insect.setPosition(null);
            }
            // True if removed, false otherwise
            return removed;
        }
        // only occurs if this insect is somehow neither a bee or hornet. Left it this way as it allows for expansion of other insects
        return false;
    }

    public void setOnFire() {
        this.onFire = true;
    }

    public boolean isOnFire() {
        return this.onFire;
    }
}