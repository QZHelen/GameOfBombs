package powerUpCollection;

import interfaceCollection.Destroyable;
import interfaceCollection.Immovable;

public abstract class PowerUp implements Destroyable, Immovable{
	
	public boolean isDestroyable(){
		return true;
	}
	
	public boolean isMovable(){
		return false;
	}
}
