package assignment1;

public class Tester {
    public static void main(String[] args) {
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        Tile tile3 = new Tile();
        tile1.buildNest();
        tile1.createPath(tile2, null);
        tile2.createPath(tile3, tile1);
        tile3.createPath(new Tile(), tile2);
        SniperBee sniperBee = new SniperBee(tile3, 5,2);
        // Swarm: [hornet1, hornet2]
        Hornet hornet1 = new Hornet(tile2, 1, 0);
        Hornet hornet2 = new Hornet(tile2, 1, 0);
        sniperBee.takeAction();
        // sniperBee should kill hornet1 and hornet2
        sniperBee.takeAction();
        System.out.println(hornet1.getHealth() + " and " + hornet2.getHealth());
    }
}
