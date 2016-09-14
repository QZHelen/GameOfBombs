package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import mapCollection.GridConstants;
import mapCollection.Map;
import characterCollection.Player;
import characterCollection.PlayerOne;
import gameItemCollection.Bomb;
import gameItemCollection.Fire;

public class Game extends Canvas implements Runnable, KeyListener{
	
	Player p1;
	Player p2;
	Map map;
	public BufferStrategy strategy;
	boolean gameRunning;
	public static int gridWidth;
	public static int gridHeight;
	public static int maxWidth;
	public static int maxHeight;
	public static boolean left,right,up,down;
	public static int panelHeight;
	static TopPanel tPanel;
	static BottomPanel bPanel;
	static Timer godModetimer;
	public static Timer bombPasstimer;
	AssetsManager assetsManager;
	
	public Game(Map map) throws IOException {
		this.p1 = new PlayerOne((int) Math.floor(gridWidth * .9),(int) Math.floor(gridWidth * .9),gridWidth - (int)Math.floor(gridWidth * .9), map);
		this.p2 = new PlayerOne((int) Math.floor(gridWidth * .9),(int) Math.floor(gridWidth * .9),gridWidth - (int)Math.floor(gridWidth * .9), map);
		this.map = map;
		
		gameRunning = true;
		setBackground(Color.BLUE); 
	    addKeyListener(this);
	    left = false;
	    right = false;
	    up = false;
	    down = false;
	    assetsManager = new AssetsManager();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(!p1.isActive()) return;
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
	    	p1.setBomb();
	    }
		
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
				break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(!p1.isActive()) return;
		int keyCode = e.getKeyCode();
	    if (keyCode == KeyEvent.VK_LEFT && !right) 
	    	p1.setDx(0);
	    else if (keyCode == KeyEvent.VK_RIGHT && !left)
	    	p1.setDx(0);
	    else if (keyCode == KeyEvent.VK_UP && !down)
	    	p1.setDy(0);
	    else if (keyCode == KeyEvent.VK_DOWN && !up)
	    	p1.setDy(0);
		
	}
	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
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
		p1.update(delta,map);
		
	}

	private void render() {
		Graphics g = strategy.getDrawGraphics();
		//draw background
	    g.setColor(Color.CYAN);
//	    g.fillRect(0,0,Game.maxWidth,Game.maxHeight);
	    g.drawImage(assetsManager.getBackground(),0,0,Game.maxWidth,Game.maxHeight,null);
	    int[][] grids = map.getGrids();
	    for(int i = 0; i < grids.length; i++) {
	    	for(int j = 0; j < grids[0].length; j++) {
	    		if(grids[i][j] == GridConstants.BRICK || grids[i][j] == GridConstants.POWERBRICK ) { // TODO modified here for powerup
	    			g.setColor(Color.RED);
	    			g.drawImage(assetsManager.getBrick(), j * Game.gridWidth, i * Game.gridHeight, Game.gridWidth, Game.gridHeight, null);
	    		} else if (grids[i][j] == GridConstants.POWERUP ) { // TODO modified here for powerup
	    			g.setColor(map.getPowerUpGrids()[i][j].renderColor(map.getPowerUpGrids()[i][j].getPowertype()));
	    			g.fillOval(j * Game.gridWidth, i * Game.gridHeight, (int)(Game.gridWidth * .9),(int) (.9 * Game.gridHeight));
	    		}
	    	}
	    }
	    //draw bomb
	    g.setColor(Color.BLACK);

	    
	    for(int i = 0; i < map.getBombGrids().length; i++) {
	    	for(int j = 0; j < map.getBombGrids()[0].length; j++) {
	    		if(map.getBombGrids()[i][j] != null) {
	    			Bomb b = map.getBombGrids()[i][j];
	    			if(!b.isExplode()) {
			    		 g.drawImage(assetsManager.getBomb(),b.getCol() * Game.gridWidth,b.getRow() * Game.gridHeight,b.getWidth(),b.getHeight(), null);
			    	} else {
			    		// set fire
			    		map.getBombGrids()[i][j] = null;
			    		b.explode();
			    	}
	    		}
	    	}
	    }
	    //draw fire
	    g.setColor(Color.ORANGE);
	    Fire[][] fg = map.getFireGrids();
	    for(int i = 0; i < grids.length; i++) {
	    	for(int j = 0; j < grids[0].length; j++) {
	    		if(fg[i][j] != null) {
	    			if(!fg[i][j].timeUp()) {
	    				g.fillRect(j * Game.gridWidth, i * Game.gridHeight, Game.gridWidth, Game.gridHeight);
	    				if(!p1.isFirechecked()) {
	    					if(!p1.isGodMode()) {
	    						p1.checkFire(i, j);
	    					} else {
	    						if(godModetimer == null) {
	    							godModetimer = new Timer();
	    							godModetimer.schedule(new GodModTimerTask(p1), 10 * 1000);
	    						}
	    					}
	    				}
	    			}
	    			else {
	    				fg[i][j] = null;
	    				p1.setFirechecked(false);
	    			}
	    				
	    		}
	    	}
	    }
	    fg = null;
	    //draw player	
	    g.setColor(Color.gray);
	    if(p1.isActive()) {
//	    	g.fillRect(p1.getX(),p1.getY(),p1.getWidth(),p1.getHeight());
	    	g.drawImage(assetsManager.getBombManFace(), p1.getX(),p1.getY(),p1.getWidth(),p1.getHeight(), null);
	    }
	    g.dispose();
	    strategy.show();
		
	}
	
}
