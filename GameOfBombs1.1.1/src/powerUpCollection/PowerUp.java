package powerUpCollection;

import interfaceCollection.Destroyable;
import interfaceCollection.Immovable;

public abstract class PowerUp implements Destroyable, Immovable{
	protected enum PowerUpType {FIREUP, FIREDOWN, BOMBUP, BOMBDOWN, HEAART, LIFE, INVINCIBLEVEST, SPEEDUP, SPEEDDOWN, BOMBPASS}; 
	
	abstract public PowerUpType getPowerUpType();
	
	public boolean isDestroyable(){
		return true;
	}
	
	public boolean isMovable(){
		return false;
	}
}
