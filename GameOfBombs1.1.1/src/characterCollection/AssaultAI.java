package characterCollection;

import java.util.Collections;
import java.util.Timer;

import game.PathTimerTask;
import gameItemCollection.PathNode;
import mapCollection.GridConstants;
import mapCollection.Map;

public class AssaultAI extends AI {

	public AssaultAI(int x, int y, int width, int height, Map map) {
		super(x, y, width, height, map);
	}
	@Override
	public boolean checkReached() {
		return checkReached;
	}
	public boolean destPosChecked() {
		double minDis = Double.POSITIVE_INFINITY;
		Player targetPlayer = null;
		for(int i = 0; i < playList.size();i++) {
			if(distanceToP(playList.get(i)) < minDis) {
				minDis = distanceToP(playList.get(i));
				targetPlayer = playList.get(i);
			}
		}
		 if(targetPlayer.getRow() != destination[0] || targetPlayer.getCol() != destination[1]) {
			 destination[0] = targetPlayer.getRow();
			 destination[1] = targetPlayer.getCol();
			 return true;
		 } else {
			 return false;
		 }
	}
	@Override
	public void run() {

		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
		while(gameRunning) {
			synchronized(map) {
//				System.out.println("hellow");
				long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    double delta = updateLength / ((double) OPTIMAL_TIME);

			    // update the game logic
//			    if(!bombStay) {
//			    	bombStay = avoidBomb();
//			    }
			    if(fireStay) {
			    	System.out.println("fireStay: " + fireStay);
			    	if(pathTimer == null) {
			    		pathTimer = new Timer();
			    		pathTimer.schedule(new PathTimerTask(this), 3 * 1000);
			    	}
			    }
			    if(!fireStay) {
			    	avoidBomb();
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
			    		foundPath = pathFind(delta,map,map.getPathGrids());
			    		findPath = true;
			    	} 
				    if(foundPath) {
				    	if(path.size() == 0) {
						    PathNode temp = map.getPathGrids()[destination[0]][destination[1]];
							path.add(temp);
							while(temp.getParent() != null) {

								path.add(temp);
								temp = temp.getParent();
							}
							path.add(temp);
							Collections.reverse(path);
				    	}
				    	
				    	checkReached = followPath(1);
				    } else {

				    	reset();
				    }
//				    if(checkReached() && bombStay) {
	//
//				    }
			    	if(checkReached()) {
//			    		System.out.println("checkreached: " + checkReached() + target.x + " " + target.y);
			    		if(bombStay) {
				    		bombStay = false;
				    		avoidBomb();
				    		if(!bombStay) fireStay = true;
			    		} else {
				    		setBomb();
				    		assert fireStay == false;
			    		}
			    	}
			    	if(!bombStay && !fireStay) {
			    		if(destPosChecked()) {
			    			reset();
						}
			    	}

				}
			}
			    
	        try {
	        	Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
	        } catch (Exception ex) {}

		}
		
	}
}
