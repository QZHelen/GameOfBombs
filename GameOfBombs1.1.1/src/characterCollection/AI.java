package characterCollection;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.Timer;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JLabel;

import org.javatuples.Pair;

import game.Game;
import game.PathTimerTask;
import gameItemCollection.Bomb;
import gameItemCollection.Fire;
import gameItemCollection.PathNode;
import mapCollection.GridConstants;
import mapCollection.Map;
import powerUpCollection.PowerUp;

public abstract class AI extends JLabel implements Runnable {
	
	Map map;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected double dx = 0;
	protected double dy = 0;
	public boolean bombStay;
	public boolean fireStay;
	protected Hashtable<Integer,Stack<PowerUp>> badAssList; 
	protected double speed;
//	private PowerUp pu;
	protected boolean active;
	protected int row,col;
	protected int health;
	protected boolean findPath;
	protected boolean foundPath;
	protected PriorityQueue<PathNode> openList;
	protected ArrayList<PathNode> closeList;
	protected ArrayList<PathNode> path;
	protected PathNode target;
	protected PathNode start;
	protected int fireRadius;
	protected boolean checkReached;
	protected int bombNum;
	Timer pathTimer;
	public boolean changePath;
	public int[] destination;
	protected boolean firechecked;
	public boolean left,right,up,down;
	protected int healthCheck;
	public Vector<Player> playList;
//	int key1,key2,key3;
	protected Random rand;
	protected boolean gameRunning;
	ArrayList<Integer> directions;
	public int prevdir;
	public int[] arr1;
	public int[] arr2;
	public int[] topBlock,bottomBlock,leftBlock,rightBlock;
	
	class AIBomb extends Bomb{
		AI monster;
		public AIBomb(int col, int row, int width, int height, Map map, Player p) {
			super(col, row, width, height, map, p);
		}
		public AIBomb(int col, int row, int width, int height, Map map, AI monster) {
			super(col, row, width, height, map,null);
			this.monster = monster;
		}
		@Override
		public void explode() {
			map.getGrids()[row][col] = GridConstants.NOTHING;
			map.setWalkable(row, col);
			bombNum++;
			map.setFireGrids(row, col, new Fire(width, height, getFireRadius()));
			
		}
		public ArrayList<Pair<Integer, Integer>> range() {
			int radius = fireRadius;
			int rowstart,rowend,colstart,colend;
			rowstart = row - radius;
			rowend = row + radius;
			colstart = col - radius;
			colend = col + radius;
			if(rowstart < 0) rowstart = 0;
			if(rowend > GridConstants.GRIDNUMY - 1) rowend = GridConstants.GRIDNUMY - 1;
			if(colstart < 0) colstart = 0;
			if(colend > GridConstants.GRIDNUMX - 1) colend = GridConstants.GRIDNUMX - 1;
			ArrayList<Pair<Integer,Integer>> bombs = new ArrayList<>();
			for(; rowstart <= rowend; rowstart++) {
				bombs.add(new Pair<>(rowstart,col));
			}
			for(; colstart <= colend; colstart++) {
				bombs.add(new Pair<>(row,colstart));
			}
//			bombs.add(new Pair<>(row,col));
			return bombs;
		}
		
	}
	class PathSort implements Comparator<PathNode> {

		@Override
		public int compare(PathNode o1, PathNode o2) {
			return (int)(o1.f - o2.f);
		}
	}
	@Override
	public abstract void run();
	
	public void reset() {
		findPath = false;
    	foundPath = false;
    	start = map.getPathGrids()[target.row][target.col];
	}
	public double distanceToP(Player player) {
		int diffx = x - player.getX();
		int diffy = y - player.getY();
		return (double) Math.sqrt((diffx * diffx)+(diffy*diffy));
	}
	public int getBombNum() {
		return bombNum;
	}
	public void setBombNum(int bombNum) {
		if (bombNum > 0) {
		this.bombNum = bombNum;
		}
	}
	public int getFireRadius() {
		return fireRadius;
	}

	public void setFireRadius(int fireRadius) {
		this.fireRadius = fireRadius;
	}
	private boolean targetChecked() {
		if(target == null)  {

			return true;
		}
		//bug
		double diffx = Math.abs(target.x - x);
		double diffy = Math.abs(target.y - y);
		if(diffx >= 0 && diffx <= 1) x = target.x;
		if(diffy >= 0 && diffy <= 1) y = target.y;
		return target.x == x && target.y == y;
	}
	protected boolean followPath(double delta) {
		boolean result = false;
		if(!path.isEmpty()) {

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

		} 
//		System.out.println(path.size());
		return result && path.isEmpty();
		
		
	}
	protected boolean followPath(double delta,PathNode[][] pathgrids) {
		// TODO Auto-generated method stub
		boolean result = false;
		if(!path.isEmpty()) {

			int count = -1;
			result = targetChecked();
			if(result) {
				for (int i = 0; i < path.size();i++) {
					if(path.get(i).equals(pathgrids[getRow()][getCol()])) {
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
		bombNum = 1;
		health = 100;
		firechecked = false;
		gameRunning = true;
		healthCheck = 20;
		rand = new Random();
//		left = false;
//	    right = false;
//	    up = true;
//	    down = false;
		fireRadius = 1;
	    checkReached = false;
	    setDy(-speed);
	    findPath = false;
	    foundPath = false;
	    arr1 = new int[]{0,1,3};
	    arr2 = new int[]{0,2,3};
	    changePath = false;
	    destination = new int[2];
	    fireStay = false;
//	    topBlock = new int[2]; 
//	    bottomBlock = new int[2];
//	    leftBlock = new int[2];
//	    rightBlock = new int[2];
	    PathSort ps = new PathSort();
	    openList = new PriorityQueue<PathNode>(10,ps);
	    closeList = new ArrayList<PathNode>();
	    path = new ArrayList<PathNode>();
	    start = map.getPathGrids()[getRow()][getCol()];
	    playList = new Vector<Player>();
	}
	
	public abstract boolean destPosChecked();
	public ArrayList<PathNode> getPath() {
		return path;
	}

	public void setPath(ArrayList<PathNode> path) {
		this.path = path;
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
	
	public void setBomb() {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		int imod = x % Game.gridWidth;
		int jmod = y % Game.gridHeight;
		if(imod >= (int)(.7 * Game.gridWidth) && imod <= (int)(.99 * Game.gridWidth)) {
			if(right) x = (i + 1) * Game.gridWidth;
			i = i + 1;
		}
		
		if(jmod >= (int)(.7 * Game.gridHeight) && jmod <= (int)(.99 * Game.gridWidth)) {
			if(down) y = (j + 1) * Game.gridHeight;
			j = j + 1;
		}
		
		if(bombNum > 0 && this.map.getBombGrids()[j][i] == null) {
			bombNum--;
			
			map.getBombGrids()[j][i] = new AIBomb(i,j,Game.gridWidth,Game.gridHeight, map, this);
			map.getGrids()[j][i] = GridConstants.BOMB;
			map.setUnWalkable(j, i);
//			avoidBomb();
			
		}
			
	}
	public boolean checkReached() {
		return checkReached;
	}
	
	public void avoidBomb(){
		System.out.println("avoidbomb: " + bombStay);
		if(!bombStay){
			int rowstart = 0;
			int rowend = 0;
			int colstart = 0;
			int colend = 0;
//			bombStay = false;
			if(getRow()<= Bomb.MAXFIRERAD) {
				rowstart = 0;
			} else {
				rowstart = getRow() - Bomb.MAXFIRERAD;
			}
			if(getRow() >= GridConstants.GRIDNUMY - Bomb.MAXFIRERAD) {
				rowend = GridConstants.GRIDNUMY;
			} else {
				rowend = getRow() + Bomb.MAXFIRERAD;
			}
			if(getCol()<= Bomb.MAXFIRERAD) {
				colstart = 0;
			} else {
				colstart = getCol() - Bomb.MAXFIRERAD;
			}
			if(getCol() >= GridConstants.GRIDNUMX - Bomb.MAXFIRERAD) {
				colend = GridConstants.GRIDNUMX;
			} else {
				colend = getCol() + Bomb.MAXFIRERAD;
			}
			HashSet<Pair<Integer,Integer>> bombs = new HashSet<Pair<Integer,Integer>>();
			for (int i = rowstart; i < rowend; i++) {
				for (int j = colstart; j < colend; j++) {
					if(map.getBombGrids()[i][j] != null) {
						bombs.addAll(map.getBombGrids()[i][j].range());
					}
				}
			}
			Pair<Integer,Integer> currLoc = new Pair<>(getRow(),getCol());
			if(bombs.contains(currLoc)) {
				while(true) {
					Pair<Integer,Integer> noBomb = new Pair<Integer,Integer>(rand.nextInt(rowend - rowstart - 1) + rowstart,rand.nextInt(colend - colstart -1) + colstart);
					if(!bombs.contains(noBomb) && !noBomb.equals(currLoc)){
						moveAwayBomb(noBomb.getValue0(),noBomb.getValue1());
						reset();
						bombStay = true;
						break;
					}
				}
			}	
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
	public Pair<Integer,Integer> moveAwayBomb(int row,int col) {
		destination[0] = row;
		destination[1] = col;
		return new Pair(destination[0],destination[1]);
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
	


	public boolean pathFind(double delta,Map map,PathNode[][] pathgrids) {
		synchronized(map) {
			//parent bug
			openList.add(start);
			PathNode current;
			PathNode neighbor;
			boolean result;
			while(!closeList.contains(pathgrids[destination[0]][destination[1]])) {
				if(openList.isEmpty()) {
					break;
				}
				current = openList.poll();
				closeList.add(current);
				if(current.row + 1 < GridConstants.GRIDNUMY) {
					neighbor = pathgrids[current.row + 1][current.col];
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
					neighbor = pathgrids[current.row - 1][current.col];
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
					neighbor = pathgrids[current.row][current.col + 1];
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
					neighbor = pathgrids[current.row][current.col - 1];
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
			result = (closeList.contains(pathgrids[destination[0]][destination[1]]))? true : false;
			
			return result;
		}
	}
	public boolean pathFind(double delta,Map map) {
		synchronized(map) {
			//parent bug
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
			
			return result;
		}
			

		
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
