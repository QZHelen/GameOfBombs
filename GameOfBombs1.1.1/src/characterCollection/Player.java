package characterCollection;


import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import game.Game;
import game.CustomLabel;
import gameItemCollection.Bomb;
import mapCollection.GridConstants;
import mapCollection.Map;
import powerUpCollection.PowerUp;

public abstract class Player extends JLabel implements Runnable {
	
	Map map;
	private int x;
	private int y;
	private int width;
	private int height;
	private double dx = 0;
	private double dy = 0;
	private int diff = 0;
	private int fireRadius;
	private int bombNum;
	private Hashtable<Integer,Stack<PowerUp>> badAssList; 
	private double speed;
	private Stack<PowerUp> powerUpList;
	private PowerUp pu;
	private boolean active;
	private int row,col;
	private int health;
	private int life;
	private boolean firechecked;
	private boolean godMode;
	private boolean bombPassMode;
	private int healthCheck;
	public CustomLabel hl1,hl2,hl3,hl4,hl5;
	public CustomLabel item1,item2,item3;
	public boolean left,right,up,down;
	public JProgressBar p1healthbar;
	Timer godModetimer;
	Timer bombPasstimer;
	public Player otherPlayer;
	int key1,key2,key3;
	
	public Timer getBombPasstimer() {
		return bombPasstimer;
	}

	public void setBombPasstimer(Timer bombPasstimer) {
		this.bombPasstimer = bombPasstimer;
	}

	
	
	public Timer getGodModetimer() {
		return godModetimer;
	}

	public void setGodModetimer(Timer godModetimer) {
		this.godModetimer = godModetimer;
	}


	public boolean isBombPassMode() {
		return bombPassMode;
	}

	public void setBombPassMode(boolean bombPassMode) {
		this.bombPassMode = bombPassMode;
	}

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
	public boolean isGodMode() {
		return godMode;
	}

	public void setGodMode(boolean godMode) {
		this.godMode = godMode;
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
	
	
	public Player(int x, int y, int width, int height, int diff, Map map,int key1,int key2,int key3) {
		this.key1 = key1;
		this.key2 = key2;
		this.key3 = key3;
		setWidth(width);
		setHeight(height);
		this.diff = diff;
		this.map = map;
		this.x = x;
		this.y = y;
		fireRadius = 1;
		bombNum = 1;
		speed = 2;
		powerUpList = new Stack<PowerUp>();
		badAssList = new Hashtable<Integer,Stack<PowerUp>>();
		badAssList.put(key1, new Stack<PowerUp>());
		badAssList.put(key2, new Stack<PowerUp>());
		badAssList.put(key3, new Stack<PowerUp>());
		active = true;
		health = 100;
		life = 3;
		firechecked = false;
		godMode = false;
		bombPassMode = false;
		healthCheck = 20;
		left = false;
	    right = false;
	    up = false;
	    down = false;
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
		if(left)
			setDx(-speed);
		
	}
	public void moveRight() {

		if(right)
			setDx(speed);
		
	}
	public void moveUp() {

		if(up)
			setDy(-speed);
	}
	public void moveDown() {
		if(down)
			setDy(speed);
	}
	
	public void hurt() {
		health -= healthCheck;
		p1healthbar.setValue(health);
	}
	
	public void die() {
		changeLifeBy(-1);
		if(life <= 0) {
			active = false;
			health = 0;
			p1healthbar.setValue(health);
		} else {
			health = 100;
			p1healthbar.setValue(health);
		}
		
	}
	
	public void changeLifeBy(int i) {
		if(this.life < 5)
			this.life += i;
		if(life == 1) {
			hl1.setVisible(true);
			hl2.setVisible(false);
			hl3.setVisible(false);
			hl4.setVisible(false);
			hl5.setVisible(false);
		} else if(life == 2) {
			hl1.setVisible(true);
			hl2.setVisible(true);
			hl3.setVisible(false);
			hl4.setVisible(false);
			hl5.setVisible(false);
		} else if(life == 3) {
			hl1.setVisible(true);
			hl2.setVisible(true);
			hl3.setVisible(true);
			hl4.setVisible(false);
			hl5.setVisible(false);
		} else if(life == 4) {
			hl1.setVisible(true);
			hl2.setVisible(true);
			hl3.setVisible(true);
			hl4.setVisible(true);
			hl5.setVisible(false);
		} else if(life == 5) {
			hl1.setVisible(true);
			hl2.setVisible(true);
			hl3.setVisible(true);
			hl4.setVisible(true);
			hl5.setVisible(true);
		}
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
			
			this.map.getBombGrids()[j][i] = new Bomb(i,j,Game.gridWidth,Game.gridHeight, map, this);
			this.map.getGrids()[j][i] = GridConstants.BOMB;
			
		}
			
	}
	
	public void setPowerUp(int key) {
		switch(key) {
			case KeyEvent.VK_1:
				if(getBadAssList().get(1).isEmpty()) return;
				break;
			case KeyEvent.VK_2:
				if(getBadAssList().get(2).isEmpty()) return;
				break;
			case KeyEvent.VK_3:
				if(getBadAssList().get(3).isEmpty()) return;
				break;
			case KeyEvent.VK_8:
				if(getBadAssList().get(8).isEmpty()) return;
				break;
			case KeyEvent.VK_9:
				if(getBadAssList().get(9).isEmpty()) return;
				break;
			case KeyEvent.VK_0:
				if(getBadAssList().get(0).isEmpty()) return;
				break;
			default:
				return;
		}
//		System.out.println("pressed" + key);
		key = key - 48;
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
		//bug
		if(map.getPowerUpGrids()[j][i] == null) {
			PowerUp temp = getBadAssList().get(key).pop();
			temp.setRow(j);
			temp.setCol(i);
//			temp.setMyPlayer(null);
			map.getPowerUpGrids()[j][i] = temp;
			this.map.getGrids()[j][i] = GridConstants.POWERUP;
			if(key == 1 || key == 8) {
				item1.setText("" + getBadAssList().get(key).size());
			} else if(key == 2 || key == 9) {
				item2.setText("" + getBadAssList().get(key).size());
			} else {
				item3.setText("" + getBadAssList().get(key).size());
			}
			
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
//		if(pu.getMyPlayer() == this) return;
		if(pu.getMyPlayer() != this) {
			pu.setMyPlayer(this);
			pu.takeEffect(pu.getPowertype(),key1,key2,key3);
			map.getPowerUpGrids()[row][col] = null;
			map.getGrids()[row][col] = GridConstants.NOTHING;
		}
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

	public boolean collisionCheckX(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridHeight;
		if((x + dx * delta) < (i * Game.gridWidth)) {
			if(i - 1 >= 0) {
				if(map.getGrids()[j][i - 1] == GridConstants.BRICK || map.getGrids()[j][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j][i - 1] == GridConstants.BOMB && !isBombPassMode())) {
					x = i * Game.gridWidth;
					return false;
				}
				if(map.getGrids()[j][i - 1] == GridConstants.POWERUP) {
					checkPowerUp(j, i - 1);
					return true;
				}
				if(j + 1 < GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && (map.getGrids()[j + 1][i - 1] == GridConstants.BRICK || map.getGrids()[j + 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i - 1] == GridConstants.BOMB && !isBombPassMode()))) {
						x = i * Game.gridWidth;
						return false;
					}
					if((y + width) > (j + 1) * Game.gridWidth && map.getGrids()[j + 1][i - 1] == GridConstants.POWERUP) {
						checkPowerUp(j + 1, i - 1);
						return true;
					}
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && (map.getGrids()[j - 1][i - 1] == GridConstants.BRICK || map.getGrids()[j - 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i - 1] == GridConstants.BOMB && !isBombPassMode()))) {
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
				if(map.getGrids()[j][i + 1] == GridConstants.BRICK || map.getGrids()[j][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j][i + 1] == GridConstants.BOMB && !isBombPassMode())) {
					x = i * Game.gridWidth + diff;
					return false;
				}
				if(map.getGrids()[j][i + 1] == GridConstants.POWERUP) {
					checkPowerUp(j, i + 1);
					return true;
				}
				if(j + 1 < GridConstants.GRIDNUMY) {
					if((y + width) > (j + 1) * Game.gridWidth && (map.getGrids()[j + 1][i + 1] == GridConstants.BRICK || map.getGrids()[j + 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i + 1] == GridConstants.BOMB && !isBombPassMode()))) {
						x = i * Game.gridWidth + diff;
						return false;
					}
					
					if((y + width) > (j + 1) * Game.gridWidth && map.getGrids()[j + 1][i + 1] == GridConstants.POWERUP) {
						checkPowerUp(j + 1, i + 1);
						return true;
					}
					
				}
				if(j - 1 >= 0) {
					if(y < (j) * Game.gridWidth && (map.getGrids()[j - 1][i + 1] == GridConstants.BRICK || map.getGrids()[j - 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i + 1] == GridConstants.BOMB && !isBombPassMode()))) {
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
				return false;
			}
		} 
		
		return true;
		
	}
	
	public boolean collisionCheckY(double delta, Map map) {
		int i = x / Game.gridWidth;
		int j = y / Game.gridWidth;
//		map.getGrids()[getRow()][getCol()] = GridConstants.POWERBRICK
		if ((y + dy * delta) < (j * Game.gridWidth)) {
			if(j - 1 >= 0) {
				if(map.getGrids()[j - 1][i] == GridConstants.BRICK || map.getGrids()[j - 1][i] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i] == GridConstants.BOMB && !isBombPassMode())) {
					y = j * Game.gridWidth;
					return false;
				}
				if(map.getGrids()[j - 1][i] == GridConstants.POWERUP) {
					checkPowerUp(j - 1, i);
					return true;
				}
				if(i + 1 < GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && (map.getGrids()[j - 1][i + 1] == GridConstants.BRICK || map.getGrids()[j - 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i + 1] == GridConstants.BOMB && !isBombPassMode()))) {
						y = j * Game.gridWidth;
						return false;
					}
					if((x + width) > (i + 1) * Game.gridWidth && map.getGrids()[j - 1][i + 1] == GridConstants.POWERUP) {
						checkPowerUp(j - 1, i + 1);
						return true;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && (map.getGrids()[j - 1][i - 1] == GridConstants.BRICK || map.getGrids()[j - 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j - 1][i - 1] == GridConstants.BOMB && !isBombPassMode()))) {
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
				if(map.getGrids()[j + 1][i] == GridConstants.BRICK || map.getGrids()[j + 1][i] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i] == GridConstants.BOMB && !isBombPassMode())) {
					y = j * Game.gridWidth + diff;
					return false;
				}
				if(map.getGrids()[j + 1][i] == GridConstants.POWERUP) {
					checkPowerUp(j + 1, i);
					return true;
				}
				if(i + 1 < GridConstants.GRIDNUMX) {
					if((x + width) > (i + 1) * Game.gridWidth && (map.getGrids()[j + 1][i + 1] == GridConstants.BRICK || map.getGrids()[j + 1][i + 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i + 1] == GridConstants.BOMB && !isBombPassMode()))) {
						y = j * Game.gridWidth + diff;
						return false;
					}
					if((x + width) > (i + 1) * Game.gridWidth && map.getGrids()[j + 1][i + 1] == GridConstants.POWERUP) {
						checkPowerUp(j + 1, i + 1);
						return true;
					}
				}
				if(i - 1 >= 0) {
					if((x) < (i) * Game.gridWidth && (map.getGrids()[j + 1][i - 1] == GridConstants.BRICK || map.getGrids()[j + 1][i - 1] == GridConstants.POWERBRICK || (map.getGrids()[j + 1][i - 1] == GridConstants.BOMB && !isBombPassMode()))) {
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
		boolean checkPlayerX = checkPlayerCollisionX(otherPlayer.getX(),otherPlayer.getY(),delta);
		boolean checkPlayerY = checkPlayerCollisionY(otherPlayer.getX(),otherPlayer.getY(),delta);
//		if(!checkPlayerX) this.x = x - otherPlayer.width;
		if(collisionCheckX(delta,map) && !checkPlayerX) x += dx * delta;
		if(collisionCheckY(delta,map) && !checkPlayerY) y += dy * delta;
	}
	
	public int getDiff() {
		return diff;
	}
	
	public void setDiff(int diff) {
		this.diff = diff;
	}

	public void changeHealthBy(int i) {
		if(health <= 80) {
			health += i;
			p1healthbar.setValue(health);
		}
		
	}
}
