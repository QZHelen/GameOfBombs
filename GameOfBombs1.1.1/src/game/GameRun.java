package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JProgressBar;

import javazoom.jl.decoder.JavaLayerException;
import mapCollection.*;


public class GameRun {
	
	static JFrame frame;
	public static Game game;
	public static Map gameMap;
	static JMenuBar jmbar;
	
	public static void main(String[] args) throws IOException {
		gameSetUp();
	}
	
	public static void gameSetUp() throws IOException {
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
		BorderLayout border = new BorderLayout(); 
		Game.gridWidth = Game.maxWidth / GridConstants.GRIDNUMX;
		Game.gridHeight = Game.maxHeight / GridConstants.GRIDNUMY;
		frame = new JFrame("Test");
		frame.pack();
		Insets insets = frame.getInsets();
		frame.setMaximumSize(new Dimension(Game.maxWidth,Game.maxHeight + insets.top + Game.panelHeight));
		
		frame.setSize(Game.maxWidth,Game.maxHeight + insets.top + Game.panelHeight);
		frame.setLocationRelativeTo(null);
		gameMap = new IceMap(Game.maxWidth,Game.maxHeight,null,null);
		game = new Game(gameMap);
		gameMap.setP1(game.getP1());
		gameMap.setP2(game.getP2());
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
		game.getP1().p1healthbar = new JProgressBar(0, 100);
		game.getP1().p1healthbar.setValue(100);
		Game.tPanel.add(Box.createRigidArea(new Dimension(20,0)));
		game.getP1().p1healthbar.setMaximumSize(new Dimension(Game.maxWidth / 8,100));
		game.getP1().p1healthbar.setStringPainted(true);
		Game.tPanel.add(game.getP1().p1healthbar);
		Game.tPanel.add(Box.createRigidArea(new Dimension(10,0)));
		//image icon setting
		Image heart = Game.assetsManager.getHeart(); 
		ImageIcon heartIcon = new ImageIcon();
		heart = heart.getScaledInstance(Game.gridWidth,Game.gridHeight, Image.SCALE_SMOOTH);
		heartIcon.setImage(heart);
		
		Image fireDown = Game.assetsManager.getFireDown(); 
		ImageIcon fireIcon = new ImageIcon();
		fireDown = fireDown.getScaledInstance(Game.panelHeight / 2,Game.panelHeight / 2, Image.SCALE_SMOOTH);
		fireIcon.setImage(fireDown);
		
		Image bombDown = Game.assetsManager.getBombChange(); 
		ImageIcon bombIcon = new ImageIcon();
		bombDown = bombDown.getScaledInstance(Game.panelHeight / 2,Game.panelHeight / 2, Image.SCALE_SMOOTH);
		bombIcon.setImage(bombDown);
		
		Image speedDown = Game.assetsManager.getSpeedDown(); 
		ImageIcon speedIcon = new ImageIcon();
		speedDown = speedDown.getScaledInstance(Game.panelHeight / 2,Game.panelHeight / 2, Image.SCALE_SMOOTH);
		speedIcon.setImage(speedDown);
		
		//top panel set up
		game.getP1().hl1 = new CustomLabel(heartIcon);
		game.getP1().hl2 = new CustomLabel(heartIcon);
		game.getP1().hl3 = new CustomLabel(heartIcon);
		game.getP1().hl4 = new CustomLabel(heartIcon);
		game.getP1().hl5 = new CustomLabel(heartIcon);
		game.getP1().hl4.setVisible(false);
		game.getP1().hl5.setVisible(false);
		Game.tPanel.add(game.getP1().hl1);
		Game.tPanel.add(game.getP1().hl2);
		Game.tPanel.add(game.getP1().hl3);
		Game.tPanel.add(game.getP1().hl4);
		Game.tPanel.add(game.getP1().hl5);
		Game.tPanel.add(Box.createHorizontalGlue());
		Game.tPanel.add(new JLabel("P2"));
		Game.tPanel.add(Box.createRigidArea(new Dimension(20,0)));
		game.getP2().p1healthbar = new JProgressBar(0, 100);
		game.getP2().p1healthbar.setValue(100);
		Game.tPanel.add(Box.createRigidArea(new Dimension(20,0)));
		game.getP2().p1healthbar.setMaximumSize(new Dimension(Game.maxWidth / 8,100));
		game.getP2().p1healthbar.setStringPainted(true);
		Game.tPanel.add(game.getP2().p1healthbar);
		Game.tPanel.add(Box.createRigidArea(new Dimension(10,0)));
		game.getP2().hl1 = new CustomLabel(heartIcon);
		game.getP2().hl2 = new CustomLabel(heartIcon);
		game.getP2().hl3 = new CustomLabel(heartIcon);
		game.getP2().hl4 = new CustomLabel(heartIcon);
		game.getP2().hl5 = new CustomLabel(heartIcon);
		game.getP2().hl4.setVisible(false);
		game.getP2().hl5.setVisible(false);
		Game.tPanel.add(game.getP2().hl1);
		Game.tPanel.add(game.getP2().hl2);
		Game.tPanel.add(game.getP2().hl3);
		Game.tPanel.add(game.getP2().hl4);
		Game.tPanel.add(game.getP2().hl5);
		Game.tPanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		//bottom panel set up
		
		game.getP1().item1 = new CustomLabel(fireIcon);
		CustomLabel temp1 = game.getP1().item1;
		temp1.setHorizontalTextPosition(JLabel.CENTER);
		temp1.setVerticalTextPosition(JLabel.CENTER);
		temp1.setFont(new Font("Impact", Font.BOLD, 20));
		temp1.setForeground(Color.WHITE);
		temp1.setText("" + game.getP1().getBadAssList().get(1).size());
		game.getP1().item2 = new CustomLabel(bombIcon);
		CustomLabel temp2 = game.getP1().item2;
		temp2.setHorizontalTextPosition(JLabel.CENTER);
		temp2.setVerticalTextPosition(JLabel.CENTER);
		temp2.setFont(new Font("Impact", Font.BOLD, 20));
		temp2.setForeground(Color.WHITE);
		temp2.setText("" + game.getP1().getBadAssList().get(2).size());
		game.getP1().item3 = new CustomLabel(speedIcon);
		CustomLabel temp3 = game.getP1().item3;
		temp3.setHorizontalTextPosition(JLabel.CENTER);
		temp3.setVerticalTextPosition(JLabel.CENTER);
		temp3.setFont(new Font("Impact", Font.BOLD, 20));
		temp3.setForeground(Color.WHITE);
		temp3.setText("" + game.getP1().getBadAssList().get(3).size());
		Game.bPanel.add(Box.createRigidArea(new Dimension(10,0)));
		Game.bPanel.add(new JLabel("P1 ItemList"));
		Game.bPanel.add(Box.createRigidArea(new Dimension(20,0)));
		Game.bPanel.add(game.getP1().item1);
		Game.bPanel.add(game.getP1().item2);
		Game.bPanel.add(game.getP1().item3);
		
		game.getP2().item1 = new CustomLabel(fireIcon);
		temp1 = game.getP2().item1;
		temp1.setHorizontalTextPosition(JLabel.CENTER);
		temp1.setVerticalTextPosition(JLabel.CENTER);
		temp1.setFont(new Font("Impact", Font.BOLD, 20));
		temp1.setForeground(Color.WHITE);
		temp1.setText("" + game.getP2().getBadAssList().get(8).size());
		game.getP2().item2 = new CustomLabel(bombIcon);
		temp2 = game.getP2().item2;
		temp2.setHorizontalTextPosition(JLabel.CENTER);
		temp2.setVerticalTextPosition(JLabel.CENTER);
		temp2.setFont(new Font("Impact", Font.BOLD, 20));
		temp2.setForeground(Color.WHITE);
		temp2.setText("" + game.getP2().getBadAssList().get(9).size());
		game.getP2().item3 = new CustomLabel(speedIcon);
		temp3 = game.getP2().item3;
		temp3.setHorizontalTextPosition(JLabel.CENTER);
		temp3.setVerticalTextPosition(JLabel.CENTER);
		temp3.setFont(new Font("Impact", Font.BOLD, 20));
		temp3.setForeground(Color.WHITE);
		temp3.setText("" + game.getP2().getBadAssList().get(0).size());
		//add labels to panel
		Game.bPanel.add(Box.createHorizontalGlue());
		Game.bPanel.add(new JLabel("P2 ItemList"));
		Game.bPanel.add(Box.createRigidArea(new Dimension(20,0)));
		Game.bPanel.add(game.getP2().item1);
		Game.bPanel.add(game.getP2().item2);
		Game.bPanel.add(game.getP2().item3);
		
		
		//jframe set up
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
		backgroundMusic();
	}
	
	public static void backgroundMusic() {
		try {
			boolean loop = true;
			do{
				File f = new File("src/audio/background.mp3");
				FileInputStream fs = new FileInputStream(f);
				BufferedInputStream bs = new BufferedInputStream(fs);
				javazoom.jl.player.Player player = new javazoom.jl.player.Player(bs);
				player.play();
			} while(loop);
			
		    
		}	 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
