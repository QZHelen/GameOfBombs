package game;

import java.util.TimerTask;

import characterCollection.AI;

public class PathTimerTask extends TimerTask {
	AI p;
	public PathTimerTask(AI p) {
		this.p = p;
	}

	@Override
	public void run() {
//		if(p.prevdir == 3) {
//			p.prevdir = Map.rand.nextInt(3);
//		} else if (p.prevdir == 2) {
//			p.prevdir = p.arr1[Map.rand.nextInt(p.arr1.length)];
//		} else if (p.prevdir == 1) {
//			p.prevdir = p.arr2[Map.rand.nextInt(p.arr2.length)];
//		} else {
//			p.prevdir = Map.rand.nextInt(3) + 1;
//		}
//		p.changePath = true;
		if(p.fireStay) {
			p.fireStay = false;
		}
//		p.bombStay = false;
		p.getPathTimer().cancel();
		p.setPathTimer(null);
	}
}
