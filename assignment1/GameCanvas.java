package assignment1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/*
 * Game board rendering class
 * Author: Lancer Guo + Giulia Alberini
 */

public class GameCanvas extends Canvas {

    final static Color HIVE_COLOR = new Color(255,187,8); // dark yellow
    final static Color NEST_COLOR = Color.black;
    final static Color PATH_COLOR = Color.lightGray;
    final static Color BUSYBEE_COLOR = new Color(247,238,81); // light yellow
    final static Color ANGRYBEE_COLOR = new Color(118,154,249); // light blue
    // SPECIAL UNITS
    final static Color FIREBEE_COLOR = new Color(220,38,127); // magenta
    final static Color SNIPERBEE_COLOR = new Color(97,67,239); // purple
    final static Color TANKYBEE_COLOR = Color.cyan;
    final static Color ON_FIRE_COLOR = new Color(243,122,47); // orange
    final static Color GRASSLAND_COLOR = new Color(148,214,133); //new Color(102,255,102); // light green

    Map _map;

    private boolean _mapGenerated = false;


    public GameCanvas(int canvasX, int canvasY, int canvasWidth, int canvasHeight) {
        //this.setBackground();

        this.setBounds(canvasX, canvasY, canvasWidth, canvasHeight);
    }

    public void registerMap(Map map) {
        _map = map;
    }

    // paint is called automatically every turn
    // update map based on map object status
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);

        if (_map == null) return;

        Tile[][] tiles = _map.getAllTiles();
        int size = _map.getTileSize();

        for (int i=0; i<tiles.length; i++) {
            for (int j=0; j<tiles[i].length; j++) {
                int x = size * i + 1;
                int y = size * j + 1;

                if (tiles[i][j] != null) {
                    if (tiles[i][j].isHive()) {
                        g.setColor(Color.black);
                        g.drawRect(x, y, size, size);
                        g.setColor(HIVE_COLOR);
                        g.fillRect(x, y, size, size);
                    }
                    else if (tiles[i][j].isNest()) {
                        g.setColor(Color.black);
                        g.drawRect(x, y, size, size);
                        g.setColor(NEST_COLOR);
                        g.fillRect(x, y, size, size);
                    }
                    else if (tiles[i][j].isOnThePath() && tiles[i][j].isOnFire()) {
                        g.setColor(Color.black);
                        g.drawRect(x, y, size, size);
                        g.setColor(ON_FIRE_COLOR);
                        g.fillRect(x, y, size, size);
                    } else if (tiles[i][j].isOnThePath()) {
                        g.setColor(Color.black);
                        g.drawRect(x, y, size, size);
                        g.setColor(PATH_COLOR);
                        g.fillRect(x, y, size, size);
                    } else {
                    	g.setColor(Color.BLACK);
                        g.drawRect(x, y, size, size);
                        g.setColor(GRASSLAND_COLOR);
                        g.fillRect(x, y, size, size);
                    }

                    if (tiles[i][j].getBee() != null) {
                        HoneyBee bee = tiles[i][j].getBee();
                        if (bee instanceof BusyBee) {
                            g.setColor(Color.black);
                            g.drawRect(x, y, size, size);
                            g.setColor(BUSYBEE_COLOR);
                            g.fillRect(x, y, size, size);
                        }
                        else if (bee instanceof AngryBee) {
                            g.setColor(Color.black);
                            g.drawRect(x, y, size, size);
                            g.setColor(ANGRYBEE_COLOR);
                            g.fillRect(x, y, size, size);
                        }/*
                        else if (bee instanceof TankyBee) {
                            g.setColor(Color.black);
                            g.drawRect(x, y, size, size);
                            g.setColor(TANKYBEE_COLOR);
                            g.fillRect(x, y, size, size);
                        }*/
                        else if (bee instanceof FireBee) {
                            g.setColor(Color.black);
                            g.drawRect(x, y, size, size);
                            g.setColor(FIREBEE_COLOR);
                            g.fillRect(x, y, size, size);
                        }
                        else if (bee instanceof SniperBee) {
                            g.setColor(Color.black);
                            g.drawRect(x, y, size, size);
                            g.setColor(SNIPERBEE_COLOR);
                            g.fillRect(x, y, size, size);
                        }
                    }
                    if (tiles[i][j].getNumOfHornets() > 0) {
                        String string = "" + tiles[i][j].getNumOfHornets();
                        int fontSize = 20;
                        g.setColor(Color.BLACK);
                     
                        boolean withQueen = swarmWithQueen(tiles[i][j].getHornets());
                        if (withQueen) 
                        	g.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC , fontSize));
                        else
                        	g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
                        
                        g.drawString(string, x + 5, y + size - 5);
                    }
                }
                else {
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, size, size);
                }
            }
        }
    }
    
    private static boolean swarmWithQueen(Hornet[] hornets) {
    	for (int i=0; i<hornets.length; i++) {
    		if(hornets[i].isTheQueen())
    			return true;
    	}
    	return false;
    }

}
