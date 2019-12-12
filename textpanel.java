package a;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel {

	private JTextArea textarea;
	
	public TextPanel() {
		textarea = new JTextArea();
		setLayout(new BorderLayout());
		add(new JScrollPane(textarea), BorderLayout.NORTH);
	}
}
----------------------------------------------------------------------------------------
// stating TextPanel
package a;

import javax.swing.ImageIcon;
// imports
import javax.swing.JFrame;
import javax.swing.JLabel;

public class A {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame window = new JFrame("hhhh");
		window.setSize(600,  700);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(new TextPanel());
//		ImageIcon icon = ImageIcon.createImageIcon("images/middle.gif");
		window.add(new JLabel("Image and Text", JLabel.CENTER));
		window.setVisible(true);
		
	}

}
