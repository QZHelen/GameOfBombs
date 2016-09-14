package powerUpCollection;

import java.awt.Color;
import java.util.Timer;

import characterCollection.Player;
import game.BombPassTimerTask;
import game.Game;
import game.GodModTimerTask;
import gameItemCollection.PerishBlock;
import interfaceCollection.Destroyable;
import interfaceCollection.Immovable;

public class PowerUp extends PerishBlock {
	
	PowerUpType powertype;
	Player myPlayer;
//	public static PowerUp powerUpFactory(int row, int col, int width, int height) {
//		return new PowerUp(row, col, width, height);
//	}
	//FIREUP, BOMBUP, SPEEDUP, HEARTUP, FIREDOWN, BOMBDOWN, SPEEDDOWN,BOMBPASS,LIFEUP,INVINCIBLEVEST,MAXHEALTH;
	
	public PowerUpType getPowertype() {
		return powertype;
	}

	public void setPowertype(PowerUpType powertype) {
		this.powertype = powertype;
	}

	public Player getMyPlayer() {
		return myPlayer;
	}

	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	protected PowerUp(int row, int col, int width, int height,PowerUpType powerUpType,Player myplayer) {
		super(row, col, width, height);
		powertype = powerUpType;
		this.myPlayer = myplayer;
	}
	
	public void takeEffect(PowerUpType powertype) {
		switch(powertype) {
			case FIREUP:
				myPlayer.changeFireRadiusBy(1);
				break;
			case BOMBUP:
				myPlayer.changeBombNumBy(1);
				break;
			case SPEEDUP:
				myPlayer.changeSpeedBy(.5);;
				break;
			case HEARTUP:
				myPlayer.changeHealthBy(10);
				break;
			case FIREDOWN:
				myPlayer.changeFireRadiusBy(-1);
				break;
			case BOMBDOWN:
				myPlayer.changeBombNumBy(-1);
				break;
			case SPEEDDOWN:
				myPlayer.changeSpeedBy(-.5);
				break;
			case BOMBPASS:
				myPlayer.setBombPassMode(true);
				if(Game.bombPasstimer == null) {
					Game.bombPasstimer = new Timer();
					Game.bombPasstimer.schedule(new BombPassTimerTask(myPlayer), 10 * 1000);
				}
				break;
			case LIFEUP:
				myPlayer.changeLifeBy(1);
				break;
			case INVINCIBLEVEST:
				myPlayer.setGodMode(true);
				break;
			case MAXHEALTH:
				myPlayer.setHealth(100);
				break;
			default: break;
		}
	}
	
	public Color renderColor(PowerUpType powertype) {
		switch(powertype) {
		case FIREUP:
			return Color.DARK_GRAY;
		case BOMBUP:
			return Color.GRAY;
		case SPEEDUP:
			return Color.GREEN;
		case HEARTUP:return Color.LIGHT_GRAY;
		case FIREDOWN:return Color.MAGENTA;
		case BOMBDOWN:return Color.ORANGE;
		case SPEEDDOWN:return Color.PINK;
		case BOMBPASS:return Color.WHITE;
		case LIFEUP:return new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
		case INVINCIBLEVEST:return new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
		case MAXHEALTH:return new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
		default: return Color.YELLOW;
		}
	}
	public boolean isDestroyable(){
		return true;
	}
	
	public boolean isMovable(){
		return false;
	}
	
	
	
	
}
