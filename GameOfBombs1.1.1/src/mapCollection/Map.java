package mapCollection;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

import gameItemCollection.Bomb;
import gameItemCollection.Fire;
import gameItemCollection.Steel;

public abstract class Map {
	
	int[][] grids;
	public int[][] getGrids() {
		return grids;
	}

	public void setGrids(int[][] grids) {
		this.grids = grids;
	}

	PriorityQueue<Bomb> bombs;
	Boolean[][] powerUpGrids;
	Fire[][] fireGrids;
	ArrayList<Steel> steels;
	int width;
	int height;
	Random rand;
	
	public Map(int width, int height) {
		rand = new Random();
		this.grids = new int[GridConstants.GRIDNUMY][GridConstants.GRIDNUMX];
		powerUpGrids =  new Boolean[GridConstants.GRIDNUMY][GridConstants.GRIDNUMX];
		fireGrids = new Fire[GridConstants.GRIDNUMY][GridConstants.GRIDNUMX];
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


	public PriorityQueue<Bomb> getBombs() {
		return bombs;
	}

	public void setBombs(PriorityQueue<Bomb> bombs) {
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

	public ArrayList<Steel> getSteels() {
		return steels;
	}

	public void setSteels(ArrayList<Steel> steels) {
		this.steels = steels;
	}
}
