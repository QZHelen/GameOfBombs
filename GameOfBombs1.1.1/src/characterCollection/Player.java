package characterCollection;


import java.util.Stack;

import game.Game;
import game.GameRun;
import gameItemCollection.Bomb;
import mapCollection.GridConstants;
import mapCollection.Map;
import powerUpCollection.PowerUp;

public abstract class Player {
	private int x = 0;
	private int y = 0;
	private int width;
	private int height;
	private double dx = 0;
	private double dy = 0;
	private int diff = 0;
	private int fireRadius;
	private int bombNum;
	private double speed;
	private Stack<PowerUp> powerUpList;
	private PowerUp pu;
	private boolean active;
	private int row;
	private int col;
	private int health;
	private int life;
	private boolean firechecked;
	
	public boolean isFirechecked() {
		return firechecked;
	}

	public void setFirechecked(boolean firechecked) {
		this.firechecked = firechecked;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	Map map;
	
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
	
	
	public Player(int width, int height, int diff, Map map) {
		setWidth(width);
		setHeight(height);
		this.diff = diff;
		this.map = map;
		fireRadius = 1;
		bombNum = 1;
		speed = 2;
		powerUpList = new Stack<PowerUp>();
		active = true;
		health = 100;
		life = 3;
		firechecked = false;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		if (speed <=4 || speed >=0) {
		this.speed = speed;
		}
	}
	
	public void changeSpeedBy(double amount) {
		if (speed + amount <=4 || speed + amount >=0) {
			this.speed = speed + amount;
			}
	}

	public int getFireRadius() {
		return fireRadius;
	}
	public void setFireRadius(int fireRadius) {
		if (fireRadius > 0) {
		this.fireRadius = fireRadius;
		}
	}
	public void changeFireRadiusBy(int amount) {
		if (this.fireRadius + amount > 0) {
		this.fireRadius += amount;
		}
	}
	public Stack<PowerUp> getPowerUpList() {
		return powerUpList;
	}
	public void setPowerUpList(Stack<PowerUp> powerUpList) {
		this.powerUpList = powerUpList;
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
	public void moveLeft() {
		if(Game.left)
			setDx(-speed);
		
	}
	public void moveRight() {

		if(Game.right)
			setDx(speed);
		
	}
	public void moveUp() {

		if(Game.up)
			setDy(-speed);
	}
	public void moveDown() {
		if(Game.down)
			setDy(speed);
	}
	
	public void hurt() {
		health -= 1;
		GameRun.p1healthbar.setValue(health);
		GameRun.p1healthbar.updateUI();
	}
	public void die() {
		active = false;
	}
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setBomb() {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		int imod = x % Game.gridWidth;
		int jmod = y % Game.gridHeight;
		System.out.println(imod + " " + jmod);
		if(imod >= (int)(.7 * Game.gridWidth) && imod <= (int)(.99 * Game.gridWidth)) {
			if(Game.right) x = (i + 1) * Game.gridWidth;
			i = i + 1;
		}
		
		if(jmod >= (int)(.7 * Game.gridHeight) && jmod <= (int)(.99 * Game.gridWidth)) {
			if(Game.down) y = (j + 1) * Game.gridHeight;
			j = j + 1;
		}
		if(bombNum > 0 && this.map.getBombGrids()[j][i] == null) {
			bombNum--;
			
			this.map.getBombGrids()[j][i] = new Bomb(i,j,Game.gridWidth,Game.gridHeight, map, this);
			this.map.getGrids()[j][i] = GridConstants.BOMB;
			
		}
			
	}
	
	public int getBombNum() {
		return bombNum;
	}
	public void setBombNum(int bombNum) {
		if (bombNum > 0) {
		this.bombNum = bombNum;
		}
	}
	public void changeBombNumBy(int amount) {
		if (this.bombNum + amount > 0) {
			this.bombNum += amount;
		}
	}
	
	public void checkPowerUp(int row, int col) {
		pu = map.getPowerUpGrids()[row][col];
		pu.setMyPlayer(this);
		pu.takeEffect(pu.getPowertype());
		powerUpList.add(pu);
		map.getPowerUpGrids()[row][col] = null;
		map.getGrids()[row][col] = GridConstants.NOTHING;
	}
	
	public void checkFire(int row, int col) {
		System.out.println(row + " " + this.getRow());
		System.out.println(col + " " + this.getCol());
		if(getRow() == row && getCol() == col) {
			setFirechecked(true);
			if(health > 50) {
				hurt();
			} else {
				hurt();
				die();
			}
		}
			
	}
	public boolean collisionCheckX(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
//		System.out.println(x + " " + i + " " + y + " " + j);
		if((x + dx * delta) < (i * Game.gridWidth)) {
			if(i - 1 >= 0) {
				if(map.getGrids()[j][i - 1] == GridConstants.BRICK || map.getGrids()[j][i - 1] == GridConstants.POWERBRICK || map.getGrids()[j][i - 1] == GridConstants.BOMB) {
					x = i * Game.gridWidth;
					return false;
				}
				if(map.getGrids()[j][i - 1] == GridConstants.POWERUP) {
					checkPowerUp(j, i - 1);
					return true;
				}
				if(j + 1 < GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && (map.getGrids()[j + 1][i - 1] == GridConstants.BRICK || map.getGrids()[j + 1][i - 1] == GridConstants.POWERBRICK || map.getGrids()[j + 1][i - 1] == GridConstants.BOMB)) {
						x = i * Game.gridWidth;
						return false;
					}
					if((y + width) > (j + 1) * Game.gridWidth && map.getGrids()[j + 1][i - 1] == GridConstants.POWERUP) {
						checkPowerUp(j + 1, i - 1);
						return true;
					}
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && (map.getGrids()[j - 1][i - 1] == GridConstants.BRICK || map.getGrids()[j - 1][i - 1] == GridConstants.POWERBRICK || map.getGrids()[j - 1][i - 1] == GridConstants.BOMB)) {
						x = i * Game.gridWidth;
						return false;
					}
					
					if(y < (j) * Game.gridWidth && map.getGrids()[j - 1][i - 1] == GridConstants.POWERUP) {
						checkPowerUp(j - 1, i - 1);
						return true;
					}
				}
				
			} else {
				x = i * Game.gridWidth;
				return false;
			}
			
		}
		if((x + dx * delta + width) > (i * Game.gridWidth + Game.gridWidth)) {
			if(i + 1 < GridConstants.GRIDNUMX) {
				if(map.getGrids()[j][i + 1] == GridConstants.BRICK || map.getGrids()[j][i + 1] == GridConstants.POWERBRICK || map.getGrids()[j][i + 1] == GridConstants.BOMB) {
					x = i * Game.gridWidth + diff;
					return false;
				}
				if(map.getGrids()[j][i + 1] == GridConstants.POWERUP) {
					checkPowerUp(j, i + 1);
					return true;
				}
				if(j + 1 < GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && (map.getGrids()[j + 1][i + 1] == GridConstants.BRICK || map.getGrids()[j + 1][i + 1] == GridConstants.POWERBRICK || map.getGrids()[j + 1][i + 1] == GridConstants.BOMB)) {
						x = i * Game.gridWidth + diff;
						return false;
					}
					
					if((y + width) > (j + 1) * Game.gridWidth && map.getGrids()[j + 1][i + 1] == GridConstants.POWERUP) {
						checkPowerUp(j + 1, i + 1);
						return true;
					}
					
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && (map.getGrids()[j - 1][i + 1] == GridConstants.BRICK || map.getGrids()[j - 1][i + 1] == GridConstants.POWERBRICK || map.getGrids()[j - 1][i + 1] == GridConstants.BOMB)) {
						x = i * Game.gridWidth + diff;
						return false;
					}
					if(y < (j) * Game.gridWidth && map.getGrids()[j - 1][i + 1] == GridConstants.POWERUP) {
						checkPowerUp(j - 1, i + 1);
						return true;
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
				if(map.getGrids()[j - 1][i] == GridConstants.BRICK || map.getGrids()[j - 1][i] == GridConstants.POWERBRICK || map.getGrids()[j - 1][i] == GridConstants.BOMB) {
					y = j * Game.gridWidth;
					return false;
				}
				if(map.getGrids()[j - 1][i] == GridConstants.POWERUP) {
					checkPowerUp(j - 1, i);
					return true;
				}
				if(i + 1 < GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && (map.getGrids()[j - 1][i + 1] == GridConstants.BRICK || map.getGrids()[j - 1][i + 1] == GridConstants.POWERBRICK || map.getGrids()[j - 1][i + 1] == GridConstants.BOMB)) {
						y = j * Game.gridWidth;
						return false;
					}
					if((x + width) > (i + 1) * Game.gridWidth && map.getGrids()[j - 1][i + 1] == GridConstants.POWERUP) {
						checkPowerUp(j - 1, i + 1);
						return true;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && (map.getGrids()[j - 1][i - 1] == GridConstants.BRICK || map.getGrids()[j - 1][i - 1] == GridConstants.POWERBRICK || map.getGrids()[j - 1][i - 1] == GridConstants.BOMB)) {
						y = j * Game.gridWidth;
						return false;
					}
					if((x) < (i) * Game.gridWidth && (map.getGrids()[j - 1][i - 1] == GridConstants.POWERUP)) {
						checkPowerUp(j - 1, i - 1);
						return true;
					}
				}
			} else {
				y = j * Game.gridWidth;
				return false;
			}
		} 
		
		if((y + dy * delta + width) > (j * Game.gridWidth + Game.gridWidth)) {
			if(j + 1 < GridConstants.GRIDNUMY) {
				if(map.getGrids()[j + 1][i] == GridConstants.BRICK || map.getGrids()[j + 1][i] == GridConstants.POWERBRICK || map.getGrids()[j + 1][i] == GridConstants.BOMB) {
					y = j * Game.gridWidth + diff;
					return false;
				}
				if(map.getGrids()[j + 1][i] == GridConstants.POWERUP) {
					checkPowerUp(j + 1, i);
					return true;
				}
				if(i + 1 < GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && (map.getGrids()[j + 1][i + 1] == GridConstants.BRICK || map.getGrids()[j + 1][i + 1] == GridConstants.POWERBRICK || map.getGrids()[j + 1][i + 1] == GridConstants.BOMB)) {
						y = j * Game.gridWidth + diff;
						return false;
					}
					if((x + width) > (i + 1) * Game.gridWidth && map.getGrids()[j + 1][i + 1] == GridConstants.POWERUP) {
						checkPowerUp(j + 1, i + 1);
						return true;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && (map.getGrids()[j + 1][i - 1] == GridConstants.BRICK || map.getGrids()[j + 1][i - 1] == GridConstants.POWERBRICK || map.getGrids()[j + 1][i - 1] == GridConstants.BOMB)) {
						y = j * Game.gridWidth + diff;
						return false;
					}
					if((x) < (i) * Game.gridWidth && map.getGrids()[j + 1][i - 1] == GridConstants.POWERUP) {
						checkPowerUp(j + 1, i - 1);
						return true;
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
