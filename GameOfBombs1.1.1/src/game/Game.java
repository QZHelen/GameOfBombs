package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JPanel;

import mapCollection.IceMap;
import mapCollection.Map;
import characterCollection.Player;
import characterCollection.PlayerOne;

public class Game extends Canvas implements Runnable, KeyListener{
	int width;
	int height;
	Player p1;
	Map map;
	public BufferStrategy strategy;
	boolean gameRunning;
	/** The last time at which we recorded the frame rate */
	private long lastFpsTime;
	/** The current number of frames recorded */
	private int fps;
	
	public Game(Map map,int width, int height) {
		this.p1 = new PlayerOne(20,20);
		this.map = map;
		this.width = width;
		this.height = height;
		gameRunning = true;
		setBackground(Color.BLUE); 
	    addKeyListener(this);
	}
//	public void paint(Graphics g) {
//		
//		g.setColor(Color.gray);
//		g.fillRect(p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight());
//		
//	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
//	    int d;
	    if (keyCode == KeyEvent.VK_LEFT) 
	    	p1.moveLeft();
	    else if (keyCode == KeyEvent.VK_RIGHT)
	    	p1.moveRight();
	    else if (keyCode == KeyEvent.VK_UP)
	    	p1.moveUp();
	    else if (keyCode == KeyEvent.VK_DOWN)
	    	p1.moveDown();
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
//	    int d;
	    if (keyCode == KeyEvent.VK_LEFT) 
	    	p1.setDx(0);
	    else if (keyCode == KeyEvent.VK_RIGHT)
	    	p1.setDx(0);
	    else if (keyCode == KeyEvent.VK_UP)
	    	p1.setDy(0);
	    else if (keyCode == KeyEvent.VK_DOWN)
	    	p1.setDy(0);
		
	}
	@Override
	public void run() {
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
		// TODO Auto-generated method stub
		while(gameRunning) {
			
			long now = System.nanoTime();
		      long updateLength = now - lastLoopTime;
		      lastLoopTime = now;
		      double delta = updateLength / ((double)OPTIMAL_TIME);

		      // update the frame counter
		      lastFpsTime += updateLength;
		      fps++;
		      
		      // update our FPS counter if a second has passed since
		      // we last recorded
		      if (lastFpsTime >= 1000000000)
		      {
		         lastFpsTime = 0;
		         fps = 0;
		      }
		      
		      // update the game logic
		      p1.update(delta);
		      
		      //draw graphics 
		     
		      Graphics g = strategy.getDrawGraphics();
		      g.setColor(Color.CYAN);
		      g.fillRect(0,0,width,height);
		      g.setColor(Color.gray);
		      g.fillRect(p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight());
//		      render(g);
		      g.dispose();
		      strategy.show();
//			  repaint();
	          try {
	        	  Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
	          } catch (Exception ex) {}
		}
	}
}
