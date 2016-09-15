package game;

import java.util.TimerTask;

import characterCollection.Player;

public class BombPassTimerTask extends TimerTask {
	Player p;
	public BombPassTimerTask(Player p) {
		this.p = p;
	}

	@Override
	public void run() {
		p.setBombPassMode(false);
		p.getBombPasstimer().cancel();
		p.setBombPasstimer(null);
	}

}
