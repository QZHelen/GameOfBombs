package powerUpCollection;

import gameItemCollection.PerishBlock;
import interfaceCollection.Destroyable;
import interfaceCollection.Immovable;

public  class PowerUp extends PerishBlock {
	
	
	public static PowerUp powerUpFactory(int row, int col, int width, int height) {
		return new PowerUp(row, col, width, height);
	}
	
	protected PowerUp(int row, int col, int width, int height) {
		super(row, col, width, height);
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
