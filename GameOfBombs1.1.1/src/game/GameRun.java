package game;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import mapCollection.*;


public class GameRun {
	
	static JFrame frame;
	public static Game game;
	public static Map gameMap;
	
	public static void main(String[] args) {
		gameSetUp();
	}
	
	public static void gameSetUp() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		frame = new JFrame("Test");
		frame.pack();
		frame.setMaximumSize(new Dimension(xSize,740));
		frame.setSize(xSize,740);
		gameMap = new IceMap(xSize,ySize);
		game = new Game(gameMap, xSize,720);
		frame.getContentPane().add(game);
		game.setFocusable(true);
		game.requestFocusInWindow();
		game.setIgnoreRepaint(true);
		game.setSize(xSize,720);
		game.createBufferStrategy(2);
		game.strategy = game.getBufferStrategy();
		new Thread(game).start();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
