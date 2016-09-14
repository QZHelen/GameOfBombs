package game;

import java.util.TimerTask;

import characterCollection.Player;

public class BombPassTimerTask extends TimerTask {
	Player p;
	public BombPassTimerTask(Player p) {
		// TODO Auto-generated constructor stub
		this.p = p;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		p.setBombPassMode(false);
		Game.bombPasstimer.cancel();
		Game.bombPasstimer = null;
	}

}
