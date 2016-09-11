package mapCollection;


import java.util.Random;

import characterCollection.Player;
import game.Game;
import gameItemCollection.Bomb;
import gameItemCollection.Fire;
import gameItemCollection.Steel;
import powerUpCollection.PowerUp;

public abstract class Map {
	
	int[][] grids;
	Bomb[][] bombGrids;
	PowerUp[][] powerUpGrids;
	Fire[][] fireGrids;
	int width;
	int height;
	Random rand;
	Player p1;
	
	public Map(int width, int height) {
		rand = new Random();
		this.grids = new int[GridConstants.GRIDNUMY][GridConstants.GRIDNUMX];
		powerUpGrids =  new PowerUp[GridConstants.GRIDNUMY][GridConstants.GRIDNUMX];
		fireGrids = new Fire[GridConstants.GRIDNUMY][GridConstants.GRIDNUMX];
//		bombs = new ArrayList<Bomb>();
		bombGrids = new Bomb[GridConstants.GRIDNUMY][GridConstants.GRIDNUMX];
		initGrids(grids);
	}
	

	public Bomb[][] getBombGrids() {
		return bombGrids;
	}


	public void setBombGrids(Bomb[][] bombGrids) {
		this.bombGrids = bombGrids;
	}


	public void initGrids(int[][] grids) {
		for(int i = 1; i < grids.length - 1 ; i = i + rand.nextInt(4) + 2) {
			int count = rand.nextInt(3) + 1;
			for(int col = rand.nextInt(3) + 1;col < grids[1].length - 1; col++ ) {
				if(count-- <= 0) {
					count = rand.nextInt(3) + 1;
					col += rand.nextInt(2) + 1;
				} else {
					grids[i][col] = GridConstants.BRICK;
					
				}
			}
		}
		
		for(int i = 1; i < grids[0].length - 1 ; i = i + rand.nextInt(4) + 2) {
			int count = rand.nextInt(3) + 1;
			for(int row = rand.nextInt(3) + 1;row < grids.length - 1; row++ ) {
				if(count-- <= 0) {
					count = rand.nextInt(3) + 1;
					row += rand.nextInt(2) + 1;
				} else {
					grids[row][i] = GridConstants.BRICK;
				}
			}
		}
		int countRand = 30;
		while(countRand-- > 0) {
			grids[rand.nextInt(grids.length - 2) + 1][rand.nextInt(grids[1].length - 2) + 1] = GridConstants.POWERBRICK;
		}
//		countLast = 12;
//		while(countLast-- > 0) {
//			grids[34][rand.nextInt(grids[1].length - 2) + 1] = GridConstants.BRICK;
//		}
	}

	public int[][] getGrids() {
		return grids;
	}

	public void setGrids(int[][] grids) {
		this.grids = grids;
	}
//	public ArrayList<Bomb> getBombs() {
//		return bombs;
//	}
//
//	public void setBombs(ArrayList<Bomb> bombs) {
//		this.bombs = bombs;
//	}

	public PowerUp[][] getPowerUpGrids() {
		return powerUpGrids;
	}

	public void setPowerUpGrids(PowerUp[][] powerUpGrids) {
		this.powerUpGrids = powerUpGrids;
	}

	public Fire[][] getFireGrids() {
		return fireGrids;
	}

	public void setFireGrids(Fire[][] fireGrids) {
		this.fireGrids = fireGrids;
	}
	
	public void setFireGrids(int row, int col, Fire centerFire) {
		int radius = centerFire.getRadius();
		fireGrids[row][col] = centerFire;
		int rowstart,rowend,colstart,colend;
		rowstart = row - radius;
		rowend = row + radius;
		colstart = col - radius;
		colend = col + radius;
		if(rowstart < 0) rowstart = 0;
		if(rowend > GridConstants.GRIDNUMY - 1) rowend = GridConstants.GRIDNUMY - 1;
		if(colstart < 0) colstart = 0;
		if(colend > GridConstants.GRIDNUMX - 1) colend = GridConstants.GRIDNUMX - 1;
		for(int i = row - 1; i >= rowstart; i--) {
			if(grids[i][col] == GridConstants.NOTHING) {
				fireGrids[i][col] = centerFire;
				continue;
			}
			if(grids[i][col] == GridConstants.BRICK) {
				grids[i][col] = GridConstants.NOTHING;
				fireGrids[i][col] = centerFire;
				break;
			}
			if(grids[i][col] == GridConstants.POWERBRICK) {
				grids[i][col] = GridConstants.POWERUP;
				powerUpGrids[i][col] = PowerUp.powerUpFactory(i, col, (int) (Game.gridWidth * .9), (int) (Game.gridHeight * .9) );
				fireGrids[i][col] = centerFire;
				break;
			}

			if(grids[i][col] == GridConstants.STEEL) {
				break;
			}
			if(grids[i][col] == GridConstants.BOMB) {
//				Bomb b;
//			    if(!getBombs().isEmpty()) {
//			    	for(Iterator<Bomb> iterator = getBombs().iterator();iterator.hasNext();) {
//			    		b = iterator.next();
//			    		if(b.getRow() == i && b.getCol() == col) {
//			    			iterator.remove();
//			    			b.explode();
//			    			p1 = b.getP();
//			    			p1.setBombNum(p1.getBombNum() + 1);
////				    		iterator.remove();
//			    		}
//			    	}
//			    	b = null;
//			    }
			    if(bombGrids[i][col] != null) {
			    	Bomb temp = bombGrids[i][col];
			    	bombGrids[i][col] = null;
			    	temp.explode();
//			    	p1 = temp.getP();
//	    			p1.setBombNum(p1.getBombNum() + 1);
			    }
			}
			
		}
		for(int i = row + 1; i <= rowend; i++) {
			if(grids[i][col] == GridConstants.NOTHING) {
				fireGrids[i][col] = centerFire;
				continue;
			}
			if(grids[i][col] == GridConstants.BRICK) {
				grids[i][col] = GridConstants.NOTHING;
				fireGrids[i][col] = centerFire;
				break;
			}
			if(grids[i][col] == GridConstants.POWERBRICK) {
				grids[i][col] = GridConstants.POWERUP;
				powerUpGrids[i][col] = PowerUp.powerUpFactory(i, col, (int) (Game.gridWidth * .9), (int) (Game.gridHeight * .9) );
				fireGrids[i][col] = centerFire;
				break;
			}
			if(grids[i][col] == GridConstants.STEEL) {
				break;
			}
			if(grids[i][col] == GridConstants.BOMB) {
//				Bomb b;
//			    if(!getBombs().isEmpty()) {
//			    	for(Iterator<Bomb> iterator = getBombs().iterator();iterator.hasNext();) {
//			    		b = iterator.next();
//			    		if(b.getRow() == i && b.getCol() == col) {
//			    			iterator.remove();
//			    			b.explode();
//			    			p1 = b.getP();
//			    			p1.setBombNum(p1.getBombNum() + 1);
////				    		iterator.remove();
//			    		}
//			    	}
//			    	b = null;
//			    }
				if(bombGrids[i][col] != null) {
			    	Bomb temp = bombGrids[i][col];
			    	bombGrids[i][col] = null;
			    	temp.explode();
//			    	p1 = temp.getP();
//	    			p1.setBombNum(p1.getBombNum() + 1);
			    }
			}
		}
		for(int i = col - 1; i >= colstart; i--) {
			if(grids[row][i] == GridConstants.NOTHING) {
				fireGrids[row][i] = centerFire;
				continue;
			}
			if(grids[row][i] == GridConstants.BRICK) {
				grids[row][i] = GridConstants.NOTHING;
				fireGrids[row][i] = centerFire;
				break;
			}
			if(grids[row][i] == GridConstants.POWERBRICK) {
				grids[row][i] = GridConstants.POWERUP;
				powerUpGrids[row][i] = PowerUp.powerUpFactory(row, i, (int) (Game.gridWidth * .9), (int) (Game.gridHeight * .9) );
				fireGrids[row][i] = centerFire;
				break;
			}
			if(grids[row][i] == GridConstants.STEEL) {
				break;
			}
			if(grids[row][i] == GridConstants.BOMB) {
//				Bomb b;
//			    if(!getBombs().isEmpty()) {
//			    	for(Iterator<Bomb> iterator = getBombs().iterator();iterator.hasNext();) {
//			    		b = iterator.next();
//			    		if(b.getRow() == row && b.getCol() == i) {
//			    			iterator.remove();
//			    			b.explode();
//			    			p1 = b.getP();
//			    			p1.setBombNum(p1.getBombNum() + 1);
////				    		iterator.remove();
//			    		}
//			    	}
//			    	b = null;
//			    }
				if(bombGrids[row][i] != null) {
			    	Bomb temp = bombGrids[row][i];
			    	bombGrids[row][i] = null;
			    	temp.explode();
//			    	p1 = temp.getP();
//	    			p1.setBombNum(p1.getBombNum() + 1);
			    }
			}
		}
		for(int i = col + 1; i <= colend; i++) {
			if(grids[row][i] == GridConstants.NOTHING) {
				fireGrids[row][i] = centerFire;
				continue;
			}
			if(grids[row][i] == GridConstants.BRICK) {
				grids[row][i] = GridConstants.NOTHING;
				fireGrids[row][i] = centerFire;
				break;
			}
			if(grids[row][i] == GridConstants.POWERBRICK) {
				grids[row][i] = GridConstants.POWERUP;
				powerUpGrids[row][i] = PowerUp.powerUpFactory(row, i, (int) (Game.gridWidth * .9), (int) (Game.gridHeight * .9) );
				fireGrids[row][i] = centerFire;
				break;
			}
			if(grids[row][i] == GridConstants.STEEL) {
				break;
			}
			if(grids[row][i] == GridConstants.BOMB) {
//				Bomb b;
//			    if(!getBombs().isEmpty()) {
//			    	for(Iterator<Bomb> iterator = getBombs().iterator();iterator.hasNext();) {
//			    		b = iterator.next();
//			    		if(b.getRow() == row && b.getCol() == i) {
//			    			iterator.remove();
//			    			b.explode();
//			    			p1 = b.getP();
//			    			p1.setBombNum(p1.getBombNum() + 1);
////				    		iterator.remove();
//			    		}
//			    	}
//			    	b = null;
//			    }
				if(bombGrids[row][i] != null) {
			    	Bomb temp = bombGrids[row][i];
			    	bombGrids[row][i] = null;
			    	temp.explode();
//			    	p1 = temp.getP();
//	    			p1.setBombNum(p1.getBombNum() + 1);
			    }
			}
		}
		
		
		
	}
	
	
}
