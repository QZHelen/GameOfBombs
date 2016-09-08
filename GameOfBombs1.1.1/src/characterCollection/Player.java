package characterCollection;

import java.util.HashMap;

import game.Direction;
import game.Game;
import mapCollection.GridConstants;
import mapCollection.Map;

public abstract class Player {
	private int x = 0;
	private int y = 0;
	private double width;
	private double height;
	private int dx = 0;
	private int dy = 0;
	private Direction direction;
	
	public Player(double width, double height) {
		this.setWidth(width);
		this.setHeight(height);
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
	public double getWidth() {
		return width;
	}
	public void setWidth(double width2) {
		this.width = width2;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height2) {
		this.height = height2;
	}
	public void setDirection(Direction direction) {
		// TODO Auto-generated method stub
		this.direction = direction;
		
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
		setDirection(Direction.LEFT);
		setDx(-2);
		
	}
	public void moveRight() {
		// TODO Auto-generated method stub
		setDirection(Direction.RIGHT);
		setDx(2);
		
	}
	public void moveUp() {
		// TODO Auto-generated method stub
		setDirection(Direction.UP);
		setDy(-2);
	}
	public void moveDown() {
		// TODO Auto-generated method stub
		setDirection(Direction.DOWN);
		setDy(2);
	}
	
	public boolean collisionCheckX(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		
		if((x + dx * delta) < (i * Game.gridWidth)) {
			if(i - 1 >= 0) {
				if(map.getGrids()[j][i - 1] == GridConstants.BRICK) {
					x = i * Game.gridWidth;
					return false;
				}
				if(j + 1 <= GridConstants.GRIDNUMY) {
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
			if(i + 1 <= GridConstants.GRIDNUMX) {
				if(map.getGrids()[j][i + 1] == GridConstants.BRICK) {
					x = i * Game.gridWidth + 2;
					return false;
				}
				if(j + 1 <= GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && map.getGrids()[j + 1][i + 1] == GridConstants.BRICK) {
						x = i * Game.gridWidth + 2;
						return false;
					}
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && map.getGrids()[j - 1][i + 1] == GridConstants.BRICK) {
						x = i * Game.gridWidth + 2;
						return false;
					}
				}
				
			} else {
				x = i * Game.gridWidth + 2;
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
				if(i + 1 <= GridConstants.GRIDNUMX) {
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
			if(j + 1 <= GridConstants.GRIDNUMY) {
				if(map.getGrids()[j + 1][i] == GridConstants.BRICK) {
					y = j * Game.gridWidth + 2;
					return false;
				}
				if(i + 1 <= GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && map.getGrids()[j + 1][i + 1] == GridConstants.BRICK) {
						y = j * Game.gridWidth + 2;
						return false;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && map.getGrids()[j + 1][i - 1] == GridConstants.BRICK) {
						y = j * Game.gridWidth + 2;
						return false;
					}
				}
				
			} else {
				y = j * Game.gridWidth + 2;
				return false;
			}
		}
		return true;
		
	}
	public void update(double delta,Map map) {
		if(collisionCheckX(delta,map)) x += dx * delta;
		if(collisionCheckY(delta,map)) y += dy * delta;
	}
}
