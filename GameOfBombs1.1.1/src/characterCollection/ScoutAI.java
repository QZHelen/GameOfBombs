package characterCollection;

import java.util.Collections;

import gameItemCollection.PathNode;
import mapCollection.GridConstants;
import mapCollection.Map;

public class ScoutAI extends AI {

	public ScoutAI(int x, int y, int width, int height, Map map) {
		super(x, y, width, height, map);
		// TODO Auto-generated constructor stub
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
//		    if(pathTimer == null) {
//		    	pathTimer = new Timer();
//		    	pathTimer.schedule(new PathTimerTask(this), 3 * 1000);
//		    }
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
	    		foundPath = pathFind(delta,map);
	    		findPath = true;
	    	} 
		    if(foundPath) {
		    	if(path.size() == 0) {
				    PathNode temp = map.getPathGrids()[destination[0]][destination[1]];
					path.add(temp);
					while(temp.getParent() != null) {
//						System.out.println(temp.row + " " + temp.col);
						path.add(temp);
						temp = temp.getParent();
					}
					path.add(temp);
					Collections.reverse(path);
		    	}
		    	
		    	checkReached = followPath(1);
		    } else {
		    	moveTo(Map.rand.nextInt(GridConstants.GRIDNUMY),Map.rand.nextInt(GridConstants.GRIDNUMX));
		    	findPath = false;
		    	foundPath = false;
		    	path.clear();
		    }
//		    System.out.println(checkReached());
		    if(checkReached()) {
		    	//bug:target null pointer
		    	moveTo(Map.rand.nextInt(GridConstants.GRIDNUMY),Map.rand.nextInt(GridConstants.GRIDNUMX));
		    	findPath = false;
		    	foundPath = false;
		    	start = map.getPathGrids()[target.row][target.col];
//		    	path.clear();
//		    	target = null;
		    }
		    //draw graphics 
	        try {
	        	Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
	        } catch (Exception ex) {}
		}
		
		
	}

	@Override
	public boolean destPosChecked() {
		// TODO Auto-generated method stub
		return false;
	}

}
