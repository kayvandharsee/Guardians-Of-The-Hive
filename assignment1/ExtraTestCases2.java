package assignment1;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExtraTestCases2{
	
    // Replace NUM_OF_QUEENS with the name of the field in your hornet class that tracks queen creations!!
    private static final String numberOfQueensFieldLabel = "numQueens";

    // need to initialize queen at the start since there can only be one
    private static Hornet queen;

    @BeforeEach
    public void setupQueen() throws IllegalAccessException {
	// Set the number of queens to zero
    	try {
	    Field numberOfQueensField = Hornet.class.getDeclaredField(numberOfQueensFieldLabel);
	    numberOfQueensField.setAccessible(true);
	    numberOfQueensField.set(null, 0);
	    numberOfQueensField.setAccessible(false);
    	} catch (NoSuchFieldException e) {
	    System.out.println("Please set the value numberOfQueensFieldLabel at the top of the test class before testing (no such field '" + numberOfQueensFieldLabel + "').");
	}
	
	// Replace the queen with a new object
        Tile placeHolderForQueen = new Tile(0, true, true, true, new Tile(), new Tile(), null, new SwarmOfHornets());
        queen = new Hornet(placeHolderForQueen, 10, 50);
        queen.promote();
    }
    
    @BeforeEach
    public void setStaticVariables() {
    	Hornet.BASE_FIRE_DMG = 0;
    	SwarmOfHornets.QUEEN_BOOST = 0.0;
    	
    	HoneyBee.HIVE_DMG_REDUCTION = 0;
    	
    	BusyBee.BASE_AMOUNT_COLLECTED = 1;
    	BusyBee.BASE_COST = 2;
    	BusyBee.BASE_HEALTH = 3;
    	
    	AngryBee.BASE_COST = 4;
    	AngryBee.BASE_HEALTH = 3;
    	
    	FireBee.BASE_COST = 5;
    	FireBee.BASE_HEALTH = 3;
    	
    	SniperBee.BASE_COST = 6;
    	SniperBee.BASE_HEALTH = 3;
    }

    // test createPath() null case
    // you should only be able to pass null as an argument if
    // the tile you are calling createPath() on is a hive/nest
    @Test
    @DisplayName("test createPath null case")
    public void testCreatePathNullCase() {
        // Path1: nest -> tile1
        // Path2: tile2 -> hive
        // Path3: null -> tile1 -> tile2 (should raise exception)

        // path1
        Tile nest = new Tile();
        nest.buildNest();
        boolean path1Passed = true;
        try {
            nest.createPath(new Tile(), null);
        }
        catch(Exception e) {
            path1Passed = false;
        }

        // path 2
        Tile hive = new Tile();
        hive.buildHive();
        boolean path2Passed = true;
        try {
            hive.createPath(null, new Tile());
        }
        catch(Exception e) {
            path2Passed = false;
        }

        // path 3
        Tile tile1 = new Tile();
        boolean path3RaisedException = false;
        try {
            tile1.createPath(new Tile(), null);
        }
        catch(Exception e) {
            path3RaisedException = true;
        }

        assertTrue(path1Passed && path2Passed && path3RaisedException,
                "null case not handled properly");
    }

    // test health regeneration rounding
    // should round downwards, even if >.5
    @Test
    @DisplayName("test health regeneration rounding")
    public void testRegenerateRounding() {
        Tile tile = new Tile();
        tile.createPath(new Tile(), new Tile());

        // Regenerating 13% of 13 should return a new health of 14
        Hornet testHornet1 = new Hornet(tile, 13, 0);
        testHornet1.regenerateHealth(13.0/100);
        boolean firstCasePassed = (testHornet1.getHealth() == 14);

        // Regenerating 19.4% of 67 should return a new health value of 79
        Hornet testHornet2 = new Hornet(tile,67,0);
        testHornet2.regenerateHealth(19.4/100);
        boolean secondCasePassed = (testHornet2.getHealth() == 79);

        // Regenerating 1% of 100 should return a new health value of 101
        Hornet testHornet3 = new Hornet(tile,100,0);
        testHornet3.regenerateHealth(1.0/100);
        boolean thirdCasePassed = (testHornet3.getHealth() == 101);

        // Regenerating 0% of 1 should return a new health value of 1
        Hornet testHornet4 = new Hornet(tile,1,0);
        testHornet4.regenerateHealth(0.0/100);
        boolean fourthCasePassed = (testHornet4.getHealth() == 1);

        assertTrue(firstCasePassed & secondCasePassed & thirdCasePassed
                    & fourthCasePassed, "health regeneration didn't round properly");

    }

    // test hive damage reduction
    // should round downwards, even if >.5
    @Test
    @DisplayName("test hive damage reduction rounding")
    public void testHiveDamageReductionRounding() {
        Tile tile = new Tile();
        tile.buildHive();
        BusyBee.BASE_HEALTH = 10;

        // Taking 1 damage with 1% damage reduction should do 0 damage
        BusyBee testBee1 = new BusyBee(tile);
        HoneyBee.HIVE_DMG_REDUCTION = 1.0/100;
        testBee1.takeDamage(1);
        boolean firstCasePassed = testBee1.getHealth() == 10;
        tile.removeInsect(testBee1);

        // Taking 5 damage with 0% damage reduction should do 5 damage
        BusyBee testBee2 = new BusyBee(tile);
        HoneyBee.HIVE_DMG_REDUCTION = 0;
        testBee2.takeDamage(5);
        boolean secondCasePassed = testBee2.getHealth() == 5;
        tile.removeInsect(testBee2);

        // Taking 1 damage with 0.000000001% damage reduction should do 0 damage
        BusyBee testBee3 = new BusyBee(tile);
        HoneyBee.HIVE_DMG_REDUCTION = 0.000000001/100;
        testBee3.takeDamage(1);
        boolean thirdCasePassed = testBee3.getHealth() == 10;
        tile.removeInsect(testBee3);

        // Taking 2 damage with 10% damage reduction should do 1 damage
        BusyBee testBee4 = new BusyBee(tile);
        HoneyBee.HIVE_DMG_REDUCTION = 10.0/100;
        testBee4.takeDamage(2);
        boolean fourthCasePassed = testBee4.getHealth() == 9;

        assertTrue(firstCasePassed & secondCasePassed & thirdCasePassed
                & fourthCasePassed, "damage reduction didn't round properly");
    }

    // test adding bee on top of another
    @Test
    @DisplayName("test adding bee on top of another")
    public void testAddingBeeOnTopOfAnother() {
        Tile tile = new Tile();
        BusyBee bee1 = new BusyBee(tile);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            BusyBee bee2 = new BusyBee(tile);
        }, "should throw exception when adding to tile that already has a bee");
    }

    // test adding hornet to a non-path tile
    @Test
    @DisplayName("test adding hornet to non-path tile")
    public void testAddingHornetToNonPathTile() {
        Tile tileNotOnPath = new Tile();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Hornet notOnPathHornet = new Hornet(tileNotOnPath, 0, 0);
        }, "should throw exception when adding to non-path tile");
    }

    // test double add when hornet created in add function
    @Test
    @DisplayName("test adding a hornet within the constructor of a tile")
    public void testAddingHornetWithinConstructor() {
        Tile newTile = new Tile();
        newTile.createPath(new Tile(), new Tile());
        newTile.addInsect(new Hornet(newTile, 8, 10));
        Hornet hornet2 = new Hornet(newTile, 8, 10);
        assertEquals(2, newTile.getHornets().length);
    }

    // test queen double action
    // queen should act twice in one call to takeAction()
    @Test
    @DisplayName("test queen double action")
    public void testQueenDoubleAction() {
        // Path : tile1 (nest) -> tile2 -> tile3
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        Tile tile3 = new Tile();
        tile1.buildNest();
        tile1.createPath(tile2, null);
        tile2.createPath(tile3, tile1);
        tile3.createPath(new Tile(), tile2);
        tile1.addInsect(this.queen);
        // queen should move two spots with this takeAction()
        this.queen.takeAction();
        assertEquals(1, tile3.getNumOfHornets(), "queen did not act twice");
    }

    // test queen regeneration upon adding to swarm
    // should heal the entire swarm except self by constant QUEEN_BOOST upon joining
    // fails if regeneration is rounded improperly
    @Test
    @Order(1)
    @DisplayName("test queen regeneration")
    public void testQueenRegeneration() {
        Tile tile = new Tile();
        Tile placeholder = new Tile();
        tile.createPath(new Tile(), new Tile());
        placeholder.createPath(new Tile(), new Tile());
        Hornet hornet1 = new Hornet(tile, 10, 0);
        Hornet hornet2 = new Hornet(tile, 10, 0);
        Hornet hornet3 = new Hornet(tile, 10, 0);
        // hornet1, hornet2, hornet3 should all regenerate when queen joins
        SwarmOfHornets.QUEEN_BOOST = 50.0/100;
        tile.addInsect(queen);
        // check if hornet1, hornet2, hornet3 regenerated
        boolean regeneratedCorrectly = (hornet1.getHealth() == 15
                && hornet2.getHealth() == 15 && hornet3.getHealth() == 15);
        // make sure queen didn't regenerate
        boolean queenDidntRegenerate = (queen.getHealth() == 10);
        assertTrue(regeneratedCorrectly & queenDidntRegenerate,
                "queen regeneration didn't work correctly");
    }

    // test queen double action with a death
    // if the queen dies on her first action, takeAction() should immediately return
    // false and no exception should be output

    @Test
    @Order(2)
    @DisplayName("test queen double action w/ death")
    public void testQueenDeathDuringAction() {
        // Path: nest -> tile1 (queen) (on fire) -> tile2
        Tile nest = new Tile();
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        nest.buildNest();
        tile1.createPath(tile2,nest);
        tile1.addInsect(queen);
        tile1.setOnFire();
        Hornet.BASE_FIRE_DMG = 1000;
        // the queen should die during this first action, the method should return false
        // and she should not be found present on tile 2 afterward
        boolean returnedFalse = !queen.takeAction();
        boolean queenNotOnTile2 = queen.getPosition() != tile2;
        assertTrue(returnedFalse && queenNotOnTile2, "the queen either moved up" +
                " or didn't return false after dying");
    }

    // test hornet death by fire cleanup
    // if a hornet dies from fire damage, ensure that it does not move forward afterward
    @Test
    @DisplayName("test hornet death by fire cleanup")
    public void testHornetDeathByFireCleanup() {
        Hornet.BASE_FIRE_DMG = 500;
        // Path: nest -> tile1 -> tile2
        Tile hornetNest = new Tile();
        hornetNest.buildNest();
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        hornetNest.createPath(tile1, null);
        tile1.createPath(tile2, hornetNest);
        // We check that if a hornet dies from fire on tile1 that it does not move to tile2
        Hornet dyingHornet = new Hornet(hornetNest, 1, 0);
        FireBee hornetKiller = new FireBee(tile2, 3);
        hornetKiller.takeAction();
        dyingHornet.takeAction();
        assertEquals(0, tile2.getNumOfHornets(),
                "hornet died but still moved on to next tile");
    }

    // test sniper bee aiming phase
    // expected actions: turn1: aim, turn2: shoot, turn3: aim, turn4: shoot
    @Test
    @DisplayName("test sniper bee aiming phase")
    public void testSniperBeeCooldown() {
        // Path: tile1 (nest) -> tile2 -> tile3 (has sniper bee)
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        Tile tile3 = new Tile();
        tile1.buildNest();
        tile1.createPath(tile2, null);
        tile2.createPath(tile3, tile1);
        tile3.createPath(new Tile(), tile2);
        SniperBee sniperBee = new SniperBee(tile3, 4, 1);
        Hornet hornet = new Hornet(tile2, 100, 0);
        // test that sniper bee only attacks every two turns
        boolean sniperBeeAimsCorrectly1 = !sniperBee.takeAction();
        boolean sniperBeeShootsCorrectly1 = sniperBee.takeAction();
        boolean sniperBeeAimsCorrectly2 = !sniperBee.takeAction();
        boolean sniperBeeShootsCorrectly2 = sniperBee.takeAction();
        assertTrue(sniperBeeAimsCorrectly1 && sniperBeeShootsCorrectly1 &&
                sniperBeeAimsCorrectly2 && sniperBeeShootsCorrectly2,
                "sniper bee cooldown didn't work properly");
    }

    // test sniper bee piercing
    // sniper bee should hit min(piercingPower, amountOfHornets) in swarm
    @Test
    @DisplayName("test sniper bee piercing")
    public void testSniperBeePiercing() {
        // Path: tile1 (nest) -> tile2 (swarm) -> tile3 (sniper bee)
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        Tile tile3 = new Tile();
        tile1.buildNest();
        tile1.createPath(tile2, null);
        tile2.createPath(tile3, tile1);
        tile3.createPath(new Tile(), tile2);
        SniperBee sniperBee = new SniperBee(tile3, 5,2);
        // Swarm: [hornet1, hornet2]
        Hornet hornet1 = new Hornet(tile2, 10, 0);
        Hornet hornet2 = new Hornet(tile2, 10, 0);
        sniperBee.takeAction();
        // sniper bee should now hit both hornets in swarm for 5 damage
        sniperBee.takeAction();
        boolean successfullyPierced = (hornet1.getHealth() == 5 && hornet2.getHealth() == 5);
        assertTrue(successfullyPierced, "did not do damage to both hornets");
    }

    // test sniper bee death cleanup
    // if a sniper bee kills a hornet in the swarm, make sure that the next target
    // is still hit for damage
    @Test
    @DisplayName("test sniper bee death cleanup")
    public void testSniperBeeDeathCleanup() {
        // Path : tile1 (nest) -> tile2 (swarm) -> tile3 (sniper bee)
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
        assertEquals(0, tile2.getNumOfHornets(), "sniperBee did not kill both hornets");
    }

}
