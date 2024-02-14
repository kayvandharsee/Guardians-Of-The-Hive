package assignment1;

public class SwarmOfHornets {
    private Hornet[] array;
    private int size;
    public static double QUEEN_BOOST;

    public SwarmOfHornets() {
        // Initialized to 10 spaces, but size 0 as all are null
        this.array = new Hornet[10];
        this.size = 0;
    }
    // Getter method
    public int sizeOfSwarm() {
        return this.size;
    }

    public Hornet[] getHornets() {
        if (this.size == 0) {
            return new Hornet[0];
        }
        Hornet[] hornetArray = new Hornet[this.size];
        for (int i = 0; i < this.size; i++) {
            hornetArray[i] = this.array[i];
        }
        return hornetArray;
    }

    public Hornet getFirstHornet() {
        if (this.size == 0) {
            return null;
        }
        return this.array[0];
    }

    public void addHornet(Hornet hornet) {
        if (this.array.length == this.size) {
            resize();
        }
        this.array[this.size] = hornet;
        this.size++;
        if (hornet.isTheQueen()) {
            for (int i = 0; i < this.size - 1; i++) {
                this.array[i].regenerateHealth(QUEEN_BOOST);
            }
        }
    }

    // Resizes array if needed
    private void resize() {
        Hornet[] larger = new Hornet[array.length * 2]; // Opted for doubling array size
        for (int i = 0; i < this.size; i++) {
            larger[i] = this.array[i];
        }
        this.array = larger;
    }

    public boolean removeHornet(Hornet hornet) {
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] == hornet) {
                this.array[i] = null;
                if (i != this.size - 1) {
                    for (int j = i; j < this.size - 1; j++) {
                        this.array[j] = this.array[j + 1];
                    }
                }
                size--;
                return true;
            }
        }
        return false;
    }

    public String toString() {
        if (this.size == 0) {
            return null;
        }
        String returnStr = "[";
        for (int i = 0; i < this.size; i++) {
            returnStr += this.array[i];
            if (i != this.size - 1) {
                returnStr += ", ";
            }
        }
        returnStr += "]";
        return returnStr;
    }
}
