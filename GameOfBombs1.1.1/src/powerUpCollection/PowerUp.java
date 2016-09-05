package powerUpCollection;

import gameItemCollection.PerishBlock;
import interfaceCollection.Destroyable;
import interfaceCollection.Immovable;

public  class PowerUp extends PerishBlock {
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
