package gameItemCollection;

import interfaceCollection.Immovable;
import interfaceCollection.Indestroyable;

public class Steel implements Indestroyable,Immovable{

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
