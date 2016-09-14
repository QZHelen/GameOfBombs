package game;

import javax.swing.Icon;
import javax.swing.JLabel;

public class HeartLabel extends JLabel {
	boolean active;
	public HeartLabel() {
		// TODO Auto-generated constructor stub
		active = true;
	}

	public HeartLabel(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public HeartLabel(Icon image) {
		super(image);
		
		// TODO Auto-generated constructor stub
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public HeartLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		// TODO Auto-generated constructor stub
	}

	public HeartLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		// TODO Auto-generated constructor stub
	}

	public HeartLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		// TODO Auto-generated constructor stub
	}

}
