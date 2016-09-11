package powerUpCollection;

import characterCollection.Player;
import gameItemCollection.PerishBlock;
import interfaceCollection.Destroyable;
import interfaceCollection.Immovable;

public class PowerUp extends PerishBlock {
	
	PowerUpType powertype;
	Player myPlayer;
//	public static PowerUp powerUpFactory(int row, int col, int width, int height) {
//		return new PowerUp(row, col, width, height);
//	}
	
	protected PowerUp(int row, int col, int width, int height,PowerUpType powerUpType,Player myplayer) {
		super(row, col, width, height);
		powertype = powerUpType;
		this.myPlayer = myplayer;
	}
	
	public boolean isDestroyable(){
		return true;
	}
	
	public boolean isMovable(){
		return false;
	}
	
	
	
	
}
