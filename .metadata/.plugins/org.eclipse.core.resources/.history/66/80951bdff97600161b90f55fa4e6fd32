package gameItemCollection;

import characterCollection.Player;
import game.Game;
import mapCollection.Map;

public class Bomb extends PerishBlock {
	private long creationTime;
	private double timeDuration;
	boolean explode;
	Map map;
	Player p;
	
	public Bomb(int x, int y, int width, int height, boolean explode, Map map, Player p) {
		super(x, y, width, height);
		this.creationTime = System.nanoTime();
		this.timeDuration = mapCollection.GridConstants.TIMESPAN;
		this.explode = false;
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
		map.setFireGrids(x/Game.gridWidth, y/Game.gridHeight, new Fire(x, y, width, height, p.getFireRadius()));
	}
	
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	

}
