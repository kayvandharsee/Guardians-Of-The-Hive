package assignment1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MiniTester_GuardiansOfTheHive {
	@Test
	@Tag("score:0")
	@DisplayName("Test if the expected class names exist")
	void testClassNames() {
		String[] expectedClassNames = {"AngryBee","assignment1.BusyBee","assignment1.FireBee","assignment1.HoneyBee","assignment1.Hornet","assignment1.Insect","assignment1.SniperBee","assignment1.SwarmOfHornets","assignment1.Tile"};
		boolean allClassesExist = true;
		for (String className : expectedClassNames) {
			try {
				Class.forName("assignment1." + className);
			} catch (ClassNotFoundException e) {
				System.out.println("Missing class: " + className);
				allClassesExist = false;
			}
		}
		// Print that all classes exist
		assertTrue(allClassesExist, "Not all expected classes exist..");
		System.out.println("All expected class names exist..");

	}

	@Test
	@Tag("score:0")
	@DisplayName("Test if assignment1.Insect and assignment1.HoneyBee are abstract classes")
	void testAbstractClasses() {
		// Test if Unit and MilitaryUnit are abstract
		assertTrue(Modifier.isAbstract(HoneyBee.class.getModifiers()), "assignment1.HoneyBee should be an abstract class..");
		assertTrue(Modifier.isAbstract(Insect.class.getModifiers()), "assignment1.Insect should be an abstract class..");
	}

	@Test
	@Tag("score:0")
	@DisplayName("Testing inheritance for assignment1.Insect and assignment1.HoneyBee")
	void testInheritance() {
		boolean allInheritanceCorrect = true;
		allInheritanceCorrect &= isSubclass(AngryBee.class, HoneyBee.class);
		allInheritanceCorrect &= isSubclass(BusyBee.class, HoneyBee.class);
		allInheritanceCorrect &= isSubclass(FireBee.class, HoneyBee.class);
		allInheritanceCorrect &= isSubclass(SniperBee.class, HoneyBee.class);
		allInheritanceCorrect &= isSubclass(HoneyBee.class, Insect.class);
		allInheritanceCorrect &= isSubclass(Hornet.class, Insect.class);

		assertTrue(allInheritanceCorrect, "Not all inheritances are correct..");
	}

	@Test
	@Tag("score:0")
	@DisplayName("Test if all expected classes have a constructor")
	void testIfAllClassesHaveAConstructor() {
		String[] expectedClassNames = {"AngryBee","assignment1.BusyBee","assignment1.FireBee","assignment1.HoneyBee","assignment1.Hornet","assignment1.Insect","assignment1.SniperBee","assignment1.SwarmOfHornets","assignment1.Tile"};
		// Test if all classes have a constructor
		boolean allClassesHaveAConstructor = true;
		for (String className : expectedClassNames) {
			try {
				Constructor<?> constructor = Class.forName("assignment1." + className).getDeclaredConstructors()[0];
				if (constructor.getParameterCount() == 0) {
					if(className.equals("assignment1.SwarmOfHornets") || className.equals("assignment1.Tile")) {
						continue;
					}
					System.out.println("Missing constructor for class: " + className);
					allClassesHaveAConstructor = false;
				}
			} catch (ClassNotFoundException e) {
				System.out.println("Missing class: " + className);
				allClassesHaveAConstructor = false;
			}
		}
		// Print that all classes have a constructor
		assertTrue(allClassesHaveAConstructor, "Not all classes have a constructor..");
		System.out.println("All classes have a constructor..");
	}

	@Test
	@Tag("score:0")
	@DisplayName("Test expected method names for each class")
	void testMethodNamesForClasses() throws ClassNotFoundException {
		boolean allMethodsExist = true;
		//For assignment1.Insect
		allMethodsExist &= testMethodNamesForClass("assignment1.Insect", "getPosition", "getHealth", "setPosition", "takeDamage", "takeAction", "equals", "regenerateHealth");
		//For AngryBee
		allMethodsExist &= testMethodNamesForClass("AngryBee", "takeAction");
		//For assignment1.BusyBee
		allMethodsExist &= testMethodNamesForClass("assignment1.BusyBee", "takeAction");
		//For assignment1.FireBee
		allMethodsExist &= testMethodNamesForClass("assignment1.FireBee", "takeAction");
		//For assignment1.SniperBee
		allMethodsExist &= testMethodNamesForClass("assignment1.SniperBee", "takeAction");
		//For assignment1.HoneyBee
		allMethodsExist &= testMethodNamesForClass("assignment1.HoneyBee", "getCost","takeDamage");
		//For assignment1.Hornet
		allMethodsExist &= testMethodNamesForClass("assignment1.Hornet", "takeAction", "equals", "isTheQueen", "promote");
		//For assignment1.Tile
		allMethodsExist &= testMethodNamesForClass("assignment1.Tile", "setOnFire", "isOnFire", "isHive", "isNest","buildHive", "buildNest", "isOnThePath","towardTheHive","towardTheNest","createPath","collectFood","storeFood","getNumOfHornets","getBee","getHornet","getHornets","addInsect","removeInsect");
		//For assignment1.SwarmOfHornets
		allMethodsExist &= testMethodNamesForClass("assignment1.SwarmOfHornets", "sizeOfSwarm", "getHornets", "addHornet", "removeHornet", "getFirstHornet");

		assertTrue(allMethodsExist, "Not all expected methods exist..");
		System.out.println("All expected method names exist..");
	}

	@Test
	@Tag("score:0")
	@DisplayName("Test if any expected declared fields are missing")
	void testDeclaredFields() {
		boolean allFieldsExist = true;
		allFieldsExist &= checkFieldsForClass("AngryBee", int.class, int.class, int.class);
		allFieldsExist &= checkFieldsForClass("assignment1.BusyBee", int.class, int.class, int.class);
		allFieldsExist &= checkFieldsForClass("assignment1.FireBee", int.class, int.class, int.class);
		allFieldsExist &= checkFieldsForClass("assignment1.SniperBee", int.class, int.class, int.class, int.class);
		allFieldsExist &= checkFieldsForClass("assignment1.HoneyBee", double.class, int.class);
		allFieldsExist &= checkFieldsForClass("assignment1.Hornet", int.class, int.class, boolean.class, int.class);
		allFieldsExist &= checkFieldsForClass("assignment1.SwarmOfHornets", Hornet[].class, int.class, double.class);
		allFieldsExist &= checkFieldsForClass("assignment1.Insect", Tile.class, int.class);
		allFieldsExist &= checkFieldsForClass("assignment1.Tile", int.class, boolean.class, boolean.class, boolean.class, Tile.class, Tile.class, SwarmOfHornets.class, HoneyBee.class);

		assertTrue(allFieldsExist, "Not all expected declared fields exist..");
		System.out.println("All expected declared fields exist..");
	}

	private boolean isSubclass(Class<?> childClass, Class<?> parentClass) {
		if(!parentClass.isAssignableFrom(childClass)) {
			System.out.println(childClass.getSimpleName() + " should be a subclass of " + parentClass.getSimpleName());
			return false;
		}
		return true;
	}

	private boolean testMethodNamesForClass(String className, String... expectedMethodNames) throws ClassNotFoundException {
		Class<?> clazz = Class.forName("assignment1." + className);
		Method[] methods = clazz.getDeclaredMethods();
		Map<String, Method> methodMap = new HashMap<>();
		boolean allMethodsExist = true;
		// Add all declared methods to a map for quick lookup
		for (Method method : methods) {
			methodMap.put(method.getName(), method);
		}

		// Check if each expected method name exists and print those that are missing
		for (String methodName : expectedMethodNames) {
			if (!methodMap.containsKey(methodName)) {
				System.out.println("Missing expected method name in class " + className + ": " + methodName);
				allMethodsExist = false;
			}
		}
		return allMethodsExist;
	}
	public static boolean checkFieldsForClass(String className, Class<?>... expectedFieldTypes) {
		boolean classFieldsExist = true;
		try {
			Class<?> clazz = Class.forName("assignment1." + className);
			Field[] fields = clazz.getDeclaredFields();

			Set<Class<?>> expectedTypesSet = new HashSet<>();
			for (Class<?> expectedFieldType : expectedFieldTypes) {
				expectedTypesSet.add(expectedFieldType);
			}

			for (Field field : fields) {
				if (expectedTypesSet.contains(field.getType())) {
					expectedTypesSet.remove(field.getType());
				}
			}
			if(!expectedTypesSet.isEmpty()) {
				System.out.println("Missing expected declared field(s) in class " + className + ": " + expectedTypesSet);
				classFieldsExist = false;
			}
		} catch (ClassNotFoundException e) {
			// Handle class not found exception here if needed
			return false;
		}
		return classFieldsExist;
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Insect fields")
	void testInsectFields() {
		assertTrue(checkFieldsForClass("assignment1.Insect", Tile.class, int.class), "assignment1.Insect should have private fields assignment1.Tile position and int health");
	}
	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Hornet fields")
	void testHornetFields() {
		assertTrue(checkFieldsForClass("assignment1.Hornet", int.class, int.class, boolean.class, int.class), "assignment1.Hornet should have private fields int attackDamage, int numOfQueens, boolean isQueen, int BASE_FIRE_DMG");
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.HoneyBee fields")
	void testHoneyBeeFields() {
		assertTrue(checkFieldsForClass("assignment1.HoneyBee", double.class, int.class), "assignment1.HoneyBee should have private fields double cost and int health");
	}
	@Test
	@Tag("score:1")
	@DisplayName("Test presence of two public static fields BASE_HEALTH and BASE_COST")
	void testBusyBeeFields() throws NoSuchFieldException {
		// presence of the two public static fields BASE_HEALTH and BASE_COST 1pt
		Field baseHealth = BusyBee.class.getDeclaredField("BASE_HEALTH");
		assertTrue(Modifier.isPublic(baseHealth.getModifiers()));
		assertTrue(Modifier.isStatic(baseHealth.getModifiers()));
		assertEquals(int.class, baseHealth.getType());

		Field baseCost = BusyBee.class.getDeclaredField("BASE_COST");
		assertTrue(Modifier.isPublic(baseCost.getModifiers()));
		assertTrue(Modifier.isStatic(baseCost.getModifiers()));
		assertEquals(int.class, baseCost.getType());


	}

	// PRESENCE TESTS
	@Test
	@Tag("score:1")
	@DisplayName("Test presence of two public static fields BASE_HEALTH and BASE_COST and int")
	void testAngryBeeFields() throws NoSuchFieldException {
		// presence of the two public static fields BASE_HEALTH and BASE_COST 1pt
		Field baseHealth = AngryBee.class.getDeclaredField("BASE_HEALTH");
		assertTrue(Modifier.isPublic(baseHealth.getModifiers()));
		assertTrue(Modifier.isStatic(baseHealth.getModifiers()));
		assertEquals(int.class, baseHealth.getType());

		Field baseCost = AngryBee.class.getDeclaredField("BASE_COST");
		assertTrue(Modifier.isPublic(baseCost.getModifiers()));
		assertTrue(Modifier.isStatic(baseCost.getModifiers()));
		assertEquals(int.class, baseCost.getType());
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.SwarmOfHornets fields")
	void testSwarmOfHornetsFields() throws NoSuchFieldException {
		assertTrue(checkFieldsForClass("assignment1.SwarmOfHornets", Hornet[].class, int.class, double.class), "assignment1.SwarmOfHornets should have private fields assignment1.Hornet[] hornets, int numOfHornets, double QUEEN_BOOST");
		Field queenBoost = SwarmOfHornets.class.getDeclaredField("QUEEN_BOOST");
		assertTrue(Modifier.isPublic(queenBoost.getModifiers()));
		assertTrue(Modifier.isStatic(queenBoost.getModifiers()));
		assertEquals(double.class, queenBoost.getType());
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Tile fields")
	void testTileFields() throws NoSuchFieldException {
		assertTrue(checkFieldsForClass("assignment1.Tile", int.class, boolean.class, boolean.class, boolean.class, Tile.class, Tile.class, SwarmOfHornets.class, HoneyBee.class), "assignment1.Tile should have private fields int food, boolean isOnFire, boolean isHive, boolean isNest, assignment1.Tile next, assignment1.Tile previous, assignment1.SwarmOfHornets swarm, assignment1.HoneyBee bee");
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test that assignment1.Insect Class has abstract method takeAction")
	void testAbstractMethodInsect() {
		// Test that assignment1.Insect Class has abstract method takeAction
		boolean hasAbstractMethodTakeAction = false;
		for (Method method : Insect.class.getDeclaredMethods()) {
			if(method.getName().equals("takeAction") && Modifier.isAbstract(method.getModifiers())) {
				hasAbstractMethodTakeAction = true;
				break;
			}
		}
		assertTrue(hasAbstractMethodTakeAction, "assignment1.Insect should have an abstract method takeAction..");
	}
	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.HoneyBee field HIVE DAMAGE REDUCTION")
	void testHoneyBeeFields2() throws NoSuchFieldException {
		// check that HIVE_DMG_REDUCTION is static
		Field hiveDmgReductionField = HoneyBee.class.getDeclaredField("HIVE_DMG_REDUCTION");
		assertTrue(Modifier.isPublic(hiveDmgReductionField.getModifiers()));
		assertTrue(Modifier.isStatic(hiveDmgReductionField.getModifiers()));
		assertEquals(double.class, hiveDmgReductionField.getType());
	}
	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.BusyBee field AMOUNT COLLECTED")
	void testBusyBeeFields2() throws NoSuchFieldException {
		// check that BASE AMOUNT COLLECTED is static
		Field baseAmountCollected = BusyBee.class.getDeclaredField("BASE_AMOUNT_COLLECTED");
		assertTrue(Modifier.isPublic(baseAmountCollected.getModifiers()));
		assertTrue(Modifier.isStatic(baseAmountCollected.getModifiers()));
		assertEquals(int.class, baseAmountCollected.getType());
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.FireBee fields")
	void testFireBeeFields() throws NoSuchFieldException {
		assertTrue(checkFieldsForClass("assignment1.FireBee", int.class, int.class, int.class), "assignment1.FireBee should have public static int BASE_HEALTH, public static int BASE_COST, private int maxRange");
		Field baseHealth = FireBee.class.getDeclaredField("BASE_HEALTH");
		assertTrue(Modifier.isPublic(baseHealth.getModifiers()));
		assertTrue(Modifier.isStatic(baseHealth.getModifiers()));
		assertEquals(int.class, baseHealth.getType());

		Field baseCost = FireBee.class.getDeclaredField("BASE_COST");
		assertTrue(Modifier.isPublic(baseCost.getModifiers()));
		assertTrue(Modifier.isStatic(baseCost.getModifiers()));
		assertEquals(int.class, baseCost.getType());
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.SniperBee fields")
	void testSniperBeeFields() throws NoSuchFieldException {
		assertTrue(checkFieldsForClass("assignment1.SniperBee", int.class, int.class, int.class, int.class), "Sniper Bee should have private fields int attackDamage, int piercing power, as well as public static int BASE_HEALTH, public static int BASE_COST");
		Field baseHealth = SniperBee.class.getDeclaredField("BASE_HEALTH");
		assertTrue(Modifier.isPublic(baseHealth.getModifiers()));
		assertTrue(Modifier.isStatic(baseHealth.getModifiers()));
		assertEquals(int.class, baseHealth.getType());

		Field baseCost = SniperBee.class.getDeclaredField("BASE_COST");
		assertTrue(Modifier.isPublic(baseCost.getModifiers()));
		assertTrue(Modifier.isStatic(baseCost.getModifiers()));
		assertEquals(int.class, baseCost.getType());
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Hornet fields")
	void testHornetFields2() throws NoSuchFieldException {
		assertTrue(checkFieldsForClass("assignment1.Hornet", int.class, int.class, boolean.class, int.class), "assignment1.Hornet should have private fields int attackDamage, int numOfQueens, boolean isQueen, int numOfQueens and also a public static int BASE_FIRE_DMG");
		Field baseFireDmg = Hornet.class.getDeclaredField("BASE_FIRE_DMG");
		assertTrue(Modifier.isPublic(baseFireDmg.getModifiers()));
		assertTrue(Modifier.isStatic(baseFireDmg.getModifiers()));
		assertEquals(int.class, baseFireDmg.getType());
	}

	// SKELETON TEST
	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Insect getPosition()")
	void testInsectGetPosition() {
		// test that getPosition returns a assignment1.Tile
		try {
			String[] fieldNames = getPathFieldsNames();
			String onPath = fieldNames[0];
			Tile t = new Tile();
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(t, true);
			Insect insect = new Hornet(t, 10, 2);

			// Then
			assertNotNull(insect.getPosition(), "Position should not be null");
			assertTrue(insect.getPosition() instanceof Tile, "Position should be an instance of assignment1.Tile");
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Insect getHealth()")
	void testInsectGetHealth() {
		try {
			String[] fieldNames = getPathFieldsNames();
			String onPath = fieldNames[0];
			Tile t = new Tile();
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(t, true);
			Insect insect = new Hornet(t, 10, 2);
			assertEquals(10, insect.getHealth(), "Health should be 10");
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Insect setPosition()")
	void testInsectSetPosition() {
		try {
			String[] fieldNames = getPathFieldsNames();
			String onPath = fieldNames[0];
			Tile t = new Tile();
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(t, true);
			Insect insect = new Hornet(t, 10, 2);

			Tile tile = new Tile();
			insect.setPosition(tile);
			assertEquals(tile, insect.getPosition(), "Position should be set to the given tile");
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}
	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Hornet constructor initializing fields of super class")
	void testHornetConstructor1() {
		try {
			String[] fieldNames = getPathFieldsNames();
			String onPath = fieldNames[0];
			Tile t = new Tile();
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(t, true);
			Insect hornet = new Hornet(t, 10, 2);

			assertEquals(t, hornet.getPosition(), "Position should be initialized to the input tile");
			assertEquals(10, hornet.getHealth(), "Health was not initialized correctly");
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}

	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Hornet constructor initializing fields of current class")
	void testHornetConstructor2() throws IllegalAccessException {
		try {
			String[] fieldNames = getPathFieldsNames();
			String onPath = fieldNames[0];
			Tile t = new Tile();
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(t, true);
			Insect hornet = new Hornet(t, 10, 20);

			boolean attackInitializedCorrectly = false;
			Field[] declaredFields = Hornet.class.getDeclaredFields();
			for (Field field : declaredFields) {
				if (field.getType() == int.class) {
					field.setAccessible(true);
					int intValue = (int) field.get(hornet);
					if (intValue == 20) {
						attackInitializedCorrectly = true;
						break;
					}
				}
			}
			assertTrue(attackInitializedCorrectly, "Attack damage was not initialized correctly");
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.HoneyBee getCost()")
	void testHoneyBeeGetCost() {
		Tile t = new Tile();
		BusyBee.BASE_COST = 2;
		HoneyBee bee = new BusyBee(t);
		assertEquals(2, bee.getCost(), "Cost should be 2");
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.BusyBee constructor calls super constructor")
	void testBusyBeeConstructor1() {
		// Test that the constructor calls the super constructor 1pt
		Tile t = new Tile();
		BusyBee.BASE_COST = 2;
		BusyBee.BASE_HEALTH = 5;
		BusyBee bee = new BusyBee(t);
		assertEquals(t, bee.getPosition(), "Position should be initialized to the input tile");
		assertEquals(5, bee.getHealth(), "Health was not initialized correctly");
		assertEquals(2, bee.getCost(), "Cost was not initialized correctly");
	}

	// BASIC GAME TEST

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Insect takeDamage(): update hp")
	void testTakeDamage1() {
		Tile t = new Tile();
		t.buildHive();
		BusyBee.BASE_HEALTH = 5;
		Insect bee = new BusyBee(t);
		HoneyBee.HIVE_DMG_REDUCTION = 10.0/100.0;
		bee.takeDamage(2);
		assertEquals(4, bee.getHealth(), "health points are not updated correctly");
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Insect takeDamage(): remove insect")
	void testTakeDamage2() {
		Tile t = new Tile();
		t.buildHive();
		BusyBee.BASE_HEALTH = 5;
		Insect bee = new BusyBee(t);
		bee.takeDamage(6);
		assertEquals(null, bee.getPosition(), "insect is not removed correctly");

	}

	@Test
	@Tag("score:1")
	@DisplayName("Test takeAction(): stings")
	void testHornetTakeAction1() {
		try {
			String[] fieldNames = getPathFieldsNames();
			String onPath = fieldNames[0];
			Tile t = new Tile();
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(t, true);

			BusyBee.BASE_HEALTH = 5;
			HoneyBee bee = new BusyBee(t);
			Hornet hornet = new Hornet(t, 10, 2);
			hornet.takeAction();

			assertEquals(3, bee.getHealth(), "assignment1.Hornet: takeAction() does the wrong amount of damage to bee. Expected: 3, actual: " + bee.getHealth());
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.Hornet takeAction(): assignment1.Hornet wins")
	void testHornetTakeAction2() {
		try {
			String[] fieldNames = getPathFieldsNames();
			String onPath = fieldNames[0];
			Tile t = new Tile();
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(t, true);

			HoneyBee bee = new BusyBee(t);
			Hornet hornet = new Hornet(t, 10, 2);
			assertTrue(hornet.takeAction(), "assignment1.Hornet: takeAction() returns the wrong value. Expected: true, actual: false");
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}
	@Test
	@Tag("score:1")
	@DisplayName("Test assignment1.BusyBee takeAction()")
	void testBusyBeeTakeAction() {
		Tile t = new Tile();
		BusyBee.BASE_AMOUNT_COLLECTED = 2;
		BusyBee bee = new BusyBee(t);

		assertTrue(bee.takeAction(), "takeAction() returns the wrong value. Expected: true, actual: false");
		int food = t.collectFood();
		assertEquals(2, food, "takeAction() adds wrong amount of food to the tile. Expected: 2, actual:" + food);
	}

	// SPECIAL UNITS TEST

	@Test
	@Tag("score:1")
	@DisplayName("Test constructor")
	void testConstructorFireBee() {
		try {
			Tile aTile = new Tile();
			FireBee testFireBee = new FireBee(aTile, 1);

			// Dynamically find the maxRange field
			Field[] declaredFields = FireBee.class.getDeclaredFields();
			boolean maxRangeInitializedCorrectly = false;

			for (Field field : declaredFields) {
				if (field.getType() == int.class) {
					field.setAccessible(true);
					int intValue = (int) field.get(testFireBee);
					maxRangeInitializedCorrectly = true;
					break;
				}
			}

			assertTrue(maxRangeInitializedCorrectly, "maxRange field not found or initialized correctly");
			assertEquals(aTile, testFireBee.getPosition(), "Constructor did not instantiate the field position correctly");
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}


	private static String[] getPathFieldsNames() {
		String[] names = new String[3]; // onPath, towH, towN
		Tile towH = new Tile();
		Tile towN = new Tile();
		Tile sample = new Tile (0, false, false, true, towH, towN, null, null) ;
		try {
			Field[] declaredFields = Tile.class.getDeclaredFields();
			for (Field f : declaredFields) {
				if (f.getType() == boolean.class) {
					f.setAccessible(true);
					boolean bValue = (boolean) f.get(sample);
					if (bValue == true) {
						names[0] = f.getName();
					}
				} else if (f.getType() == Tile.class) {
					f.setAccessible(true);
					Tile t = (Tile) f.get(sample);
					if (t == towH)
						names[1] = f.getName();
					else if (t == towN)
						names[2] = f.getName();
				}
			}
			return names;
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return names;
	}


	@Test
	@Tag("score:1")
	@DisplayName("Test takeAction() modified")
	void testModifiedTakeActionFireBee() {
		String[] names = getPathFieldsNames();
		assertNotNull(names);
		for (String s : names)
			assertNotNull(s);

		String onPath = names[0];
		String towHive = names[1];
		String towNest = names[2];

		try {
			Tile currentTile = new Tile();
			Tile previousTile = new Tile();
			Field currentPathField = Tile.class.getDeclaredField(onPath);
			currentPathField.setAccessible(true);
			currentPathField.set(currentTile, true);
			currentPathField.set(previousTile, true);

			Field towardHiveField = Tile.class.getDeclaredField(towHive);
			towardHiveField.setAccessible(true);
			towardHiveField.set(currentTile, previousTile);

			Hornet.BASE_FIRE_DMG = 1;
			Hornet aHornet = new Hornet(currentTile, 4, 1);
			currentTile.setOnFire();
			assertTrue(aHornet.takeAction(), "takeAction() should be run successfully, but didn't");
			assertEquals(aHornet.getHealth(), 3, "Hornets position on a tile on fire should take damage at every action taken, but didn't");
		}
		catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
	}
	@Test
	@Tag("score:1")
	@DisplayName("Test single occupied tiles within range")
	void testSingleTilesFireBee() {
		String[] names = getPathFieldsNames();
		assertNotNull(names);
		for (String s : names)
			assertNotNull(s);

		String onPath = names[0];
		String towHive = names[1];
		String towNest = names[2];

		try {
			Tile previousTile = new Tile();
			Tile currentTile = new Tile();

			Field towardNestField = Tile.class.getDeclaredField(towNest);
			towardNestField.setAccessible(true);
			towardNestField.set(currentTile, previousTile);
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(currentTile, true);
			onPathField.set(previousTile, true);
			Hornet aHornet = new Hornet(previousTile, 2, 2);

			FireBee testFirebee = new FireBee(currentTile,2);
			boolean actionTaken = testFirebee.takeAction();
			assertTrue(actionTaken, "takeAction() did not run successfully - single tile was not attacked");
			assertTrue(previousTile.isOnFire(), "attacked tile should be on fire but isn't");
		}
		catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test multiple occupied tile within range")
	void testMultTileFireBee() {
		String[] names = getPathFieldsNames();
		assertNotNull(names);
		for (String s : names)
			assertNotNull(s);

		String onPath = names[0];
		String towHive = names[1];
		String towNest = names[2];

		try {
			//Path will be current -> previous 1 -> previous 2
			Tile previousTile1 = new Tile();
			Tile previousTile2 = new Tile();
			Tile currentTile = new Tile();

			Field towardNestField = Tile.class.getDeclaredField(towNest);
			towardNestField.setAccessible(true);
			towardNestField.set(previousTile1, previousTile2);
			towardNestField.set(currentTile, previousTile1);

			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(currentTile, true);
			onPathField.set(previousTile1, true);
			onPathField.set(previousTile2, true);
			previousTile1.setOnFire();
			Hornet aHornet = new Hornet(previousTile2, 2, 2);
			Hornet anotherHornet = new Hornet(previousTile1, 2, 2);

			FireBee testFirebee = new FireBee(currentTile,2);
			boolean actionTaken = testFirebee.takeAction();
			assertTrue(actionTaken, "takeAction() did not run successfully - non-fired tile within multiple ones was not attacked");
			assertTrue(previousTile1.isOnFire(), "this tile should be on fire but isn't. Check setOnFire()");
			assertTrue(previousTile2.isOnFire(), "attacked tile should be on fire but isn't");
		}
		catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test no target tile within range")
	void testNoTileWithinTargetFireBee() {
		String[] names = getPathFieldsNames();
		assertNotNull(names);
		for (String s : names)
			assertNotNull(s);

		String onPath = names[0];
		String towHive = names[1];
		String towNest = names[2];

		try {
			// Path currentTile -> previousTile1 -> previousTile2 -> previousTile3
			Tile previousTile1 = new Tile();
			Tile previousTile2 = new Tile();
			Tile previousTile3 = new Tile();
			Tile currentTile = new Tile();

			Field towardNestField = Tile.class.getDeclaredField(towNest);
			towardNestField.setAccessible(true);
			towardNestField.set(previousTile2, previousTile3);
			towardNestField.set(previousTile1, previousTile2);
			towardNestField.set(currentTile, previousTile1);

			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(currentTile, true);
			onPathField.set(previousTile1, true);
			onPathField.set(previousTile2, true);
			onPathField.set(previousTile3, true);

			previousTile1.setOnFire();
			//previousTile2.setOnFire();

			Hornet aHornet = new Hornet(previousTile3, 2, 2);

			FireBee testFirebee = new FireBee(currentTile,2);
			boolean actionTaken = testFirebee.takeAction();
			assertFalse(actionTaken, "takeAction() should not run since no target within range but did");
			assertTrue(previousTile1.isOnFire(), "fired tile should stay on fire");
			assertFalse(previousTile2.isOnFire(), "the second tile should not be on fire");
			assertFalse(previousTile3.isOnFire(), "non-fired outside range tile should not be on fire");
		}
		catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
	}

	// SWARM OF HORNETS TEST

	@Test
	@Tag("score:1")
	@DisplayName("Test getHornets returns an empty array for a newly created assignment1.SwarmOfHornets")
	void testSwarmOfHornetsConstructor() {
		SwarmOfHornets swarm = new SwarmOfHornets();
		assertEquals(0, swarm.getHornets().length);
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test sizeOfSwarm returns 0 for a new assignment1.SwarmOfHornets")
	void testSizeOfSwarm() {
		SwarmOfHornets swarm = new SwarmOfHornets();
		assertEquals(0, swarm.sizeOfSwarm());
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test addHornet adds a assignment1.Hornet to the swarm")
	void testAddHornet() {
		try {
			String[] fieldNames = getPathFieldsNames();
			String onPath = fieldNames[0];
			Tile t = new Tile();
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(t, true);

			Hornet hornet = new Hornet(t, 10, 2);
			SwarmOfHornets swarm = new SwarmOfHornets();
			swarm.addHornet(hornet);

			assertEquals(1, swarm.sizeOfSwarm());
		} catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test getFirstHornet returns the firstly added assignment1.Hornet")
	void testGetFirstHornet() {
		try {
			String[] fieldNames = getPathFieldsNames();
			String onPath = fieldNames[0];
			Tile t = new Tile();
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(t, true);

			Hornet hornet1 = new Hornet(t, 10, 2);
			Hornet hornet2 = new Hornet(t, 20, 2);
			SwarmOfHornets swarm = new SwarmOfHornets();
			swarm.addHornet(hornet1);
			swarm.addHornet(hornet2);

			assertEquals(hornet1, swarm.getFirstHornet());
		} catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
	}

	@Test
	@Tag("score:1")
	@DisplayName("Test removeHornet returns the correct status")
	void testRemoveHornet() {
		try {
			String[] fieldNames = getPathFieldsNames();
			String onPath = fieldNames[0];
			Tile t = new Tile();
			Field onPathField = Tile.class.getDeclaredField(onPath);
			onPathField.setAccessible(true);
			onPathField.set(t, true);

			Hornet hornet = new Hornet(t, 10, 2);
			SwarmOfHornets swarm = new SwarmOfHornets();
			assertFalse(swarm.removeHornet(hornet));

			swarm.addHornet(hornet);
			assertTrue(swarm.removeHornet(hornet));
		} catch(ReflectiveOperationException e){
			e.printStackTrace();
		}
	}

	// TILE TEST
	/**
	 * The test case to test the default constructor of assignment1.Tile
	 * It should not be a bee hive or a nest and it should not be on the path
	 */
	@Test
	@Tag("score:1")
	@DisplayName("Testing the default constructor(no input) of assignment1.Tile")
	public void testTileBasicConstructor() {
		Tile defaultTile = new Tile();
		//it should not be a hive
		assertFalse(defaultTile.isHive());
		//it should not be a nest
		assertFalse(defaultTile.isNest());
		//it should not on the path
		assertFalse(defaultTile.isOnThePath());
		//it should not contain bee
		assertNull(defaultTile.getBee());
		//it should not contain hornet
		assertNull(defaultTile.getHornet());
		//it should contain any food
		assertEquals(0, defaultTile.collectFood());
	}
	
	@Test
	@Tag("score:0")
	@DisplayName("Testing the basic method buildHive() of assignment1.Tile")
	public void testTileBasicMethod_buildHive(){
		Tile a = new Tile();
		a.buildHive();
		assertTrue(a.isHive());
	}
	
	@Tag("score:0")
	@DisplayName("Testing the basic method buildNest() of assignment1.Tile")
	@Test
	public void testTileBasicMethod_buildNest(){
		Tile a = new Tile();
		a.buildNest();

		assertTrue(a.isNest());
	}


	/**
	 * Test case for constructing a assignment1.Tile with the second constructor
	 *
	 */
	@Test
	@Tag("score:1")
	@DisplayName("Testing the constructor (with input) of assignment1.Tile")
	public void testTileConstructorWithInput() {
		Tile tHive = new Tile();
		Tile tNest = new Tile();
		HoneyBee h = new BusyBee(tHive);
		SwarmOfHornets s = new SwarmOfHornets();
		Tile tile = new Tile(10, true, false, true, tHive, tNest, h, s);
		//Used to check the number of boolean variable that is true , it should be 2 as the input
		int trueCount = 0;
		boolean field_tHive=false, field_tNest = false, field_HoneyBee = false,field_SwarmOfHornet = false,field_food = false;
		try {

			for (Field field : tile.getClass().getDeclaredFields()) {
				field.setAccessible(true); // Make private accessible.
				Object value = field.get(tile);
				if (value != null) {
					if (value instanceof Integer) {
						if((Integer)value == 10){
							field_food = true;
						}
					} else if (value instanceof Boolean) {
						if ((Boolean) value) trueCount++;
					} else if (value instanceof Tile) {
						if ((Tile) value == tHive) field_tHive = true;
						if ((Tile) value == tNest) field_tNest = true;
					} else if (value instanceof HoneyBee) {
						if ((HoneyBee) value == h) field_HoneyBee = true;
					} else if (value instanceof SwarmOfHornets) {
						if ((SwarmOfHornets) value == s) field_SwarmOfHornet = true;
					}
				}
			}
			assertTrue(field_food&&field_tHive&&field_HoneyBee&&field_tNest&&field_SwarmOfHornet&&(trueCount==2),"The second constructor of assignment1.Tile class is not implemented correctly, some fields have incorrect value");


		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}


	}

	/**
	 * This is the basic test case for createPath() method in assignment1.Tile class
	 * It will simply check whether the property of the tile is updated correctly
	 */
	@Test
	@Tag("score:1")
	@DisplayName("Testing the createPath() of assignment1.Tile, ")
	public void testCreatePath(){
		Tile tHive = new Tile();
		Tile tNest = new Tile();
		Tile tile = new Tile();
		tile.createPath(tHive,tNest);
		int trueCount = 0;
		boolean field_tHive = false, field_tNest = false;
		try {

			for (Field field : tile.getClass().getDeclaredFields()) {
				field.setAccessible(true); // Make private accessible.
				Object value = field.get(tile);
				if (value != null) {
					if (value instanceof Boolean) {
						if ((Boolean) value) trueCount++;
					} else if (value instanceof Tile) {
						if ((Tile) value == tHive) field_tHive = true;
						if ((Tile) value == tNest) field_tNest = true;
					}
				}
			}
			assertTrue(field_tHive&&field_tNest&&(trueCount==1), "The creatPath method do not set the field in tile properly");


		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}


	}

	/**
	 * This test case is used to test addInsect() method
	 * We will create a tile that is on the path and add two hornet to it, which should be successful
	 * We will add a bee to the same tile which should also be successful
	 */

	@Test
	@Tag("score:1")
	@DisplayName("Testing the addInsect() of assignment1.Tile in successful scenario  ")
	public void testAddInsect_success(){
		Tile t1 = new Tile();
		Tile t2= new Tile(10, false, false, true, null, null, null, new SwarmOfHornets());
		Tile tile = new Tile(10, false, false, true, t1, t2, null, new SwarmOfHornets());

		Hornet h1 = new Hornet(t2,10,999);
		Hornet h2 = new Hornet(t2,10,999);
		HoneyBee bee1 = new BusyBee(t1);

		boolean addFirstHornet = tile.addInsect(h1);
		boolean addSecondHornet = tile.addInsect(h2);
		boolean addFirstBee = tile.addInsect(bee1);
		assertTrue(addFirstHornet && addFirstBee && addSecondHornet &&(tile.getNumOfHornets()==2), "The addInsect() method does not update the assignment1.Tile correctly");


	}

	/**
	 * This is the test case to test addInsect() method under two unsuccessful scenario
	 * Add a bee to a tile that already has a bee
	 * Add hornets to a tile that is not on the path
	 */

	@Test
	@Tag("score:1")
	@DisplayName("Testing the addInsect() of assignment1.Tile in an un successful scenario  ")
	public void testAddInsect_unsuccess() {
		Tile t1 = new Tile();
		Tile t = new Tile();
		Tile t_withbee = new Tile();
		Tile t2 = new Tile(10, false, false, true, null, null, null, new SwarmOfHornets());
		Tile tile = new Tile(10, false, false, false, t1, t2, null, new SwarmOfHornets());

		Hornet h1 = new Hornet(t2, 10, 999);
		Hornet h2 = new Hornet(t2, 10, 999);
		HoneyBee bee1 = new BusyBee(t1);
		HoneyBee bee2 = new BusyBee(t_withbee);

		boolean addFirstHornet = tile.addInsect(h1);
		boolean addSecondHornet = tile.addInsect(h2);
		boolean addFirstBee = t_withbee.addInsect(bee1);
		assertTrue(!addFirstHornet && !addFirstBee && !addSecondHornet, "The addInsect() method does not update the assignment1.Tile correctly");

	}
}
