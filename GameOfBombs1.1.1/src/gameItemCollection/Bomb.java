package gameItemCollection;

import characterCollection.Player;
import mapCollection.GridConstants;
import mapCollection.Map;

public class Bomb extends PerishBlock {
	private long creationTime;
	private double timeDuration;
	Map map;
	Player p;
	
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
		p.setBombNum(p.getBombNum() + 1);
		map.setFireGrids(row, col, new Fire(width, height, p.getFireRadius()));
		
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
