package gameItemCollection;

public class Fire extends PerishBlock {
	
	private int radius;
	private long creationTime;
	private double timeDuration;
	
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getTimeDuration() {
		return timeDuration;
		//
	}

	
	public Fire(int x, int y, int width, int height, int radius) {
		super(x, y, width, height);
		this.creationTime = System.nanoTime();
		this.timeDuration = 1.0;
		this.radius = radius;
	}

	public Fire(int width, int height, int radius) {
		super(width, height);
		this.creationTime = System.nanoTime();
		this.timeDuration = 1.0;
		this.radius = radius;
	}

}
