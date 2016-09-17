package characterCollection;


import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;

import javax.swing.JLabel;

import game.Game;
import game.PathTimerTask;
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
	private PowerUp pu;
	private boolean active;
	private int row,col;
	private int health;
	Timer pathTimer;
	public boolean changePath;

	//	private int life;
	private boolean firechecked;
//	private boolean godMode;
//	private boolean bombPassMode;
	public boolean left,right,up,down;
	private int healthCheck;
	public Player otherPlayer;
	int key1,key2,key3;
	private boolean gameRunning;
	ArrayList<Integer> directions;
	public int prevdir;
	public int[] arr1;
	public int[] arr2;
	

	
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
		    if(pathTimer == null) {
		    	pathTimer = new Timer();
		    	pathTimer.schedule(new PathTimerTask(this), 3 * 1000);
		    }
		    // update the game logic
		    pathFind(delta,map);
		    //draw graphics 
	        try {
	        	Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
	        } catch (Exception ex) {}
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
	
	
	public AI(int x, int y, int width, int height, Map map) {
		directions = new ArrayList<Integer>();
		setWidth(width);
		setHeight(height);
		this.map = map;
		this.x = x;
		this.y = y;
		prevdir = 2;
		speed = 2;
		active = true;
		health = 100;
		firechecked = false;
		gameRunning = true;
		healthCheck = 20;
		left = false;
	    right = false;
	    up = true;
	    down = false;
	    setDy(-speed);
//	    setDx(-speed);
	    arr1 = new int[]{0,1,3};
	    arr2 = new int[]{0,2,3};
	    changePath = false;
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
	public int move(ArrayList<Integer> directions,double delta) {
		
		int direction = Map.rand.nextInt(directions.size());
		if(prevdir == 3) {
			direction = Map.rand.nextInt(3);
		} else if (prevdir == 2) {
			direction = arr1[Map.rand.nextInt(arr1.length)];
		} else if (prevdir == 1) {
			direction = arr2[Map.rand.nextInt(arr2.length)];
		} else {
			direction = Map.rand.nextInt(3) + 1;
		}
//		if(!directions.contains(0) || !directions.contains(1)) {
//			direction = rand.nextInt(2) + 2;
//		} else {
//			direction = rand.nextInt(2);
//		}
		switch(direction) {
			case 0:
//				if(prevdir == 2 || prevdir == 3) {
					moveLeft(delta);
					prevdir = 0;
					setDy(0);
//				}
				break;	
			case 1:
//				if(prevdir == 2 || prevdir == 3) {
					moveRight(delta);
					prevdir = 1;
					setDy(0);
//				}
				break;
			case 2:
//				if(prevdir == 0 || prevdir == 1) {
					moveUp(delta);
					prevdir = 2;
					setDx(0);
//				}
				break;
			case 3:
//				if(prevdir == 0 || prevdir == 1) {
					moveDown(delta);
					prevdir = 3;
					setDx(0);
//				}
				break;
			default:
				break;
				
		}
		return direction;
		
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
		if((x + dx * delta) < (i * Game.gridWidth)) {
			if(i - 1 >= 0) {
				if(map.getGrids()[j][i - 1] == GridConstants.BRICK || map.getGrids()[j][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j][i - 1] == GridConstants.BOMB)) {
					x = i * Game.gridWidth;
					return false;
				}
				if(j + 1 < GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && (map.getGrids()[j + 1][i - 1] == GridConstants.BRICK || map.getGrids()[j + 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i - 1] == GridConstants.BOMB))) {
						x = i * Game.gridWidth;
						return false;
					}
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && (map.getGrids()[j - 1][i - 1] == GridConstants.BRICK || map.getGrids()[j - 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i - 1] == GridConstants.BOMB))) {
						x = i * Game.gridWidth;
						return false;
					}
				}
				
			} else {
				x = i * Game.gridWidth;
				return false;
			}
			
		}
		return true;
	}
	public boolean collisionCheckXRight(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		if((x + dx * delta + width) > (i * Game.gridWidth + Game.gridWidth)) {
			if(i + 1 < GridConstants.GRIDNUMX) {
				if(map.getGrids()[j][i + 1] == GridConstants.BRICK || map.getGrids()[j][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j][i + 1] == GridConstants.BOMB)) {
					x = i * Game.gridWidth;
					return false;
				}
				if(j + 1 < GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && (map.getGrids()[j + 1][i + 1] == GridConstants.BRICK || map.getGrids()[j + 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i + 1] == GridConstants.BOMB))) {
						x = i * Game.gridWidth;
						return false;
					}
					
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && (map.getGrids()[j - 1][i + 1] == GridConstants.BRICK || map.getGrids()[j - 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i + 1] == GridConstants.BOMB ))) {
						x = i * Game.gridWidth;
						return false;
					}
				}
				
			} else {
				x = i * Game.gridWidth ;
				return false;
			}
		} 
		return true;
	}
	public boolean collisionCheckYUp(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		if ((y + dy * delta) < (j * Game.gridWidth)) {
			if(j - 1 >= 0) {
				if(map.getGrids()[j - 1][i] == GridConstants.BRICK || map.getGrids()[j - 1][i] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i] == GridConstants.BOMB )) {
					y = j * Game.gridWidth;
					return false;
				}
				if(i + 1 < GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && (map.getGrids()[j - 1][i + 1] == GridConstants.BRICK || map.getGrids()[j - 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i + 1] == GridConstants.BOMB))) {
						y = j * Game.gridWidth;
						return false;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && (map.getGrids()[j - 1][i - 1] == GridConstants.BRICK || map.getGrids()[j - 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i - 1] == GridConstants.BOMB))) {
						y = j * Game.gridWidth;
						return false;
					}
				}
			} else {
				y = j * Game.gridWidth;
				return false;
			}
		} 
		return true;
	}
	public boolean collisionCheckYDown(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		if((y + dy * delta + width) > (j * Game.gridWidth + Game.gridWidth)) {
			if(j + 1 < GridConstants.GRIDNUMY) {
				if(map.getGrids()[j + 1][i] == GridConstants.BRICK || map.getGrids()[j + 1][i] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i] == GridConstants.BOMB )) {
					y = j * Game.gridWidth;
					return false;
				}
				if(i + 1 < GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && (map.getGrids()[j + 1][i + 1] == GridConstants.BRICK || map.getGrids()[j + 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i + 1] == GridConstants.BOMB))) {
						y = j * Game.gridWidth;
						return false;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && (map.getGrids()[j + 1][i - 1] == GridConstants.BRICK || map.getGrids()[j + 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i - 1] == GridConstants.BOMB))) {
						y = j * Game.gridWidth;
						return false;
					}
				}
				
			} else {
				y = j * Game.gridWidth;
				return false;
			}
		}
		return true;
	}
//	public void collisionCheckX(double delta, Map map) {
//		int i = x / Game.gridWidth;
//		int j = y / Game.gridHeight;
//		
//		
//	}
//	
//	public void collisionCheckY(double delta, Map map) {
//		int i = x / Game.gridWidth;
//		int j = y / Game.gridWidth;
//		
//	}
	
	public void pathFind(double delta,Map map) {
//		boolean checkPlayerX = checkPlayerCollisionX(otherPlayer.getX(),otherPlayer.getY(),delta);
//		boolean checkPlayerY = checkPlayerCollisionY(otherPlayer.getX(),otherPlayer.getY(),delta);
//		if(prevdir == 2 || prevdir == 3) {
//			up = collisionCheckYUp(delta, map);
//			down = collisionCheckYDown(delta, map);
//			left = true;
//			right = true;
//		}
//		
//		if(prevdir == 0 || prevdir == 1) {
//			up = true;
//			down = true;
//			left = collisionCheckXLeft(delta, map);
//			right = collisionCheckXRight(delta, map);
//		}
		
		up = collisionCheckYUp(delta, map);
		down = collisionCheckYDown(delta, map);
		left = collisionCheckXLeft(delta, map);
		right = collisionCheckXRight(delta, map);
		
		
		if(left) directions.add(0);
		if(right) directions.add(1);
		if(up) directions.add(2);
		if(down) directions.add(3);

		int temp = 3;
		if(directions.size() < 4 || changePath) {
			temp = move(directions,delta);
//			System.out.println(temp);
		}
		if(changePath) changePath = false;
		x += dx * delta;
		y += dy * delta;
//		if(temp == 0 || temp == 1) {
//			x += dx * delta;
//		} else {
//			y += dy * delta;
//
//		}
		directions.clear();
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
}
