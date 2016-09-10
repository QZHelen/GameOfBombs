package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import javax.swing.JPanel;

import mapCollection.GridConstants;
import mapCollection.IceMap;
import mapCollection.Map;
import characterCollection.Player;
import characterCollection.PlayerOne;

public class Game extends Canvas implements Runnable, KeyListener{
	
	Player p1;
	Map map;
	public BufferStrategy strategy;
	boolean gameRunning;
	public static int gridWidth;
	public static int gridHeight;
	public static int maxWidth;
	public static int maxHeight;
	public static boolean left,right,up,down;
	public Game(Map map) {
		this.p1 = new PlayerOne((int)Math.floor(gridWidth * .9),(int)Math.floor(gridWidth * .9),gridWidth - (int)Math.floor(gridWidth * .9), map);
		this.map = map;
		
		gameRunning = true;
		setBackground(Color.BLUE); 
	    addKeyListener(this);
	    left = false;
	    right = false;
	    up = false;
	    down = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
	    if (keyCode == KeyEvent.VK_LEFT) {
	    	setKeys('l');
	    	p1.moveLeft();
	    }	else if (keyCode == KeyEvent.VK_RIGHT) {
	    	setKeys('r');
	    	p1.moveRight();
	    }	else if (keyCode == KeyEvent.VK_UP) {
	    	setKeys('u');
	    	p1.moveUp();
	    }	else if (keyCode == KeyEvent.VK_DOWN) {
	    	setKeys('d');
	    	p1.moveDown();
	    }	else if (keyCode == KeyEvent.VK_SPACE) {
	    	setKeys('s');
	    	p1.setBomb();
	    }
	    	
	    System.out.println("pressed " + keyCode);
		
	}
	
	public void setKeys(char key) {
		switch (key){
			case 'l':
				Game.left = true;
				Game.right = false;
				Game.up = false;
				Game.down = false;
				break;
			case 'r':
				Game.left = false;
				Game.right = true;
				Game.up = false;
				Game.down = false;
				break;
			case 'u':
				Game.left = false;
				Game.right = false;
				Game.up = true;
				Game.down = false;
				break;
			case 'd':
				Game.left = false;
				Game.right = false;
				Game.up = false;
				Game.down = true;
				break;
			default:
				//TODO
				break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
	    if (keyCode == KeyEvent.VK_LEFT) 
	    	p1.setDx(0);
	    else if (keyCode == KeyEvent.VK_RIGHT)
	    	p1.setDx(0);
	    else if (keyCode == KeyEvent.VK_UP)
	    	p1.setDy(0);
	    else if (keyCode == KeyEvent.VK_DOWN)
	    	p1.setDy(0);
	    System.out.println("released " + keyCode);
		
	}
	@Override
	public void run() {
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
		while(gameRunning) {
			
			long now = System.nanoTime();
		    long updateLength = now - lastLoopTime;
		    lastLoopTime = now;
		    double delta = updateLength / ((double)OPTIMAL_TIME);

		    // update the game logic
		    updateAll(delta,map);
		    //draw graphics 
		    render();
	        try {
	        	Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
	        } catch (Exception ex) {}
		}
	}

	private void updateAll(double delta, Map map) {
		// TODO Auto-generated method stub
		p1.update(delta,map);
		
	}

	private void render() {
		// TODO Auto-generated method stub
		Graphics g = strategy.getDrawGraphics();
	    g.setColor(Color.CYAN);
	    g.fillRect(0,0,Game.maxWidth,Game.maxHeight);
	    g.setColor(Color.RED);
	    int[][] grids = map.getGrids();
	    for(int i = 0; i < grids.length; i++) {
	    	for(int j = 0; j < grids[0].length; j++) {
	    		if(grids[i][j] == GridConstants.BRICK) {
	    			g.fillRect(j * Game.gridWidth, i * Game.gridHeight, Game.gridWidth, Game.gridHeight);
	    		}
	    	}
	    }
	    g.setColor(Color.BLACK);
	    if(!map.getBombs().isEmpty()) {
	    	if(!map.getBombs().get(0).isExplode()) 
	    		 g.fillOval(map.getBombs().get(0).getX(),map.getBombs().get(0).getY(),map.getBombs().get(0).getWidth(),map.getBombs().get(0).getHeight());
	    }
//	    	map.getBombs().
	    	
	    g.setColor(Color.gray);
	    g.fillRect(p1.getX(),p1.getY(),p1.getWidth(),p1.getHeight());
	    g.dispose();
	    strategy.show();
		
	}
	
}
