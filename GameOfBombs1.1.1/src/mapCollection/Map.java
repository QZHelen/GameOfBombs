package mapCollection;

import java.util.ArrayList;

import gameItemCollection.Bomb;
import gameItemCollection.Fire;
import gameItemCollection.Steel;
import powerUpCollection.PowerUp;

public abstract class Map {
	
	int[][] grids;
	ArrayList<Bomb> bombs;
	PowerUp[][] powerUpGrids;
	Fire[][] fireGrids;
	ArrayList<Steel> steels;
	int width;
	int height;
	
	public Map(int width, int height) {
		
	}
}
