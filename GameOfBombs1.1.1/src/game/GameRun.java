package game;

import java.awt.Toolkit;

import javax.swing.JFrame;


public class GameRun {
	static JFrame frame;
	public static Game game;
	public static void main(String[] args) {
		frame = new JFrame("Test");
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		frame.setSize(xSize,ySize);
		frame.pack();
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		game = new Game(null, xSize,ySize);
		frame.getContentPane().add(game);
		game.setFocusable(true);
		game.requestFocusInWindow();
		game.setIgnoreRepaint(true);
//		System.out.println(xSize + " " + ySize);
		game.setSize(xSize,ySize);
		game.createBufferStrategy(2);
		game.strategy = game.getBufferStrategy();
		new Thread(game).start();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
}
