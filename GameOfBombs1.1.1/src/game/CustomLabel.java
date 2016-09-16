package game;

import javax.swing.Icon;
import javax.swing.JLabel;

public class CustomLabel extends JLabel {
	boolean active;
	public CustomLabel() {
		// TODO Auto-generated constructor stub
		active = true;
	}

	public CustomLabel(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public CustomLabel(Icon image) {
		super(image);
		
		// TODO Auto-generated constructor stub
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CustomLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		// TODO Auto-generated constructor stub
	}

	public CustomLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		// TODO Auto-generated constructor stub
	}

	public CustomLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		// TODO Auto-generated constructor stub
		active = true;
	}

}
