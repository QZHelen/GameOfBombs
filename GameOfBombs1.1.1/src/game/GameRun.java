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
		Game.maxHeight = (int) (xSize * GridConstants.RATIO);
		Game.maxWidth = (int) (ySize / GridConstants.RATIO);
		if(Game.maxHeight > ySize) {
			Game.maxHeight = ySize;
		} else {
			Game.maxWidth = xSize;
		}
		Game.gridWidth = Game.maxWidth / GridConstants.GRIDNUMX;
		Game.gridHeight = Game.maxHeight / GridConstants.GRIDNUMY;
		frame = new JFrame("Test");
		frame.pack();
		frame.setMaximumSize(new Dimension(Game.maxWidth,Game.maxHeight));
		frame.setSize(Game.maxWidth,Game.maxHeight);
		gameMap = new IceMap(Game.maxWidth,Game.maxHeight);
		game = new Game(gameMap);
		frame.getContentPane().add(game);
		game.setFocusable(true);
		game.requestFocusInWindow();
		game.setIgnoreRepaint(true);
		game.createBufferStrategy(2);
		game.strategy = game.getBufferStrategy();
		new Thread(game).start();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
