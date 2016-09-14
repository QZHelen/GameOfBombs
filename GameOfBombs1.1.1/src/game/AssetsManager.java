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
	public AssetsManager() throws IOException {
		// TODO Auto-generated constructor stub
		brick = ImageIO.read(new File("src/images/brick.png")); 
		background = ImageIO.read(new File("src/images/lawn.jpg")); 
		bombmanFace = ImageIO.read(new File("src/images/bombface.png")); 
		bomb = ImageIO.read(new File("src/images/bomb.png"));
	}
	public Image getBrick() {
		return brick;
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

}
