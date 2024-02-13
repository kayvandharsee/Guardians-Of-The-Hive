public class Hornet extends Insect {
    private int attack;
    public static int BASE_FIRE_DMG;
    private boolean isQueen = false;
    private static int numQueens = 0;
    // Used for queens double moves, and static as there can only be one queen at a time
    private static boolean queensTurn = true;

    public Hornet(Tile tile, int hp, int attack) {
        super(tile, hp);
        this.attack = attack;
    }

    public String toString() {
        return "Hornet with attack " + this.attack;
    }

    public boolean takeAction() {
        boolean secondResult;
        if (this.getPosition().isOnFire()) {
            this.takeDamage(BASE_FIRE_DMG);
            // Handles case where hornet is dead before getting to attack
            if (this.getPosition() == null) {
                return false;
            }
        }
        if (this.getPosition().getBee() != null) {
            this.getPosition().getBee().takeDamage(this.attack);
            if (this.isQueen && queensTurn) {
                queensTurn = false;
                secondResult = takeAction();
                return secondResult;
            }
            else {
                return true;
            }
        }
        else {
            // Hornets win if there is no bees and they are on the hive. Queen doesn't need double turn in this case
            if (this.getPosition().isHive()) {
                return false;
            }
            else {
                // Removes insect from tile and adds insect to next tile (also takes care of changing the insects personal tile)
                Tile moveFrom = this.getPosition();
                moveFrom.removeInsect(this);
                moveFrom.towardTheHive().addInsect(this);
                if (this.isQueen && queensTurn) {
                    queensTurn = false;
                    secondResult = takeAction();
                    return secondResult;
                }
                else {
                    return true;
                }
            }
        }
    }

    public boolean equals(Object obj) {
        boolean equal = super.equals(obj);
        if (equal) {
            if (this.attack != ((Hornet) obj).attack) {
                equal = false;
            }
        }
        return equal;
    }

    public boolean isTheQueen() {
        return this.isQueen;
    }

    public void promote() {
        if (numQueens == 0) {
            this.isQueen = true;
            numQueens++;
        }
    }
}
