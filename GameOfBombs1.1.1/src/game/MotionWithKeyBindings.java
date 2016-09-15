package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class MotionWithKeyBindings {
	JComponent component;
	public MotionWithKeyBindings(JComponent component) {
		// TODO Auto-generated constructor stub
		this.component = component;
	}

}
class MotionAction extends AbstractAction implements ActionListener
{
    private int deltaX;
    private int deltaY;
 
    public MotionAction(String name, int deltaX, int deltaY)
    {
        super(name);
 
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
    
    public MotionAction addAction(String name, int deltaX, int deltaY,JComponent component)
    {
        MotionAction action = new MotionAction(name, deltaX, deltaY);
     
        KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(pressedKeyStroke, name);
        component.getActionMap().put(name, action);
     
        return action;
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
