package characterCollection;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Timer;

import javax.swing.JLabel;

import game.Game;
import game.PathTimerTask;
import gameItemCollection.PathNode;
import gameItemCollection.PerishBlock;
import mapCollection.GridConstants;
import mapCollection.Map;
import powerUpCollection.PowerUp;

public class AI extends JLabel implements Runnable {
	
	Map map;
	private int x;
	private int y;
	private int width;
	private int height;
	private double dx = 0;
	private double dy = 0;
	private Hashtable<Integer,Stack<PowerUp>> badAssList; 
	private double speed;
//	private PowerUp pu;
	private boolean active;
	private int row,col;
	private int health;
	private boolean findPath;
	private boolean foundPath;
	private PriorityQueue<PathNode> openList;
	private ArrayList<PathNode> closeList;
	private ArrayList<PathNode> path;
	private PathNode target;
	private PathNode start;
	private boolean checkReached;
	public ArrayList<PathNode> getPath() {
		return path;
	}

	public void setPath(ArrayList<PathNode> path) {
		this.path = path;
	}

	Timer pathTimer;
	public boolean changePath;
	public int[] destination;
	private boolean firechecked;
	public boolean left,right,up,down;
	private int healthCheck;
	public Player otherPlayer;
	int key1,key2,key3;
	private boolean gameRunning;
	ArrayList<Integer> directions;
	public int prevdir;
	public int[] arr1;
	public int[] arr2;
	public int[] topBlock,bottomBlock,leftBlock,rightBlock;
	

	static class PathSort implements Comparator<PathNode> {

		@Override
		public int compare(PathNode o1, PathNode o2) {
			// TODO Auto-generated method stub
			return (int)(o1.f - o2.f);
		}
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
		    	System.out.println(target);
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
	private boolean targetChecked() {
		if(target == null)  {
//	    	x = start.x;
//	    	y = start.y;
			return true;
		}
		//bug
		double diffx = Math.abs(target.x - x);
		double diffy = Math.abs(target.y - y);
		if(diffx >= 0 && diffx <= 1) x = target.x;
		if(diffy >= 0 && diffy <= 1) y = target.y;
		return target.x == x && target.y == y;
	}
	private boolean followPath(double delta) {
		// TODO Auto-generated method stub
		boolean result = false;
		if(!path.isEmpty()) {
//			up = collisionCheckYUp(delta, map);
//			down = collisionCheckYDown(delta, map);
//			left = collisionCheckXLeft(delta, map);
//			right = collisionCheckXRight(delta, map);
//			if(left) directions.add(0);
//			if(right) directions.add(1);
//			if(up) directions.add(2);
//			if(down) directions.add(3);
//			collisionCheckYUp(delta, map);
//			collisionCheckYDown(delta, map);
//			collisionCheckXLeft(delta, map);
//			collisionCheckXRight(delta, map);
			int count = -1;
			result = targetChecked();
			if(result) {
				for (int i = 0; i < path.size();i++) {
					if(path.get(i).equals(map.getPathGrids()[getRow()][getCol()])) {
						if(i + 1 < path.size()) {
							target = path.get(i + 1);
						}
						
						count = i;
//						System.out.println("target " + target.row + " " + target.col);
						break;
					}
				}
				if(path.size() > count && count >= 0) path.remove(count);
			}
			prevdir = bestDirection();
//			System.out.println(prevdir+ " " + target);
			changeDirection(delta);
			x += dx * delta;
			y += dy * delta;
//			if(dx < 0) {
//				x += Math.floor(dx * delta);
//			} else if(dx > 0) {
//				x += Math.ceil(dx * delta);
//			} else {
//				x += dx * delta;
//			}
//			if(dy < 0) {
//				y += Math.floor(dy * delta);
//			} else if(dy > 0) {
//				y += Math.ceil(dy * delta);
//			} else {
//				
//			}
//			y += dy * delta;
//			System.out.println(x);
		
//			path.remove(count);
		} 
//		System.out.println(path.size());
		return result && path.isEmpty();
		
		
	}

	public AI(int x, int y, int width, int height, Map map) {
		directions = new ArrayList<Integer>();
		setWidth(width);
		setHeight(height);
		this.map = map;
		this.x = x;
		this.y = y;
		prevdir = 2;
		speed = 1;
		active = true;
		health = 100;
		firechecked = false;
		gameRunning = true;
		healthCheck = 20;
		left = false;
	    right = false;
	    up = true;
	    down = false;
	    checkReached = false;
	    setDy(-speed);
	    findPath = false;
	    foundPath = false;
	    arr1 = new int[]{0,1,3};
	    arr2 = new int[]{0,2,3};
	    changePath = false;
	    destination = new int[2];
	    topBlock = new int[2]; 
	    bottomBlock = new int[2];
	    leftBlock = new int[2];
	    rightBlock = new int[2];
	    PathSort ps = new PathSort();
	    openList = new PriorityQueue<PathNode>(10,ps);
	    closeList = new ArrayList<PathNode>();
	    path = new ArrayList<PathNode>();
	    start = map.getPathGrids()[getRow()][getCol()];
	}
	

	public Hashtable<Integer, Stack<PowerUp>> getBadAssList() {
		return badAssList;
	}

	public void setBadAssList(Hashtable<Integer, Stack<PowerUp>> badAssList) {
		this.badAssList = badAssList;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		if (speed <= 4 || speed >= 1) {
		this.speed = speed;
		}
	}
	
	public void changeSpeedBy(double amount) {
		if (speed + amount <= 4 || speed + amount >= 1) {
			this.speed = speed + amount;
		}
	}
	
	public boolean checkReached() {
		return checkReached;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width2) {
		this.width = width2;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height2) {
		this.height = height2;
	}
	public double getDx() {
		return dx;
	}
	public void setDx(double speed2) {
		this.dx = speed2;
	}
	public double getDy() {
		return dy;
	}
	public void setDy(double speed2) {
		this.dy = speed2;
	}
	public void moveLeft(double delta) {
		setDx(-speed);
//		x += x * delta;
		
	}
	public void moveRight(double delta) {
		setDx(speed);

//		x += x * delta;
	}
	public void moveUp(double delta) {
		setDy(-speed);
//		y += y * delta;
	}
	public void moveDown(double delta) {
		setDy(speed);
//		y += y * delta;
	}
	public int changeDirection(double delta) {
		if(target == null) {
			setDy(0);
			setDx(0);
			return prevdir;
		}
		switch(prevdir) {
			case 0:
//				if(prevdir == 2 || prevdir == 3) {
					moveLeft(delta);
//					prevdir = 0;
					setDy(0);
//				}
				break;	
			case 1:
//				if(prevdir == 2 || prevdir == 3) {
					moveRight(delta);
//					prevdir = 1;
					setDy(0);
//				}
				break;
			case 2:
//				if(prevdir == 0 || prevdir == 1) {
					moveUp(delta);
//					prevdir = 2;
					setDx(0);
//				}
				break;
			case 3:
//				if(prevdir == 0 || prevdir == 1) {
					moveDown(delta);

					setDx(0);
//				}
				break;
			default:
				break;
				
		}
		return prevdir;
		
	}
	
	public void hurt() {
		health -= healthCheck;
	}
	
	public void die() {
		active = false;
		health = 0;
		
	}
	


	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	public void checkFire(int row, int col) {
		if(getRow() == row && getCol() == col) {
			setFirechecked(true);
			if(health > healthCheck) {
				hurt();
			} else {
				die();
			}
		}		
	}
	
	public boolean checkPlayerCollisionX(int x, int y,double delta) {
		boolean checkRight = (getX() + width + dx * delta > x) && (getY() + height > y && getY() < y + height);
		boolean checkLeft =  (getX() + dx * delta < (x + width)) && (getY() + height > y && getY() < y + height);
		if(checkRight && checkLeft) {
			if(this.x < x) this.x = x - width;
			else this.x = x + width;
			return true;
		}
		return false;
	}
	
	
	public boolean checkPlayerCollisionY(int x, int y,double delta) {
		boolean checkUp = (getY() + width + dy * delta > y) && (getX() + height > x && getX() < x + height);
		boolean checkDown =  (getY() + dy * delta < (y + width)) && (getX() + height > x && getX() < x + height);
		if(checkUp && checkDown) {
			if(this.y < y) this.y = y - width;
			else this.y = y + width;
			return true;
		}
		return false;
	}
	
	public boolean collisionCheckXLeft(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		if((x + -speed * delta) < (i * Game.gridWidth)) {
			if(i - 1 >= 0) {
				if(map.getGrids()[j][i - 1] == GridConstants.BRICK || map.getGrids()[j][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j][i - 1] == GridConstants.BOMB)) {
					if(dx == -speed)x = i * Game.gridWidth;
					return false;
				}
				if(j + 1 < GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && (map.getGrids()[j + 1][i - 1] == GridConstants.BRICK || map.getGrids()[j + 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i - 1] == GridConstants.BOMB))) {
						if(dx == -speed)x = i * Game.gridWidth;
						return false;
					}
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && (map.getGrids()[j - 1][i - 1] == GridConstants.BRICK || map.getGrids()[j - 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i - 1] == GridConstants.BOMB))) {
						if(dx == -speed)x = i * Game.gridWidth;
						return false;
					}
				}
				
			} else {
				if(dx == -speed)x = i * Game.gridWidth;
				return false;
			}
			
		}
		if(i - 1 < 0) {
			if(dx == -speed) x = i * Game.gridWidth;
			return false;
		}
		if(map.getGrids()[j][i - 1] == GridConstants.NOTHING) {
			leftBlock[0] = j;
			leftBlock[1] = i - 1;
		}
		return true;
	}
	public boolean collisionCheckXRight(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		if((x + speed * delta + width) > (i * Game.gridWidth + Game.gridWidth)) {
			if(i + 1 < GridConstants.GRIDNUMX) {
				if(map.getGrids()[j][i + 1] == GridConstants.NOTHING) {
					rightBlock[0] = j + 1;
					rightBlock[1] = i;
				}
				if(map.getGrids()[j][i + 1] == GridConstants.BRICK || map.getGrids()[j][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j][i + 1] == GridConstants.BOMB)) {
					if(dx == speed)x = i * Game.gridWidth;
					return false;
				}
				if(j + 1 < GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && (map.getGrids()[j + 1][i + 1] == GridConstants.BRICK || map.getGrids()[j + 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i + 1] == GridConstants.BOMB))) {
						if(dx == speed)x = i * Game.gridWidth;
						return false;
					}
					
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && (map.getGrids()[j - 1][i + 1] == GridConstants.BRICK || map.getGrids()[j - 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i + 1] == GridConstants.BOMB ))) {
						if(dx == speed)x = i * Game.gridWidth;
						return false;
					}
				}
				
			} else {
				if(dx == speed) x = i * Game.gridWidth ;
				return false;
			}
		} 
		return true;
	}
	public boolean collisionCheckYUp(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		if ((y + -speed * delta) < (j * Game.gridWidth)) {
			if(j - 1 >= 0) {
				if(map.getGrids()[j - 1][i] == GridConstants.BRICK || map.getGrids()[j - 1][i] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i] == GridConstants.BOMB )) {
					if(dy == -speed)y = j * Game.gridWidth;
					return false;
				}
				if(i + 1 < GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && (map.getGrids()[j - 1][i + 1] == GridConstants.BRICK || map.getGrids()[j - 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i + 1] == GridConstants.BOMB))) {
						if(dy == -speed)y = j * Game.gridWidth;
						return false;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && (map.getGrids()[j - 1][i - 1] == GridConstants.BRICK || map.getGrids()[j - 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i - 1] == GridConstants.BOMB))) {
						if(dy == -speed)y = j * Game.gridWidth;
						return false;
					}
				}
			} else {
				if(dy == -speed)y = j * Game.gridWidth;
				return false;
			}
		}
		if(j - 1 < 0) {
			if(dy == -speed)y = j * Game.gridWidth;
			return false;
		}
		if(map.getGrids()[j - 1][i] == GridConstants.NOTHING) {
			topBlock[0] = j - 1;
			topBlock[1] = i;
		}
		return true;
	}
	public boolean collisionCheckYDown(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		if((y + speed * delta + width) > (j * Game.gridWidth + Game.gridWidth)) {
			if(j + 1 < GridConstants.GRIDNUMY) {
				if(map.getGrids()[j + 1][i] == GridConstants.NOTHING) {
					bottomBlock[0] = j + 1;
					bottomBlock[1] = i;
				}
				if(map.getGrids()[j + 1][i] == GridConstants.BRICK || map.getGrids()[j + 1][i] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i] == GridConstants.BOMB )) {
					if(dy == speed)y = j * Game.gridWidth;
					return false;
				}
				if(i + 1 < GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && (map.getGrids()[j + 1][i + 1] == GridConstants.BRICK || map.getGrids()[j + 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i + 1] == GridConstants.BOMB))) {
						if(dy == speed)y = j * Game.gridWidth;
						return false;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && (map.getGrids()[j + 1][i - 1] == GridConstants.BRICK || map.getGrids()[j + 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i - 1] == GridConstants.BOMB))) {
						if(dy == speed)y = j * Game.gridWidth;
						return false;
					}
				}
				
			} else {
				if(dy == speed)y = j * Game.gridWidth;
				return false;
			}
		}
		return true;
	}

	public void moveTo(int row,int col) {
		destination[0] = row;
		destination[1] = col;
	}
	
	public int bestDirection() {
		//bug2
		if(target == null) return prevdir;
		if(target.row < getRow()) {
			prevdir = 2;
		}
		if(target.row > getRow()) {
			prevdir = 3;
		}
		if(target.col < getCol()) {
			prevdir = 0;
		} 
		if(target.col > getCol()) {
			prevdir = 1;
		} 
		return prevdir;
	}
	



	public boolean pathFind(double delta,Map map) {

//		openList.add(map.getPathGrids()[getRow()][getCol()]);
		openList.add(start);
		PathNode current;
		PathNode neighbor;
		boolean result;
		while(!closeList.contains(map.getPathGrids()[destination[0]][destination[1]])) {
			if(openList.isEmpty()) {
				break;
			}
			current = openList.poll();
			closeList.add(current);
			if(current.row + 1 < GridConstants.GRIDNUMY) {
				neighbor = map.getPathGrids()[current.row + 1][current.col];
				if(neighbor.isWalkable() && !closeList.contains(neighbor)) {
					if(!openList.contains(neighbor)) {
						neighbor.setParent(current);
						neighbor.setF();
						openList.add(neighbor);
					} else {
						if(current.g + 1 < neighbor.g) {
							neighbor.setParent(current);
							neighbor.setF();
						}
					}
					
				}
			}
			if(current.row - 1 >= 0 ) {
				neighbor = map.getPathGrids()[current.row - 1][current.col];
				if(neighbor.isWalkable() && !closeList.contains(neighbor)) {
					if(!openList.contains(neighbor)) {
						neighbor.setParent(current);
						neighbor.setF();
						openList.add(neighbor);
					} else {
						if(current.g + 1 < neighbor.g) {
							neighbor.setParent(current);
							neighbor.setF();
						}
					}
				}
			}
			if(current.col + 1 < GridConstants.GRIDNUMX) {
				neighbor = map.getPathGrids()[current.row][current.col + 1];
				if(neighbor.isWalkable() && !closeList.contains(neighbor)) {
					if(!openList.contains(neighbor)) {
						neighbor.setParent(current);
						neighbor.setF();
						openList.add(neighbor);
					} else {
						if(current.g + 1 < neighbor.g) {
							neighbor.setParent(current);
							neighbor.setF();
						}
					}
				}
				
			}
			if(current.col - 1 >= 0) {
				neighbor = map.getPathGrids()[current.row][current.col - 1];
				if(neighbor.isWalkable() && !closeList.contains(neighbor)) {
					if(!openList.contains(neighbor)) {
						neighbor.setParent(current);
						neighbor.setF();
						openList.add(neighbor);
					} else {
						if(current.g + 1 < neighbor.g) {
							neighbor.setParent(current);
							neighbor.setF();
						}
					}
				}

			}
			
		}
		result = (closeList.contains(map.getPathGrids()[destination[0]][destination[1]]))? true : false;
//		collisionCheckYUp(delta, map);
//		collisionCheckYDown(delta, map);
//		System.out.println("running");
		
		return result;
//		up = collisionCheckYUp(delta, map);
//		down = collisionCheckYDown(delta, map);
//		left = collisionCheckXLeft(delta, map);
//		right = collisionCheckXRight(delta, map);
//		if(left) directions.add(0);
//		if(right) directions.add(1);
//		if(up) directions.add(2);
//		if(down) directions.add(3);
//		prevdir = bestDirection();
//		changeDirection(directions,delta);
//		x += dx * delta;
//		y += dy * delta;
////		if(changePath) changePath = false;
////		if(collisionCheckX(delta,map)) x += dx * delta;
////		if(collisionCheckY(delta, map)) y += dy * delta;

////		if(temp == 0 || temp == 1) {
////			x += dx * delta;
////		} else {
////			y += dy * delta;
////
////		}
//		directions.clear();
//		if(collisionCheckX(delta,map) && dx != 0) {
//			x += dx * delta;
//			return;
//		}
//		if(collisionCheckY(delta,map) && dy != 0) {
//			y += dy * delta;
//		} 
	}
	

	public void changeHealthBy(int i) {
		if(health <= 80) {
			health += i;
		}
		
	}
	public Timer getPathTimer() {
		return pathTimer;
	}

	public void setPathTimer(Timer pathTimer) {
		this.pathTimer = pathTimer;
	}

	
	public boolean isFirechecked() {
		return firechecked;
	}

	public void setFirechecked(boolean firechecked) {
		this.firechecked = firechecked;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getRow() {
		return y / Game.gridHeight;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return x / Game.gridWidth;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	
}
