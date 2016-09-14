package game;

import java.util.TimerTask;

import characterCollection.Player;

public class GodModTimerTask extends TimerTask {
	Player p;
	public GodModTimerTask(Player p) {
		// TODO Auto-generated constructor stub
		this.p = p;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		p.setGodMode(false);
		Game.godModetimer.cancel();
		Game.godModetimer = null;
	}

}
