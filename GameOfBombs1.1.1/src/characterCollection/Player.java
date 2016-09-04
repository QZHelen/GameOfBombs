package characterCollection;

import game.Direction;

public abstract class Player{
	private int x = 20;
	private int y = 20;
	private int width;
	private int height;
	private int dx = 0;
	private int dy = 0;
	private Direction direction;
	public Player(int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setDirection(Direction direction) {
		// TODO Auto-generated method stub
		this.direction = direction;
		
	}
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getDy() {
		return dy;
	}
	public void setDy(int dy) {
		this.dy = dy;
	}
	public void moveLeft() {
		// TODO Auto-generated method stub
		setDirection(Direction.LEFT);
		setDx(-10);
		
	}
	public void moveRight() {
		// TODO Auto-generated method stub
		setDirection(Direction.RIGHT);
		setDx(10);
		
	}
	public void moveUp() {
		// TODO Auto-generated method stub
		setDirection(Direction.UP);
		setDy(-10);
	}
	public void moveDown() {
		// TODO Auto-generated method stub
		setDirection(Direction.DOWN);
		setDy(10);
	}
	
	public void update(double delta) {
		x += dx * delta;
	    y += dy * delta;
	}
}
