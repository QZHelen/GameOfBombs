package gameItemCollection;

import java.util.TimerTask;

import characterCollection.Player;

public class GodModTimerTask extends TimerTask {
	Player p;
	public GodModTimerTask(Player p) {
		this.p = p;
	}

	@Override
	public void run() {
		p.setGodMode(false);
		p.getGodModetimer().cancel();
		p.setGodModetimer(null);
	}

}
