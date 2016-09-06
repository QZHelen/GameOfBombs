package characterCollection;

import java.util.HashMap;

import game.Direction;
import mapCollection.GridConstants;
import mapCollection.Map;

public abstract class Player {
	private int x = 0;
	private int y = 0;
	private int width;
	private int height;
	private int dx = 0;
	private int dy = 0;
	private Direction direction;
	
	public Player(int width, int height) {
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
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
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
		int i = x / 20;
		int j = y / 20;
		//check boundary
//		if(x + dx * delta < 0 || x + dx * delta > 1260 || y + dy * delta < 0 || y + dy * delta  + 20 > 725) {
//			return true;
//		}
//		if(x + dx * delta < 0 || x + dx * delta > 1260) return false;
//		if(i - 1 < 0 || i + 1 > 63 || j - 1 < 0 || j + 1 > 34) return true;
		//check left block
		
		if((x + dx * delta) < (i * 20)) {
			if(i - 1 >= 0) {
				if(map.getGrids()[j][i - 1] == GridConstants.BRICK) {
					x = i * 20;
					return false;
				}
				if(j + 1 <= 34) {
					if((y + 20) > (j + 1) * 20 && map.getGrids()[j + 1][i - 1] == GridConstants.BRICK) {
						x = i * 20;
						return false;
					}
				}
				if(j - 1 >= 0) {
					if(y < (j) * 20 && map.getGrids()[j - 1][i - 1] == GridConstants.BRICK) {
						x = i * 20;
						return false;
					}
				}
				
			} else {
				x = i * 20;
				return false;
			}
			
		}
		if((x + dx * delta + 20) > (i * 20 + 20)) {
			if(i + 1 <= 63) {
				if(map.getGrids()[j][i + 1] == GridConstants.BRICK) {
					x = i * 20;
					return false;
				}
				if(j + 1 <= 34) {
					if((y + 20) > (j + 1) * 20 && map.getGrids()[j + 1][i + 1] == GridConstants.BRICK) {
						x = i * 20;
						return false;
					}
				}
				if(j - 1 >= 0) {
					if(y < (j) * 20 && map.getGrids()[j - 1][i + 1] == GridConstants.BRICK) {
						x = i * 20;
						return false;
					}
				}
				
			} else {
				x = i * 20;
				return false;
			}
		} 
		
		return true;
		
	}
	
	public boolean collisionCheckY(double delta, Map map) {
		int i = x / 20;
		int j = y / 20;
		//check boundary
//		if(x + dx * delta < 0 || x + dx * delta > 1260 || y + dy * delta < 0 || y + dy * delta  + 20 > 725) {
//			return true;
//		}
//		if(y + dy * delta < 0 || y + dy * delta + 20 > 725) return false;
//		if(i - 1 < 0 || i + 1 > 63 || j - 1 < 0 || j + 1 > 34) return true;
		//check left block
		if ((y + dy * delta) < (j * 20)) {
			if(j - 1 >= 0) {
				if(map.getGrids()[j - 1][i] == GridConstants.BRICK) {
					y = j * 20;
					return false;
				}
				if(i + 1 <= 63) {
					if((x + 20) > (i + 1) * 20 && map.getGrids()[j - 1][i + 1] == GridConstants.BRICK) {
						y = j * 20;
						return false;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * 20 && map.getGrids()[j - 1][i - 1] == GridConstants.BRICK) {
						y = j * 20;
						return false;
					}
				}
			} else {
				y = j * 20;
				return false;
			}
		} 
		
		if((y + dy * delta + 20) > (j * 20 + 20)) {
			if(j + 1 <= 63) {
				if(map.getGrids()[j + 1][i] == GridConstants.BRICK) {
					y = j * 20;
					return false;
				}
				if(i + 1 <= 63) {
					if((x + 20) > (i + 1) * 20 && map.getGrids()[j + 1][i + 1] == GridConstants.BRICK) {
						y = j * 20;
						return false;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * 20 && map.getGrids()[j + 1][i - 1] == GridConstants.BRICK) {
						y = j * 20;
						return false;
					}
				}
				
			} else {
				y = j * 20;
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
