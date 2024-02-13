

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

/*
 * Main game loop
 * Author: Lancer Guo + Giulia Alberini
 */

import javax.swing.BoxLayout;
import javax.swing.Timer;

enum EnumBeeType {
	busyBee,
	angryBee,
	// SPECIAL UNITS
	fireBee,
	sniperBee,
	//tankyBee
}

public class BeeColony extends Frame {
	private static final Random RND_GEN = new Random();

	private static final String[] MSG_BEE_DEFEAT = {"A somber moment on the battlefield: %d brave "
			+ "bees have sacrificed their lives in defense of the hive.\n", "Your valiant bees fought bravely, "
					+ "but alas, %d fell victim to the stingers of the invading hornets this round.\n", "The buzz of sorrow "
							+ "fills the air as %d of your fiercest bees meet their end, sacrificing themselves for the safety of the hive.\n"};
	private static final String[] MSG_HORNET_DEFEAT = {"A resounding hum of victory! Your bee forces deliver a decisive sting, "
			+ "eliminating %d hornets in this round.\n", "The hive stands strong as your bees swarm the enemy, triumphing over "
					+ "%d hornets with their relentless attack.\n", "The air is clear as the threat diminishes — %d hornets "
							+ "succumb to the might of your formidable bees.\n"};


	private static final int RES_X = 900;
	private static final int RES_Y = 600;

	// costs
	private static final int BUSYBEE_COST = 4;
	private static final int ANGRYBEE_COST = 3;
	// SPECIAL UNITS
	//private static final int TANKYBEE_COST = 6;
	private static final int FIREBEE_COST = 6;
	private static final int SNIPERBEE_COST = 6;
	
	// health
	private static final int BUSYBEE_HEALTH = 20;
	private static final int ANGRYBEE_HEALTH = 100;
	private static final int FIREBEE_HEALTH = 80;
	private static final int SNIPERBEE_HEALTH = 70;

	// abilities
	private static final int BUSYBEE_AMOUNT_COLLECTED = 2;
	private static final int ANGRYBEE_ATTACK = 10;
	// SPECIAL UNITS    
	//private static final int TANKYBEE_ATTACK = 10;
	//private static final int TANKYBEE_ARMOR = 10;
	private static final int FIREBEE_RANGE = 3;
	private static final int SNIPERBEE_PIERCING = 5;
	private static final int SNIPERBEE_ATTACK = 3;
	private static final int HORNET_FIRE_DMG = 5;
	
	
	private static final double HIVE_DMG_REDUCTION = 10.0/100.0;
	private static final double HORNET_QUEENBOOST = 10.0/100.0;
	
	
	


	private static final int HORNET_ATTACK = 10;
	private static final int HORNET_HEALTH = 100;

	static final int STARTING_FOOD = 5;
	static final int FOOD_PER_TURN = 1;

	private boolean _inSelection = false;
	private EnumBeeType _selectedBeeType;

	private int canvasX;
	private int canvasY;
	private int canvasWidth;
	private int canvasHeight;
	private static GameCanvas _boardCanvas;
	private static Label _msgPanel;
	private static Label _foodPanel;
	private static TextArea _logPanel;
	private static Timer tick;
	private static Button busyBeeBtn;
	private static Button angryBeeBtn;
	// SPECIAL UNITS
	//private static Button tankyBeeBtn;
	private static Button fireBeeBtn;
	private static Button sniperBeeBtn;
	private static Button startBtn;
	private static Button endBtn;
	private static Button spawnBtn;

	private long _frameCount;
	private int _timePerFrame = 5000;	// 5 seconds per frame, increase to slow down the game
	private boolean _gameOver;

	private int _totalFood;

	private LinkedList<Insect> deadInsects;
	private static Map map;
	private static LinkedList<Hornet> hornets;
	private static LinkedList<HoneyBee> bees;
	private static StringBuffer logBuffer;
	private static int hornetsCount;
	private static int queenIndex;

	public BeeColony(){
		initUI();
		initGame();
	}

	/*************************** Main ***************************/
	public static void main(String args[]){
		BeeColony game = new BeeColony();
	}


	/****************** Game Logic ******************/

	private void initMap() {
		map = new Map(canvasWidth, canvasHeight);
		if (map.build()) {
			map.draw(_boardCanvas);
		}
		else {
			endGame();
			System.out.println("Cannot initilize map");
		}
	}

	private void initGame() {
		_frameCount = 0;
		_gameOver = true;

		//update and show food
		_totalFood = STARTING_FOOD;
		UpdateFood(0);

		//disable and enable UI
		busyBeeBtn.setEnabled(false);
		angryBeeBtn.setEnabled(false);
		// SPECIAL UNITS
		//tankyBeeBtn.setEnabled(false);
		fireBeeBtn.setEnabled(false);
		sniperBeeBtn.setEnabled(false);
		spawnBtn.setEnabled(false);
		startBtn.setEnabled(true);
		endBtn.setEnabled(false);

		initMap();
		
		initBaseAbilities();

		deadInsects = new LinkedList<Insect>();
		hornets = new LinkedList<Hornet>();
		bees = new LinkedList<HoneyBee>();
		logBuffer = new StringBuffer();
		queenIndex = RND_GEN.nextInt(10) + 10;
	}

	// Bees base abilities
	private void initBaseAbilities() {
		HoneyBee.HIVE_DMG_REDUCTION = HIVE_DMG_REDUCTION;
		// Busy bees
		BusyBee.BASE_HEALTH = BUSYBEE_HEALTH;
		BusyBee.BASE_COST = BUSYBEE_COST;
		BusyBee.BASE_AMOUNT_COLLECTED = BUSYBEE_AMOUNT_COLLECTED;

		// Angry Bees
		AngryBee.BASE_HEALTH = ANGRYBEE_HEALTH;
		AngryBee.BASE_COST = ANGRYBEE_COST;
		
		// Fire bees
		FireBee.BASE_HEALTH = FIREBEE_HEALTH;
		FireBee.BASE_COST = FIREBEE_COST;
		Hornet.BASE_FIRE_DMG = HORNET_FIRE_DMG;

		// Sniper bees
		SniperBee.BASE_HEALTH = SNIPERBEE_HEALTH;
		SniperBee.BASE_COST = SNIPERBEE_COST;

		SwarmOfHornets.QUEEN_BOOST = HORNET_QUEENBOOST;
	}

	private void startGame() {
		if (_gameOver == false) return;

		_gameOver = false;

		toggleInput(true);

		tick = new Timer(_timePerFrame, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_frameCount++;

				updateUI();
				simulate();
				render();
			}
		});
		tick.start();

	}

	private void endGame() {
		if (_gameOver == true) return;
		tick.stop();
		resetMap();
		updateUI();
		_boardCanvas.repaint();

		_gameOver = true;
		_frameCount = 0;
		toggleInput(false);
	}

	// spawn one hornet
	private void spawnHornet() {
		//spawn hornets at the nest
		hornetsCount++;
		Hornet readyToSpawn = new Hornet(map.getNestTile(), HORNET_HEALTH, HORNET_ATTACK);
		if (hornetsCount == queenIndex)
			readyToSpawn.promote();
		hornets.add(readyToSpawn);
		if (readyToSpawn.isTheQueen())
			logMessage("The air grows heavy as the Queen Hornet enters the battlefield.\n");
		else 
			logMessage("The enemy's forces replenish with the appearance of a new hornet!\n");
	}


	// game logic
	private void simulate() {
		//TODO: simulate the map based on the rule and update map
		logMessage("\n************* Turn " + _frameCount + " *************\n");

		//for each bees, do actions
		logMessage("The hive stirs with purpose as your diligent bees spring into action!\n");
		for (HoneyBee bee : bees) {
			bee.takeAction();
			//System.out.println(bee.getHealth());
		}

		//update food
		int foodFromBee = 0;
		for (Tile[] rowOfTiles : map.getAllTiles() ) {
			for (Tile tile : rowOfTiles) {
				foodFromBee += tile.collectFood();
			}
		}
		int food = FOOD_PER_TURN + foodFromBee;
		logMessage("The busy workers return with " + food + " portions of nutrient-rich pollen.\n");
		UpdateFood(food);

		// some hornets might have died after the bees took action
		// remove them to not allow them to take action
		collectDeadHornets(); 

		//System.out.println(_frameCount);
		//for each hornets, do actions
		logMessage("A hostile buzz fills the air as the hornets launch their offensive!\n");
		for (Hornet hornet : hornets) {
			boolean keepFighting = hornet.takeAction();
			if (!keepFighting && hornet.getPosition()!= null && hornet.getPosition().isHive()) {
				collectDeadInsects();
				UpdateMessage("GAME OVER! The hornets triumph.");
				this.endGame();
			}
			//System.out.println(hornet);
		}

		//collect dead insects. Both hornets and bees might have died. 
		collectDeadInsects();

		UpdateLog();
	}

	private void render() {
		_boardCanvas.repaint();
	}

	private Tile calculateSpawnLocation(int x, int y) {
		return map.getTileAtLocation(x, y);
	}

	private boolean spawnBee(Tile tileToSpawn) {
		if (_selectedBeeType == null) return false;

		/* uncomment if you want to limit the player to place bees only on the path
		if (!tileToSpawn.isOnThePath()) {
			_inSelection = false;
			_selectedBeeType = null;
			return false;
		}*/

		switch (_selectedBeeType) {
		case busyBee:
			System.out.println("Spawn busy bee");
			if (_totalFood >= BUSYBEE_COST) {
				UpdateFood(-BUSYBEE_COST);
				bees.add(new BusyBee(tileToSpawn));
				return true;
			}
			break;
		case angryBee:
			System.out.println("Spawn angry bee");
			if (_totalFood >= ANGRYBEE_COST) {
				UpdateFood(-ANGRYBEE_COST);
				bees.add(new AngryBee(tileToSpawn, ANGRYBEE_ATTACK));
				return true;
			}
			break;
			// SPECIAL UNITS
			/*case tankyBee:
                System.out.println("Spawn tanky bee");
                if (_totalFood >= TANKYBEE_COST) {
                    UpdateFood(-TANKYBEE_COST);
                    bees.add(new TankyBee(tileToSpawn, TANKYBEE_ATTACK, TANKYBEE_ARMOR));
                    return true;
                }
                break;*/
		case fireBee:
			System.out.println("Spawn fire bee");
			if (_totalFood >= FIREBEE_COST) {
				UpdateFood(-FIREBEE_COST);
				bees.add(new FireBee(tileToSpawn, FIREBEE_RANGE));
				return true;
			}
			break;
		case sniperBee:
			System.out.println("Spawn sniper bee");
			if (_totalFood >= SNIPERBEE_COST) {
				UpdateFood(-SNIPERBEE_COST);
				bees.add(new SniperBee(tileToSpawn, SNIPERBEE_ATTACK, SNIPERBEE_PIERCING));
				return true;
			}
			break;
		default:
			break;
		}

		UpdateMessage("You do not have enough food!");
		return false;
	}

	private void updateUI() {
		if (_inSelection) {
			spawnBtn.setEnabled(false);
		}
		else {
			spawnBtn.setEnabled(true);
		}

		if (_gameOver) {
			startBtn.setEnabled(true);
			endBtn.setEnabled(false);
		}
		else {
			endBtn.setEnabled(true);
			startBtn.setEnabled(false);
		}
	}

	private void collectDeadInsects() {
		int deadBees = 0;
		int deadHornets = 0;

		for (HoneyBee bee : bees) {
			if (bee.getHealth() <= 0) {
				deadInsects.add(bee);
				deadBees++;
			}
		}
		if (deadBees > 0)
			logMessage(String.format(MSG_BEE_DEFEAT[RND_GEN.nextInt(MSG_BEE_DEFEAT.length)], deadBees));


		for (Hornet hornet : hornets) {
			if (hornet.getHealth() <= 0) {
				deadInsects.add(hornet);
				deadHornets++;
			}
		}
		if (deadHornets > 0)
			logMessage(String.format(MSG_HORNET_DEFEAT[RND_GEN.nextInt(MSG_HORNET_DEFEAT.length)], deadHornets));

		for (Insect insect : deadInsects) {
			if (insect instanceof HoneyBee)
				bees.remove(insect);
			if (insect instanceof Hornet)
				hornets.remove(insect);
		}
		UpdateLog();
		deadInsects.clear();
	}

	private void collectDeadHornets() {
		int deadHornets = 0;
		for (Hornet hornet : hornets) {
			if (hornet.getHealth() <= 0) {
				deadInsects.add(hornet);
				deadHornets++;
			}
		}
		if (deadHornets > 0)
			logMessage(String.format(MSG_HORNET_DEFEAT[RND_GEN.nextInt(MSG_HORNET_DEFEAT.length)], deadHornets));

		for (Insect insect : deadInsects) {
			if (insect instanceof Hornet)
				hornets.remove(insect);
		}
		UpdateLog();
		deadInsects.clear();		
	}

	private void resetMap() {
		for (HoneyBee bee : bees) {
			bee.takeDamage(10000);
		}
		for (Hornet hornet : hornets) {
			hornet.takeDamage(10000);
		}
		collectDeadInsects();
	}

	/**************************** Interface *************************/

	public boolean isGameOver() {
		return _gameOver;
	}

	// This method is used to log message to panel
	public static void logMessage(String s) {
		logBuffer.append(s);
		UpdateLog();
	}

	// This method is necessary to let the game know if any insect is dead
	public static void notifyDeath(Insect insect) {
		if (insect instanceof HoneyBee) {
			bees.remove(insect);
			//System.out.println("remove bee");
		}
		else {
			hornets.remove(insect);
			//System.out.println("remove hornet");
		}
	}



	/************************** UI control *********************/

	public void initUI() {
		_msgPanel = new Label();
		_msgPanel.setBounds(RES_X / 2 -  125, 30, 250,20);
		_msgPanel.setAlignment(1);

		_foodPanel = new Label();
		_foodPanel.setBounds(RES_X - 100, 30, 100, 20);
		_foodPanel.setAlignment(1);

		// close window on button click
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				endGame();
				dispose();
			}
		});

		// create canvas
		canvasX = RES_X / 20;
		canvasY = RES_Y / 8;
		canvasWidth = (int)(RES_X * 0.75);
		canvasHeight = (int)(RES_Y * 0.75);
		_boardCanvas = new GameCanvas(canvasX, canvasY, canvasWidth, canvasHeight);
		_boardCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//if click on the canvas then set inSelection to false
				//System.out.println(e.getX() + " " + e.getY());
				_inSelection = false;
				Tile tile = calculateSpawnLocation(e.getX(), e.getY());
				if (tile != null) { spawnBee(tile); }
			}
		});

		// create button panel
		Panel _buttonPanel = new Panel();
		_buttonPanel.setLayout(new BoxLayout(_buttonPanel, BoxLayout.Y_AXIS));
		_buttonPanel.setBounds(RES_X - 150, 150, 140, RES_Y / 3);
		createButtons(_buttonPanel);

		// create log panel
		_logPanel = new TextArea();
		_logPanel.setSize(400,400);
		_logPanel.setBounds(150, RES_Y - 130, 600, 110);
		_logPanel.setVisible(true);
		_logPanel.setEditable(false);

		add(_logPanel);
		add(_boardCanvas);
		add(_buttonPanel);
		add(_foodPanel);
		add(_msgPanel);
		setSize(RES_X,RES_Y);				//frame size 300 width and 300 height
		setLayout(null);					//no layout manager
		setVisible(true);					//now frame will be visible, by default not visible
	}

	public void UpdateMessage(String s) {
		_msgPanel.setText(s);
	}

	public void UpdateFood(int num) {
		_totalFood += num;
		_foodPanel.setText("Food left: " + Integer.toString(_totalFood));
	}

	public static void UpdateLog() {
		_logPanel.setText(logBuffer.toString());
	}

	private void createButtons(Panel buttonPanel) {

		startBtn = new Button("Start Game");
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});

		endBtn = new Button("End Game");
		endBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endGame();
			}
		});

		spawnBtn = new Button("Spawn Hornet");
		spawnBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spawnHornet();
			}
		});


		// button for honey bee
		busyBeeBtn = new Button(String.format("Busy Bee ($%d)",BUSYBEE_COST));
		busyBeeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//set selected bee type
				//in selection to true
				_selectedBeeType = EnumBeeType.busyBee;
				_inSelection = true;
				UpdateMessage("You selected Busy Bee");
			}
		});

		// button for angry bee
		angryBeeBtn = new Button(String.format("Angry Bee ($%d)", ANGRYBEE_COST));
		angryBeeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//set selected bee type
				//in selection to true
				_selectedBeeType = EnumBeeType.angryBee;
				_inSelection = true;
				UpdateMessage("You selected Angry Bee");
			}
		});

		// button for tanky bee 
		/*
        tankyBeeBtn = new Button(String.format("Tanky Bee ($%d)", TANKYBEE_COST));
        tankyBeeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //set selected bee type
                //in selection to true
                _selectedBeeType = EnumBeeType.tankyBee;
                _inSelection = true;
                UpdateMessage("You selected Tanky Bee");
            }
        });*/

		// button for fire bee
		fireBeeBtn = new Button(String.format("Fire Bee ($%d)", FIREBEE_COST));
		fireBeeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//set selected bee type
				//in selection to true
				_selectedBeeType = EnumBeeType.fireBee;
				_inSelection = true;
				UpdateMessage("You selected Fire Bee");
			}
		});

		// button for sniper bee
		sniperBeeBtn = new Button(String.format("Sniper Bee ($%d)", SNIPERBEE_COST));
		sniperBeeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//set selected bee type
				//in selection to true
				_selectedBeeType = EnumBeeType.sniperBee;
				_inSelection = true;
				UpdateMessage("You selected Sniper Bee");
			}
		});


		buttonPanel.add(startBtn);
		buttonPanel.add(endBtn);
		buttonPanel.add(new Label(" "));
		buttonPanel.add(spawnBtn);
		buttonPanel.add(busyBeeBtn);
		buttonPanel.add(angryBeeBtn);
		// SPECIAL UNITS
		//buttonPanel.add(tankyBeeBtn);
		buttonPanel.add(fireBeeBtn);
		buttonPanel.add(sniperBeeBtn);

	}

	private void toggleInput(boolean b) {
		busyBeeBtn.setEnabled(b);
		angryBeeBtn.setEnabled(b);
		// SPECIAL UNITS
		//tankyBeeBtn.setEnabled(b);
		fireBeeBtn.setEnabled(b);
		sniperBeeBtn.setEnabled(b);
		spawnBtn.setEnabled(b);
	}

}