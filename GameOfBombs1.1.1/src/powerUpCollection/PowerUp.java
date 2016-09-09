package powerUpCollection;

import gameItemCollection.PerishBlock;
import interfaceCollection.Destroyable;
import interfaceCollection.Immovable;

public  class PowerUp extends PerishBlock {
	public PowerUp(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	protected enum PowerUpType {FIREUP, FIREDOWN, BOMBUP, BOMBDOWN, HEAART, LIFE, INVINCIBLEVEST, SPEEDUP, SPEEDDOWN, BOMBPASS}; 
	
	public PowerUpType getPowerUpType() {
		return null;
	}
	
	public boolean isDestroyable(){
		return true;
	}
	
	public boolean isMovable(){
		return false;
	}
}
