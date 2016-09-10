package mapCollection;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

import gameItemCollection.Bomb;
import gameItemCollection.Fire;
import gameItemCollection.Steel;

public abstract class Map {
	
	int[][] grids;
	ArrayList<Bomb> bombs;
	Boolean[][] powerUpGrids;
	Fire[][] fireGrids;
	int width;
	int height;
	Random rand;
	
	public Map(int width, int height) {
		rand = new Random();
		this.grids = new int[GridConstants.GRIDNUMY][GridConstants.GRIDNUMX];
		powerUpGrids =  new Boolean[GridConstants.GRIDNUMY][GridConstants.GRIDNUMX];
		fireGrids = new Fire[GridConstants.GRIDNUMY][GridConstants.GRIDNUMX];
		bombs = new ArrayList<Bomb>();
		initGrids(grids);
	}
	
	public void initGrids(int[][] grids) {
		for(int i = 1; i < grids.length - 1 ; i = i + rand.nextInt(4) + 2) {
			int count = rand.nextInt(3) + 1;
			for(int col = rand.nextInt(3) + 1;col < grids[1].length - 1; col++ ) {
				if(count-- <= 0) {
					count = rand.nextInt(3) + 1;
				} else {
					grids[i][col] = GridConstants.BRICK;
				}
			}
		}
		
		int countRand = 190;
		while(countRand-- > 0) {
			grids[rand.nextInt(grids.length - 2) + 1][rand.nextInt(grids[1].length - 2) + 1] = GridConstants.BRICK;
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
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}

	public void setBombs(ArrayList<Bomb> bombs) {
		this.bombs = bombs;
	}

	public Boolean[][] getPowerUpGrids() {
		return powerUpGrids;
	}

	public void setPowerUpGrids(Boolean[][] powerUpGrids) {
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
			if(grids[i][col] == GridConstants.STEEL) {
				break;
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
			if(grids[i][col] == GridConstants.STEEL) {
				break;
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
			if(grids[row][i] == GridConstants.STEEL) {
				break;
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
			if(grids[row][i] == GridConstants.STEEL) {
				break;
			}
		}
		
		
		
	}
	
	
}
