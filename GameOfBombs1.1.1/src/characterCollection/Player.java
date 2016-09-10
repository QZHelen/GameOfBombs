package characterCollection;

import java.util.HashMap;

import game.Direction;
import game.Game;
import gameItemCollection.Bomb;
import mapCollection.GridConstants;
import mapCollection.Map;

public abstract class Player {
	private int x = 0;
	private int y = 0;
	private int width;
	private int height;
	private int dx = 0;
	private int dy = 0;
	private int diff = 0;
	private int fireRadius;
	Map map;
	
	
	public int getFireRadius() {
		return fireRadius;
	}
	public void setFireRadius(int fireRadius) {
		this.fireRadius = fireRadius;
	}
	public Player(int width, int height, int diff, Map map) {
		this.setWidth(width);
		this.setHeight(height);
		this.diff = diff;
		this.map = map;
		this.fireRadius = 1;
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
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	public void moveLeft() {
		// TODO Auto-generated method stub
		
//		setDirection(Direction.LEFT);
		if(Game.left)
			setDx(-4);
		
	}
	public void moveRight() {
		// TODO Auto-generated method stub
//		setDirection(Direction.RIGHT);
		if(Game.right)
			setDx(4);
		
	}
	public void moveUp() {
		// TODO Auto-generated method stub
//		setDirection(Direction.UP);
		if(Game.up)
			setDy(-4);
	}
	public void moveDown() {
		// TODO Auto-generated method stub
//		setDirection(Direction.DOWN);
		if(Game.down)
			setDy(4);
	}
	
	public void setBomb() {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
//		if(this.map.getBombs().isEmpty())
			this.map.getBombs().add(new Bomb(i,j,Game.gridWidth,Game.gridHeight,false, map, this));
	}
	
	public boolean collisionCheckX(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
//		System.out.println(x + " " + i + " " + y + " " + j);
		if((x + dx * delta) < (i * Game.gridWidth)) {
			if(i - 1 >= 0) {
				if(map.getGrids()[j][i - 1] == GridConstants.BRICK) {
					x = i * Game.gridWidth;
					return false;
				}
				if(j + 1 < GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && map.getGrids()[j + 1][i - 1] == GridConstants.BRICK) {
						x = i * Game.gridWidth;
						return false;
					}
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && map.getGrids()[j - 1][i - 1] == GridConstants.BRICK) {
						x = i * Game.gridWidth;
						return false;
					}
				}
				
			} else {
				x = i * Game.gridWidth;
				return false;
			}
			
		}
		if((x + dx * delta + width) > (i * Game.gridWidth + Game.gridWidth)) {
			if(i + 1 < GridConstants.GRIDNUMX) {
				if(map.getGrids()[j][i + 1] == GridConstants.BRICK) {
					x = i * Game.gridWidth + diff;
					return false;
				}
				if(j + 1 < GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && map.getGrids()[j + 1][i + 1] == GridConstants.BRICK) {
						x = i * Game.gridWidth + diff;
						return false;
					}
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && map.getGrids()[j - 1][i + 1] == GridConstants.BRICK) {
						x = i * Game.gridWidth + diff;
						return false;
					}
				}
				
			} else {
				x = i * Game.gridWidth + diff;
//				System.out.println(i + " " + x);
				return false;
			}
		} 
		
		return true;
		
	}
	
	public boolean collisionCheckY(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridWidth;

		if ((y + dy * delta) < (j * Game.gridWidth)) {
			if(j - 1 >= 0) {
				if(map.getGrids()[j - 1][i] == GridConstants.BRICK) {
					y = j * Game.gridWidth;
					return false;
				}
				if(i + 1 <GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && map.getGrids()[j - 1][i + 1] == GridConstants.BRICK) {
						y = j * Game.gridWidth;
						return false;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && map.getGrids()[j - 1][i - 1] == GridConstants.BRICK) {
						y = j * Game.gridWidth;
						return false;
					}
				}
			} else {
				y = j * Game.gridWidth;
				return false;
			}
		} 
		
		if((y + dy * delta + width) > (j * Game.gridWidth + Game.gridWidth)) {
			if(j + 1 < GridConstants.GRIDNUMY) {
				if(map.getGrids()[j + 1][i] == GridConstants.BRICK) {
					y = j * Game.gridWidth + diff;
					return false;
				}
				if(i + 1 < GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && map.getGrids()[j + 1][i + 1] == GridConstants.BRICK) {
						y = j * Game.gridWidth + diff;
						return false;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && map.getGrids()[j + 1][i - 1] == GridConstants.BRICK) {
						y = j * Game.gridWidth + diff;
						return false;
					}
				}
				
			} else {
				y = j * Game.gridWidth + diff;
				return false;
			}
		}
		return true;
		
	}
	public void update(double delta,Map map) {
		if(collisionCheckX(delta,map)) x += dx * delta;
		if(collisionCheckY(delta,map)) y += dy * delta;
	}
	public int getDiff() {
		return diff;
	}
	public void setDiff(int diff) {
		this.diff = diff;
	}
}
