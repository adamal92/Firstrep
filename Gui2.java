/**
 * the GUI for my cyber project
 */
package cyber_project;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import training.Server;
/**
 * @author adam l
 * @version Oct 16, 2019
 */
public class Gui2 implements Runnable
{
	protected static Gui2 singleton = null; 
	protected int windowWidth = 3000;
	protected int windowhight = 1000;
	private Gui2(){
		System.err.println("Gui2()");
	}

	public static Gui2 getInstance(){
		if(Gui2.singleton==null)
			Gui2.singleton = new Gui2();
		return Gui2.singleton;
	}

	public void createWindow(){
		System.err.println("window created");
		JFrame window = new JFrame("project name");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets 'X' to close the app
		window.setSize(windowWidth, windowhight); // should be changeable & default full screen
		window.setLocation(0,0); // from top - left 
		final JTextArea textArea = new JTextArea(10, 40);
		window.getContentPane().add(BorderLayout.LINE_START, textArea);
		final JButton button = new JButton("Click Me");
		window.getContentPane().add(BorderLayout.PAGE_END, button);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.append("Button was clicked\n");}});

//		button.addActionListener((ActionEvent e) -> { textArea.append("Button was clicked\n"); });

		final JButton stser = new JButton("Click to start server"); // to start the server
		window.getContentPane().add(BorderLayout.LINE_END, stser);
		stser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.append("starting server\n");
				Thread server = new Thread(Server.getInstance(), "server"); server.run();}});

		javax.swing.JMenuBar menu = new javax.swing.JMenuBar();
		JMenuItem item = new JMenuItem(new Action(){
			private boolean b = true;
			public Object getValue(String key){return null;}
			public boolean isEnabled(){return this.b;}
			public void putValue(String key, Object value){}
			public void removePropertyChangeListener(PropertyChangeListener listener){}
			public void setEnabled(boolean b){this.b = b;}
			public void actionPerformed(ActionEvent e){textArea.append(this.NAME);}
			public void addPropertyChangeListener(PropertyChangeListener listener){	}
		});
		item.getAccessibleContext().setAccessibleName("menu");
		item.setText("menu");
		menu.add(new JMenu("menu").add(item));
		
		menu.add(new JMenu("menu2").add(new JMenuItem("menu2")));
		/*new Action(){
			private boolean b=true;
			public Object getValue(String key){return null;}
			public boolean isEnabled(){return this.b;}
			public void putValue(String key, Object value){}
			public void removePropertyChangeListener(PropertyChangeListener listener){}
			public void setEnabled(boolean b){this.b = b;}
			public void actionPerformed(ActionEvent e)
			{textArea.append("okkk\n");
			}
			public void addPropertyChangeListener(PropertyChangeListener listener){
			}}));*/
		window.getContentPane().add(BorderLayout.PAGE_START, menu);

		Canvas c=new graph();
		c.setSize(800, 800);
		c.setBackground(Color.BLACK);
		window.getContentPane().add(BorderLayout.CENTER, c);
		
		JToolBar tolbr = new JToolBar("menu3");
//		window.getContentPane().add(BorderLayout.PAGE_END, tolbr);
		
		window.pack();
		window.setVisible(true);
	}

	public void run()
	{
		System.err.println("run() Gui2");
		createWindow();
	}

}

