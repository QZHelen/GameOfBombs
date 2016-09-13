package game;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
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
		xSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
		ySize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
		ySize -= ySize % GridConstants.GRIDNUMY;
		Game.maxHeight = (int) (xSize * GridConstants.RATIO);
		Game.maxWidth = (int) (ySize / GridConstants.RATIO);
		if(Game.maxHeight > ySize) {
			Game.maxHeight = ySize;
		} else {
			Game.maxWidth = xSize;
		}
		System.out.println(Game.maxHeight + " " + Game.maxWidth);
		Game.gridWidth = Game.maxWidth / GridConstants.GRIDNUMX;
		Game.gridHeight = Game.maxHeight / GridConstants.GRIDNUMY;
		System.out.println(Game.gridWidth + " " + Game.gridHeight);
		frame = new JFrame("Test");
		frame.pack();
		Insets insets = frame.getInsets();
//		System.out.println(insets.bottom);
		frame.setMaximumSize(new Dimension(Game.maxWidth,Game.maxHeight + insets.top));
		
		frame.setSize(Game.maxWidth,Game.maxHeight + insets.top);
		frame.setLocationRelativeTo(null);
		gameMap = new IceMap(Game.maxWidth,Game.maxHeight,null);
		game = new Game(gameMap);
		gameMap.setP1(game.getP1());
		frame.getContentPane().add(game);
		game.setFocusable(true);
		game.requestFocusInWindow();
		game.setIgnoreRepaint(true);
		game.createBufferStrategy(2);
		game.strategy = game.getBufferStrategy();
		new Thread(game).start();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
