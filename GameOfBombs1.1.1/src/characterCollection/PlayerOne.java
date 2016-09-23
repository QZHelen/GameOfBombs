package characterCollection;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import mapCollection.Map;

public class PlayerOne extends Player {
	
	private boolean gameRunning;
	public PlayerOne(int x,int y,int width, int height, int diff,Map map,int key1,int key2,int key3,ArrayList<AI> monsterlist) {
		super(x, y, width, height, diff, map, key1, key2, key3, monsterlist);
		gameRunning = true;
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
		    update(delta,map);
		    //draw graphics 
	        try {
	        	Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
	        } catch (Exception ex) {}
		}
		
	}


}
