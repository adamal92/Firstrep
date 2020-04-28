package ver_19_5;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * an implementation of the <code>javax.swing.Action</code> interface <br />
 * helps to clean out the code at <code>Gui2</code>
 * @author adam l
 * @version Mar 10, 2020
 * @see <code>Gui2</code>
 * @since ver_3
 */
public class MenuAction implements Action
{
	private boolean b=true;
	@Implementation
	public MenuAction(){}
	public Object getValue(String key){return null;}
	public boolean isEnabled(){return this.b;}
	public void putValue(String key, Object value){}
	public void removePropertyChangeListener(PropertyChangeListener listener){}
	public void setEnabled(boolean b){this.b = b;}
	public void actionPerformed(ActionEvent e){}
	public void addPropertyChangeListener(PropertyChangeListener listener){}
}
	