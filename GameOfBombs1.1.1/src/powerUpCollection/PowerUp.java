package powerUpCollection;

import java.awt.Color;
import java.util.Timer;

import characterCollection.Player;
import game.BombPassTimerTask;
import gameItemCollection.PerishBlock;

import interfaceCollection.Pickable;

public class PowerUp extends PerishBlock implements Pickable {
	
	PowerUpType powertype;
	Player myPlayer;
	boolean pickable;
	
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
		if(powerUpType == PowerUpType.BOMBDOWN || powerUpType == PowerUpType.FIREDOWN || powerUpType == PowerUpType.SPEEDDOWN) pickable = true;
		else pickable = false;
	}
	
	public void takeEffect(PowerUpType powertype) {
		switch(powertype) {
			case FIREUP:
				myPlayer.getPowerUpList().add(this);
				myPlayer.changeFireRadiusBy(1);
				break;
			case BOMBUP:
				myPlayer.getPowerUpList().add(this);
				myPlayer.changeBombNumBy(1);
				break;
			case SPEEDUP:
				myPlayer.getPowerUpList().add(this);
				myPlayer.changeSpeedBy(.5);;
				break;
			case HEARTUP:
				myPlayer.getPowerUpList().add(this);
				myPlayer.changeHealthBy(10);
				break;
			case FIREDOWN:
				if(pickable) {
					pickable = false;
					myPlayer.getBadAssList().get(1).push(this);
					
				} else
					myPlayer.changeFireRadiusBy(-1);
				break;
			case BOMBDOWN:
				if(pickable) {
					pickable = false;
					myPlayer.getBadAssList().get(2).push(this);
				} else 
					myPlayer.changeBombNumBy(-1);
				break;
			case SPEEDDOWN:
				if(pickable) {
					myPlayer.getBadAssList().get(3).push(this);
					pickable = false;
				} else
					myPlayer.changeSpeedBy(-.5);
				break;
			case BOMBPASS:
				myPlayer.getPowerUpList().add(this);
				myPlayer.setBombPassMode(true);
				if(myPlayer.getBombPasstimer() == null) {
					myPlayer.setBombPasstimer(new Timer());
					myPlayer.getBombPasstimer().schedule(new BombPassTimerTask(myPlayer), 10 * 1000);
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

	@Override
	public boolean isPickable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
}
