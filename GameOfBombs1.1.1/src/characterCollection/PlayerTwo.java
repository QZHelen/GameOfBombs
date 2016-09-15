package characterCollection;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mapCollection.Map;

public class PlayerTwo extends Player implements Runnable{

	private boolean gameRunning;

	public PlayerTwo(int x,int y,int width, int height, int diff,Map map) {
		super(x, y, width, height, diff, map);
		// TODO Auto-generated constructor stub
		gameRunning = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
		while(gameRunning) {
			
			long now = System.nanoTime();
		    long updateLength = now - lastLoopTime;
		    lastLoopTime = now;
		    double delta = updateLength / ((double)OPTIMAL_TIME);

		    // update the game logic
		    update(delta,map);
		    //draw graphics 
	        try {
	        	Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
	        } catch (Exception ex) {}
		}
		
	}



}
