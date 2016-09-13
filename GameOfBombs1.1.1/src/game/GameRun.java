package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import mapCollection.*;


public class GameRun {
	
	static JFrame frame;
	public static Game game;
	public static Map gameMap;
	static JMenuBar jmbar;
	static JProgressBar p1healthbar;
	JProgressBar p2healthbar;
	
	public static void main(String[] args) {
		gameSetUp();
	}
	
	public static void gameSetUp() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		xSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
		ySize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
		Game.panelHeight = (int) ((int) ySize * .1);
		ySize -= Game.panelHeight;
		ySize -= ySize % GridConstants.GRIDNUMY;
		Game.maxHeight = (int) (xSize * GridConstants.RATIO);
		Game.maxWidth = (int) (ySize / GridConstants.RATIO);
		if(Game.maxHeight > ySize) {
			Game.maxHeight = ySize;
		} else {
			Game.maxWidth = xSize;
		}
		System.out.println(Game.maxHeight + " " + Game.maxWidth);
		BorderLayout border = new BorderLayout(); 
		Game.gridWidth = Game.maxWidth / GridConstants.GRIDNUMX;
		Game.gridHeight = Game.maxHeight / GridConstants.GRIDNUMY;
		System.out.println(Game.gridWidth + " " + Game.gridHeight);
		frame = new JFrame("Test");
		frame.pack();
		Insets insets = frame.getInsets();
//		System.out.println(insets.bottom);
		frame.setMaximumSize(new Dimension(Game.maxWidth,Game.maxHeight + insets.top + Game.panelHeight));
		
		frame.setSize(Game.maxWidth,Game.maxHeight + insets.top + Game.panelHeight);
		frame.setLocationRelativeTo(null);
		gameMap = new IceMap(Game.maxWidth,Game.maxHeight,null);
		game = new Game(gameMap);
		gameMap.setP1(game.getP1());
		Container window = frame.getContentPane();
		window.setLayout(border);
		window.add(game,BorderLayout.CENTER);
		jmbar = new JMenuBar();
		Game.bPanel = new BottomPanel();
		Game.bPanel.setPreferredSize(new Dimension(Game.maxWidth, Game.panelHeight / 2));
		Game.bPanel.setLayout(new BoxLayout(Game.bPanel, BoxLayout.X_AXIS));
		Game.tPanel = new TopPanel();
		Game.tPanel.setPreferredSize(new Dimension(Game.maxWidth, Game.panelHeight / 2));
		Game.tPanel.setLayout(new BoxLayout(Game.tPanel, BoxLayout.X_AXIS));
		Game.tPanel.add(Box.createRigidArea(new Dimension(10,0)));
		Game.tPanel.add(new JLabel("P1"));
		Game.tPanel.add(Box.createRigidArea(new Dimension(20,0)));
		p1healthbar = new JProgressBar(0, 100);
		p1healthbar.setValue(100);
		p1healthbar.setMinimumSize(new Dimension(Game.maxWidth / 8,100));
		p1healthbar.setMaximumSize(new Dimension(Game.maxWidth / 8,100));
		p1healthbar.setStringPainted(true);
		Game.tPanel.add(p1healthbar);
		window.add(Game.tPanel,BorderLayout.NORTH);
		
		window.add(Game.bPanel, BorderLayout.SOUTH);
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
