package powerUpCollection;

import java.util.Random;

public enum PowerUpType {
	FIREUP, BOMBUP, SPEEDUP, HEARTUP, FIREDOWN, BOMBDOWN, SPEEDDOWN,BOMBPASS,LIFEUP,INVINCIBLEVEST,MAXHEALTH; 
	static Random rand = new Random();
	public static PowerUpType randomBasicType() {
		return values()[rand.nextInt(4)];
	}
	public static PowerUpType randomAdvancedType() {
		return values()[rand.nextInt(values().length - 4) + 4];
	}
	public static PowerUpType randomType() {
		return values()[rand.nextInt(values().length)];
	}
	

}
