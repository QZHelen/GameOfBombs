package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AssetsManager {
	
	Image brick;
	Image background;
	Image bombmanFace;
	Image bomb;
	Image heart;
	Image fire;
	Image speed;
	Image godMode;
	Image bombPass;
	Image life;
	Image maxHealth;
	Image bombChange;
	Image ghost;
	
	public AssetsManager() throws IOException {
		brick = ImageIO.read(new File("src/images/brick.png")); 
		background = ImageIO.read(new File("src/images/lawn.jpg")); 
		bombmanFace = ImageIO.read(new File("src/images/bombface.png")); 
		bomb = ImageIO.read(new File("src/images/bomb.png"));
		heart = ImageIO.read(new File("src/images/heart.png"));
		fire = ImageIO.read(new File("src/images/firechange.png"));
		speed = ImageIO.read(new File("src/images/speedchange.png"));
		godMode = ImageIO.read(new File("src/images/godmode.png"));
		bombPass = ImageIO.read(new File("src/images/bombpass.png"));
		life = ImageIO.read(new File("src/images/lifechange.png"));
		maxHealth = ImageIO.read(new File("src/images/heartchange.png"));
		bombChange = ImageIO.read(new File("src/images/bombchange.png"));
		ghost = ImageIO.read(new File("src/images/ghost.gif"));
	}
	
	public Image getBrick() {
		return brick;
	}
	public Image getGhost() {
		return ghost;
	}
	public Image getBackground() {
		return background;
	}
	public Image getBombManFace() {
		return bombmanFace;
	}
	public Image getBomb() {
		return bomb;
	}
	public Image getHeart() {
		return heart;
	}
	public Image getBombDown() {
		return bomb;
	}
	public Image getMaxHealth() {
		return maxHealth;
	}
	public void setBombDown(Image bomb) {
		this.bomb = bomb;
	}
	public Image getFireDown() {
		return fire;
	}
	public Image getSpeedDown() {
		return speed;
	}
	public Image getGodMode() {
		return godMode;
	}
	public Image getBombPass() {
		return bombPass;
	}
	public Image getLife() {
		return life;
	}
	public Image getBombChange() {
		return bombChange;
	}

}
