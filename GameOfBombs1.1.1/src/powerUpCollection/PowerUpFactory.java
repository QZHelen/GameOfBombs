package powerUpCollection;

import characterCollection.Player;

public class PowerUpFactory {
	public PowerUp createBasicType(int row, int col, int width, int height,Player myplayer) {
		
		return new PowerUp(row, col, width, height, PowerUpType.randomBasicType(), myplayer);
		
		
	}
	
	public PowerUp createAdvancedType(int row, int col, int width, int height,Player myplayer) {
		
		return new PowerUp(row, col, width, height, PowerUpType.randomAdvancedType(), myplayer);
		
		
	}
	public PowerUp createType(int row, int col, int width, int height,Player myplayer) {
		
		return new PowerUp(row, col, width, height, PowerUpType.randomType(), myplayer);
		
		
	}
}
