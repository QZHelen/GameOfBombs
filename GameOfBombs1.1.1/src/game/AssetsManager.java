package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AssetsManager {
	Image brick;
	public AssetsManager() throws IOException {
		// TODO Auto-generated constructor stub
		brick = ImageIO.read(new File("src/images/brick.png")); 
	}
	public Image getBrick() {
		return brick;
	}

}
