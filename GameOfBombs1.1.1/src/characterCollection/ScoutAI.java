package characterCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

import characterCollection.AI.PathSort;
import gameItemCollection.PathNode;
import mapCollection.GridConstants;
import mapCollection.Map;

public class ScoutAI extends AI {
	
	public ScoutAI(int x, int y, int width, int height, Map map,PathNode[][] pathgrids) {
		super(x, y, width, height, map);
		// TODO Auto-generated constructor stub
		start = pathgrids[getRow()][getCol()];
		moveTo(rand.nextInt(GridConstants.GRIDNUMY),rand.nextInt(GridConstants.GRIDNUMX));
	}

	@Override
	public void run() {
		//bug:some variable cause deadlock
		// TODO Auto-generated method stub
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; 
		while(gameRunning) {
			synchronized(map) {
				long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    double delta = updateLength / ((double)OPTIMAL_TIME);
//			    if(pathTimer == null) {
//			    	pathTimer = new Timer();
//			    	pathTimer.schedule(new PathTimerTask(this), 3 * 1000);
//			    }
			    // update the game logic
			    if(!findPath) {

			    	for(PathNode pn:openList) {
			    		pn.setParent(null);
			    	}
			    	for(PathNode pn:closeList) {
			    		pn.setParent(null);
			    	}
			    	openList.clear();
			    	closeList.clear();
			    	path.clear();
		    		foundPath = pathFind(delta,map,map.getPathGrids2());
		    		findPath = true;
		    	} 
			    if(foundPath) {
			    	if(path.size() == 0) {
					    PathNode temp = map.getPathGrids2()[destination[0]][destination[1]];
						path.add(temp);
						while(temp.getParent() != null) {
							path.add(temp);
							temp = temp.getParent();
						}
						path.add(temp);
						Collections.reverse(path);
			    	}
			    	
			    	checkReached = followPath(1,map.getPathGrids2());
			    } else {
			    	moveTo(rand.nextInt(GridConstants.GRIDNUMY),rand.nextInt(GridConstants.GRIDNUMX));
			    	findPath = false;
			    	foundPath = false;
			    	path.clear();
			    }
//			    System.out.println(checkReached());
			    if(checkReached()) {
			    	setBomb();
			    	moveTo(rand.nextInt(GridConstants.GRIDNUMY),rand.nextInt(GridConstants.GRIDNUMX));
			    	findPath = false;
			    	foundPath = false;
			    	start = map.getPathGrids2()[target.row][target.col];
			    }
			}
	        try {
	        	Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
	        } catch (Exception ex) {}
			
		    //draw graphics 
		}
		
		
	}
	
	@Override
	public boolean destPosChecked() {
		// TODO Auto-generated method stub
		return false;
	}

}
