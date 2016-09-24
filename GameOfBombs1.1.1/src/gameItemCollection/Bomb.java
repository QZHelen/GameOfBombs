package gameItemCollection;

import java.util.ArrayList;

import org.javatuples.Pair;

import characterCollection.Player;
import mapCollection.GridConstants;
import mapCollection.Map;

public class Bomb extends PerishBlock {
	private long creationTime;
	private double timeDuration;
	public static final int MAXFIRERAD = 6;
	Map map;
	Player p;
//	AI monster;
	public Bomb(int col, int row, int width, int height,Map map, Player p) {
		super(col, row, width, height);
		this.creationTime = System.nanoTime();
		this.timeDuration = mapCollection.GridConstants.TIMESPAN;
		this.map = map;
		this.p = p;
	}

	
	public boolean isExplode() {
		return ((System.nanoTime() - this.creationTime) / 1000000000.0 > this.timeDuration);
	}

	public long getCreationTime() {
		return creationTime;
	}
	public void explode() {
		map.getGrids()[row][col] = GridConstants.NOTHING;
		map.setWalkable(row, col);
		p.setBombNum(p.getBombNum() + 1);
		map.setFireGrids(row, col, new Fire(width, height, p.getFireRadius()));
		
	}
	public ArrayList<Pair<Integer, Integer>> range() {
		int radius = p.getFireRadius();
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
//		bombs.add(new Pair<>(row,col));
		return bombs;
	}
	
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}
	

}
