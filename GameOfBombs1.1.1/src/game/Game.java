package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
//import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import java.io.IOException;
import java.util.Timer;
//import java.util.TimerTask;

//import javax.swing.AbstractAction;

import mapCollection.GridConstants;
import mapCollection.Map;
import powerUpCollection.PowerUpType;
import characterCollection.AI;
import characterCollection.Player;
import characterCollection.PlayerOne;
import gameItemCollection.Bomb;
import gameItemCollection.Fire;

public class Game extends Canvas implements Runnable, KeyListener {
	
	Player p1;
	Player p2;
	AI monster1;
	Map map;
	public BufferStrategy strategy;
	boolean gameRunning;
	public static int gridWidth;
	public static int gridHeight;
	public static int maxWidth;
	public static int maxHeight;
	public static int panelHeight;
	static TopPanel tPanel;
	static BottomPanel bPanel;
	public static AssetsManager assetsManager;
	public static final String MOVE_UP = "move up";
	public static final String MOVE_DOWN = "move down";
	public static final String MOVE_LEFT = "move left";
	public static final String MOVE_RIGHT = "move right";
	public static final String FIRE = "fire";
	
	public Game(Map map) throws IOException {
		this.p1 = new PlayerOne(0,0,(int) Math.floor(gridWidth * .9),(int) Math.floor(gridWidth * .9),gridWidth - (int)Math.floor(gridWidth * .9), map,1,2,3);
		this.p2 = new PlayerOne((GridConstants.GRIDNUMX - 1) * Game.gridWidth,0,(int) Math.floor(gridWidth * .9),(int) Math.floor(gridWidth * .9),gridWidth - (int)Math.floor(gridWidth * .9), map,8,9,0);
		this.monster1 = new AI((GridConstants.GRIDNUMX - 1) * Game.gridWidth,(GridConstants.GRIDNUMY - 1) * Game.gridHeight,gridWidth,gridWidth, map);
		this.map = map;
		p1.otherPlayer = p2;
		p2.otherPlayer = p1;
		monster1.otherPlayer = p1;
		new Thread(p1).start();
		new Thread(p2).start();
		new Thread(monster1).start();
		gameRunning = true;
		setBackground(Color.BLUE); 
	    addKeyListener(this);
	    assetsManager = new AssetsManager();
	}

	public Player getP2() {
		return p2;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(p1.isActive()) {
			if (keyCode == KeyEvent.VK_A) {
		    	setKeys('l',p1);
		    	p1.moveLeft();
		    }	else if (keyCode == KeyEvent.VK_D) {
		    	setKeys('r',p1);
		    	p1.moveRight();
		    }	else if (keyCode == KeyEvent.VK_W) {
		    	setKeys('u',p1);
		    	p1.moveUp();
		    }	else if (keyCode == KeyEvent.VK_S) {
		    	setKeys('d',p1);
		    	p1.moveDown();
		    }	else if (keyCode == KeyEvent.VK_N) {
		    	p1.setBomb();
		    }	else if(keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_3){
//		    	System.out.println("pressed");
		    	p1.setPowerUp(keyCode);
		    }
		}
		if(p2.isActive()) {
			if (keyCode == KeyEvent.VK_LEFT) {
		    	setKeys('l',p2);
		    	p2.moveLeft();
		    }	else if (keyCode == KeyEvent.VK_RIGHT) {
		    	setKeys('r',p2);
		    	p2.moveRight();
		    }	else if (keyCode == KeyEvent.VK_UP) {
		    	setKeys('u',p2);
		    	p2.moveUp();
		    }	else if (keyCode == KeyEvent.VK_DOWN) {
		    	setKeys('d',p2);
		    	p2.moveDown();
		    }	else if (keyCode == KeyEvent.VK_SPACE) {
		    	p2.setBomb();
		    } 	else if (keyCode == KeyEvent.VK_8 || keyCode == KeyEvent.VK_9 || keyCode == KeyEvent.VK_0) {
		    	p2.setPowerUp(keyCode);
		    }
		}
	}
	
	public void setKeys(char key, Player p) {
		switch (key){
			case 'l':
				p.left = true;
				p.right = false;
				p.up = false;
				p.down = false;
				break;
			case 'r':
				p.left = false;
				p.right = true;
				p.up = false;
				p.down = false;
				break;
			case 'u':
				p.left = false;
				p.right = false;
				p.up = true;
				p.down = false;
				break;
			case 'd':
				p.left = false;
				p.right = false;
				p.up = false;
				p.down = true;
				break;
			default:
				break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(p1.isActive()) {
			if (keyCode == KeyEvent.VK_A && !p1.right) 
		    	p1.setDx(0);
		    else if (keyCode == KeyEvent.VK_D && !p1.left)
		    	p1.setDx(0);
		    else if (keyCode == KeyEvent.VK_W && !p1.down)
		    	p1.setDy(0);
		    else if (keyCode == KeyEvent.VK_S && !p1.up)
		    	p1.setDy(0);
		}
		if(p2.isActive()) {
		    if (keyCode == KeyEvent.VK_LEFT && !p2.right) 
		    	p2.setDx(0);
		    else if (keyCode == KeyEvent.VK_RIGHT && !p2.left)
		    	p2.setDx(0);
		    else if (keyCode == KeyEvent.VK_UP &&  !p2.down)
		    	p2.setDy(0);
		    else if (keyCode == KeyEvent.VK_DOWN && !p2.up)
		    	p2.setDy(0);
		}
		
		
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
//		    long updateLength = now - lastLoopTime;
		    lastLoopTime = now;
//		    double delta = updateLength / ((double)OPTIMAL_TIME);

		    // update the game logic
		    //draw graphics 
		    render();
	        try {
	        	Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
	        } catch (Exception ex) {}
		}
	}

//	private void updateAll(double delta, Map map) {
//		p1.update(delta,map);
//		p2.update(delta, map);
//		
//	}

	private void render() {
		Graphics g = strategy.getDrawGraphics();
		//draw background
	    g.setColor(Color.decode("#AFE3D6"));
	    g.fillRect(0,0,Game.maxWidth,Game.maxHeight);
//	    g.drawImage(assetsManager.getBackground(),0,0,Game.maxWidth,Game.maxHeight,null);
	    int[][] grids = map.getGrids();
	    for(int i = 0; i < grids.length; i++) {
	    	for(int j = 0; j < grids[0].length; j++) {
	    		if(grids[i][j] == GridConstants.BRICK || grids[i][j] == GridConstants.POWERBRICK ) { // TODO modified here for powerup
	    			g.setColor(Color.RED);
	    			g.drawImage(assetsManager.getBrick(), j * Game.gridWidth, i * Game.gridHeight, Game.gridWidth, Game.gridHeight, null);
	    		} else if (grids[i][j] == GridConstants.POWERUP ) { 
	    			PowerUpType pt = map.getPowerUpGrids()[i][j].getPowertype();
	    			g.drawImage(map.getPowerUpGrids()[i][j].renderImage(pt), j * Game.gridWidth, i * Game.gridHeight, (int)(Game.gridWidth * .9),(int) (.9 * Game.gridHeight), null);
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
	    				checkPlayerBurn(fg,i,j,p1,p1.getGodModetimer());
	    				checkPlayerBurn(fg,i,j,p2,p2.getGodModetimer());
	    			}
	    			else {
	    				fg[i][j] = null;
	    				p1.setFirechecked(false);
	    				p2.setFirechecked(false);
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
	    if(p2.isActive()) {
//	    	g.fillRect(p1.getX(),p1.getY(),p1.getWidth(),p1.getHeight());
	    	g.drawImage(assetsManager.getBombManFace(), p2.getX(),p2.getY(),p2.getWidth(),p2.getHeight(), null);
	    }
	    if(monster1.isActive()) {
//	    	g.fillRect(p1.getX(),p1.getY(),p1.getWidth(),p1.getHeight());
	    	g.drawImage(assetsManager.getGhost(), monster1.getX(),monster1.getY(),monster1.getWidth(),monster1.getHeight(), null);
	    }
	    g.dispose();
	    strategy.show();
		
	}
	public void checkPlayerBurn(Fire[][] fg,int i,int j,Player p,Timer timer) {
		if(!p.isFirechecked()) {
			if(!p.isGodMode()) {
				p.checkFire(i, j);
			} else {
				if(timer == null) {
					p.setGodModetimer(new Timer());
					p.getGodModetimer().schedule(new GodModTimerTask(p), 10 * 1000);
				}
			}
		}
	}
//	 private class MoveAction extends AbstractAction {
//
//	        int direction;
//	        int player;
//
//	        MoveAction(int direction, int player) {
//
//	            this.direction = direction;
//	            this.player = player;
//	        }
//
//	        @Override
//	        public void actionPerformed(ActionEvent e) {
//
//	            // Same as the move method in the question code.
//	            // Player can be detected by e.getSource() instead and call its own move method.
//	        }
//	    }
//
//	    private class FireAction extends AbstractAction {
//
//	        int player;
//
//	        FireAction(int player) {
//
//	            this.player = player;
//	        }
//
//	        @Override
//	        public void actionPerformed(ActionEvent e) {
//
//	            // Same as the fire method in the question code.
//	            // Player can be detected by e.getSource() instead, and call its own fire method.
//	            // If so then remove the constructor.
//	        }
//	    }
	
}
