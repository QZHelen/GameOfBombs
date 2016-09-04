package gameItemCollection;

import interfaceCollection.Destroyable;
import interfaceCollection.Immovable;

public class PerishBlock implements Destroyable,Immovable {
	
	@Override
	public boolean isMovable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDestroyable() {
		// TODO Auto-generated method stub
		return false;
	}

}
