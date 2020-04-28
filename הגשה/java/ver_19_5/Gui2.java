package ver_19_5;

/**
 * the GUI for my cyber project
 */
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

/**
 * this class builds the projects' GUI
 * @author adam l
 * @version Mar 14, 2020
 * @since ver_1
 */
public class Gui2 implements Runnable
{
	protected static Gui2 singleton = null; 
	protected int windowWidth = 1500;
	protected int windowhight = 1000;
	public static JFrame pubwindow; // programs' main window
	public static JTextArea console; // io handling
	public static JTextArea textArea; // where code is written
	protected static final String projectName = "Secure IDE";
	public static final String currDir = System.getProperty("user.dir"); // current directury of the program
	protected static String JCOMPILER = currDir+"\\java\\jdk-13.0.2\\bin\\javac.exe"; // the javac compilers' location
	protected static String JRE = currDir+"\\java\\jdk-13.0.2\\bin\\java.exe"; // the java JVM(JRE)s' location path on local device
	protected static String PY_RTE = currDir+"\\python\\python.exe"; //"C:\\Users\\adam l\\Desktop\\python.exe"; the python interpreters' location
	protected static JScrollPane consoleScroll; // the JScrollPane that surraunds the code area
	protected static JScrollPane codeScroll; // the JScrollPane that surraunds the console
	public static String ICON_IMAGE_PATH = currDir+"\\fotos\\SecureIDE.jpg"; // directury of programs' icon
	protected static Color menuColor; // color of the menu
	protected static Color menuTextColor; // color of the text at menu
	protected static Color consoleBG_Color; // color of the console background
	protected static Color consoleTxt_Color; // color of the console text
	protected static Color consolePanel_Color; // color of the console Caret
	protected static Color consoleCaret_Color; // color of the text area's panel
	protected static Color textBG_Color; // color of the text area background
	protected static Color textTxt_Color; // color of the text area's code
	protected static Color textPanel_Color; // color of the text area's panel
	protected static Color textCaret_Color; // color of the text area's Caret
	public static ColorMode GUIcolorMode;

	private Gui2(){
		System.out.println("Gui2()");
	}

	/**
	 * returnes the only instance of the class <br />
	 * if there is no instance, a new one is created
	 * @return <code>Gui2</code>
	 */
	public static Gui2 getInstance(){
		if(Gui2.singleton==null)
			Gui2.singleton = new Gui2();
		return Gui2.singleton;
	}
	
	/**
	 * erases the old gui and returns a new one
	 * @return <code>Gui2</code>
	 */
	public Gui2 reload(){
		Gui2.singleton = new Gui2();
		return Gui2.singleton;
	}
	
	/**
	 * erases the current instance of the gui
	 */
	public void erase(){ Gui2.singleton = null;	}

	/**
	 * creates the <code>JFrame</code> main window of the project
	 *
	 */
	public void createWindow(){
		System.out.println("window created");
		JFrame window = new JFrame(this.projectName);
		// set colors
		if(this.GUIcolorMode == ColorMode.DARK){
			Gui2.menuTextColor = Color.WHITE; 
			this.menuColor = Color.DARK_GRAY;
			this.consoleBG_Color = Color.BLACK;
			this.consoleTxt_Color = new Color(100,100,255); //Red?
			this.textBG_Color = new Color(0, 50, 0, 255); // RGBA 0-255
			this.textTxt_Color = Color.YELLOW;
			this.consolePanel_Color = this.menuColor;
			this.textPanel_Color = this.consolePanel_Color;
			this.consoleCaret_Color = Color.WHITE;
			this.textCaret_Color = Color.WHITE;
		}
		else if(this.GUIcolorMode == ColorMode.BRIGHT){ 
			this.menuTextColor = Color.BLACK; 
			this.menuColor = null;
			this.consoleBG_Color = Color.WHITE;
			this.consoleTxt_Color = Color.RED;
			this.textBG_Color = Color.WHITE;
			this.textTxt_Color = Color.BLACK;
			this.consolePanel_Color = new Color(0, 0, 255, 10); // RGBA 0-255
			this.textPanel_Color = this.consolePanel_Color;
			this.consoleCaret_Color = Color.BLACK;
			this.textCaret_Color = Color.BLACK;
		}
		try {
			BufferedImage projIcon = ImageIO.read(new File(ICON_IMAGE_PATH)); // not supporting .png and .ico
			window.setIconImage(projIcon);
		} catch (Exception e) {
			System.err.println("failed to load icon image "+e);
		}
		Gui2.pubwindow = window;
		// JFrame.DISPOSE_ON_CLOSE sets 'X' to close the current window , JFrame.EXIT_ON_CLOSE sets 'X' to close the app
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		window.setSize(windowWidth, windowhight); // should be changeable & default full screen
//		window.pack(); // automatically changes the size of the frame according to the size of components in it
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); // sets window at full screen
		window.setLocation(0,0); // from top - left 

		// text area 
		JPanel p1 = new JPanel();
		final JExpandableTextArea textArea = new JExpandableTextArea(22, 100, JTA_type.CODE);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.requestFocus(); //InWindow
		this.codeScroll = scroll;
		textArea.setBackground(this.textBG_Color);
		textArea.setForeground(this.textTxt_Color);
		textArea.setFont(new Font("font", 5, 13));
		textArea.setEditable(true);
		textArea.setMargin(new java.awt.Insets(30, 30, 30, 30));
		textArea.setMinimumSize(new Dimension(22, 145));
		textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
		textArea.setCaretColor(this.textCaret_Color);
		this.textArea = textArea;
		p1.add(scroll, BorderLayout.NORTH);
		p1.setBackground(this.textPanel_Color);
		p1.setEnabled(true);
		p1.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
		window.getContentPane().add(BorderLayout.CENTER, p1);
		
		Dimension newSize1 = new Dimension(window.getWidth(), window.getHeight()/2);
		textArea.getVisibleRect().setSize(newSize1);
		scroll.getViewport().setSize(newSize1);
		scroll.getViewport().setPreferredSize(newSize1);
		
		
		// console
//		JPanel p = new JPanel();
//		window.getContentPane().add(BorderLayout.SOUTH, p);
		final JExpandableTextArea console = new JExpandableTextArea(22/3, 100, JTA_type.CONSOLE);
		JScrollPane consoleScroll = new JScrollPane(console);
		this.consoleScroll = consoleScroll;
		console.setForeground(this.consoleTxt_Color);
		console.setBackground(this.consoleBG_Color);
		console.setFont(new java.awt.Font("font", 30, 15));
		consoleScroll.requestFocus(); //InWindow
		console.setDragEnabled(true);
		console.setBorder(new EmptyBorder(10, 10, 10, 10));
//		console.setMargin(new Insets(500, 500, 500, 500));
//		console.setMinimumSize(new Dimension(22, 145));
		console.setCaretColor(this.consoleCaret_Color);
//		console.setSize(window.getWidth(), window.getHeight()/2);
		this.console = console;
		p1.add(consoleScroll, BorderLayout.SOUTH);
		p1.setBackground(this.consolePanel_Color);
		window.getContentPane().add(BorderLayout.CENTER, p1);

		Dimension newSize = new Dimension(window.getWidth(), (int)(window.getHeight()/2.5));
		console.getVisibleRect().setSize(newSize);
		consoleScroll.getViewport().setSize(newSize);
		consoleScroll.getViewport().setPreferredSize(newSize);
		
		// menu
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
		// File
		JMenu file = new JMenu("File");
		file.setForeground(this.menuTextColor);
		JMenuItem saveas = new JMenuItem("Save As...");
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new MenuAction(){
			public void actionPerformed(ActionEvent e){textArea.append("Exit\n");java.lang.System.exit(0);}});
		// Save As
		saveas.addActionListener(new MenuAction(){
			public void actionPerformed(ActionEvent e){Function.saveFile(textArea);}});
		JMenuItem saveasfile = new JMenuItem("Save As File...");
//		 Save As File
		saveasfile.addActionListener(new MenuAction(){
			public void actionPerformed(ActionEvent e){Function.GUIsaveFile(textArea);}});
		file.add(saveas);
		file.add(saveasfile);
		file.addSeparator();
		file.add(exitItem);
		// Window
		JMenu windowMenu = new JMenu("Window");
		windowMenu.setForeground(this.menuTextColor);
		JMenu showMenu = new JMenu("Show");
		JMenuItem changeC = new JMenuItem("Change color");
		changeC.addActionListener(new MenuAction(){
			@Override public void actionPerformed(ActionEvent e){ProjectMain.changeColor();}}); 
		showMenu.add(changeC);
		JMenuItem showArticlesItem = new JMenuItem("Articles");
		showArticlesItem.addActionListener(new MenuAction(){
			@Override public void actionPerformed(ActionEvent e){Function.articles();}});
		showMenu.add(showArticlesItem);
		JMenuItem legalInfo = new JMenuItem("Legal Information");
		legalInfo.addActionListener(new MenuAction(){
			@Override
			public void actionPerformed(ActionEvent e){Function.legalInfo();}});
		showMenu.add(legalInfo);
		windowMenu.add(showMenu);
		// Edit
		JMenu edit = new JMenu("Edit");
		edit.setForeground(this.menuTextColor);
		JMenuItem changepath = new JMenuItem("Change Path");
		changepath.addActionListener(new MenuAction(){
			@Override
			public void actionPerformed(ActionEvent e){Function.getpath();}});
		edit.add(changepath);
		JMenuItem changeSize = new JMenuItem("Change Size");
		changeSize.addActionListener(new MenuAction(){
			@Override
			public void actionPerformed(ActionEvent e){Function.changeSize();}});
		edit.add(changeSize);
		// Tools
		JMenu tools = new JMenu("Tools");
		tools.setForeground(this.menuTextColor);
		JMenuItem runcmd = new JMenuItem("Run Command"); 
		runcmd.addActionListener(new Action(){
			private boolean b=true;
			public Object getValue(String key){return null;}
			public boolean isEnabled(){return this.b;}
			public void putValue(String key, Object value){}
			public void removePropertyChangeListener(PropertyChangeListener listener){}
			public void setEnabled(boolean b){this.b = b;}
			public void actionPerformed(ActionEvent e){
				JTextArea textAreaCmd = new JTextArea();
				textAreaCmd.setEditable(true);
				JScrollPane scrollPane = new JScrollPane(textAreaCmd);
				scrollPane.requestFocus();
				textAreaCmd.requestFocusInWindow();
				scrollPane.setPreferredSize(new java.awt.Dimension(800, 600));
				JOptionPane.showMessageDialog(null, scrollPane, "Enter Command", JOptionPane.PLAIN_MESSAGE);
				String cmmnd = textAreaCmd.getText();

				try{
					java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmmnd).getInputStream()).useDelimiter("\\A");
					String cmdret="";
					while(s.hasNext()) cmdret+=s.next();
					javax.swing.JOptionPane.showMessageDialog(null, String.format("executing command: %s", cmmnd));
					JFrame f = new JFrame();
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets 'X' to close the app
					f.setExtendedState(JFrame.MAXIMIZED_BOTH); // sets window at full screen
					f.setLocation(0,0); 
					f.setMaximumSize(new Dimension(10, 10));
					ErrorMessagePanel cmdreturn = new ErrorMessagePanel(cmdret);
					cmdreturn.setMaximumSize(new Dimension(10, 10));
					JOptionPane.showMessageDialog(f, cmdreturn);
				}catch(Exception e1){javax.swing.JOptionPane.showMessageDialog(null, "couldn't execute");}
			}
			@Implementation
			public void addPropertyChangeListener(PropertyChangeListener listener){
			}});
		tools.add(runcmd);
		// Run
		JMenu Run = new JMenu("Run");
		Run.setForeground(this.menuTextColor);
		JMenu java = new JMenu("Java");
//		java.setForeground(this.menuTextColor);
		Color javaCol = Color.CYAN;
		java.setBackground(javaCol);
		JMenuItem RunJavaItem = new JMenuItem("Run Java"); 
		RunJavaItem.addActionListener(new MenuAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				Function.setCOMPILER(Gui2.JCOMPILER);
				Function.setRTE(Gui2.JRE);
				Function.console(console, true);}});
		RunJavaItem.setBackground(Color.CYAN);
		JMenuItem changeJavaComp = new JMenuItem("Change Java Compiler");
		changeJavaComp.addActionListener(new MenuAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{Gui2.JCOMPILER = Function.returnpath();}
				catch(Exception e1){System.err.println("failed to Change Java Compiler");}
			}});
		changeJavaComp.setBackground(Color.CYAN);
		JMenuItem changeJRE = new JMenuItem("Change Java RTE");
		changeJRE.addActionListener(new MenuAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{Gui2.JRE = Function.returnpath();}
				catch(Exception e1){System.err.println("failed to Change Java Compiler");}
			}});
		changeJRE.setBackground(javaCol);
		JMenu python = new JMenu("Python");
//		python.setForeground(this.menuTextColor);
		Color pyCol = Color.ORANGE;
		python.setBackground(pyCol);
		JMenuItem RunPython = new JMenuItem("Run Python"); 
		RunPython.addActionListener(new MenuAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				Function.setRTE(Gui2.PY_RTE);
				Function.console(console, false);}});
		RunPython.setBackground(pyCol);
		JMenuItem changePython = new JMenuItem("Change Python RTE");
		changePython.addActionListener(new MenuAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				try{Gui2.PY_RTE = Function.returnpath();}
				catch(Exception e1){System.err.println("failed to Change Java Compiler");}
				}});
		changePython.setBackground(pyCol);
		java.add(RunJavaItem);
		java.add(changeJavaComp);
		java.add(changeJRE);
		python.add(RunPython);
		python.add(changePython);
		Run.add(java);
		Run.addSeparator();
		Run.add(python);
		// run Java
		JMenu runJava = new JMenu();
////		run.setSize(Run.getSize());
//		BufferedImage runIconBuff = null;
//		try {
//			runIconBuff = ImageIO.read(new File(ICON_IMAGE_PATH)); // not supporting .png and .ico
////			run.setIcon((Icon) runIconBuff);
//		} catch (Exception e) {
//			System.err.println("failed to load icon image "+e);
//		}
//		Image dimg = runIconBuff.getScaledInstance(tools.getWidth(), tools.getHeight(), Image.SCALE_SMOOTH);
//		ImageIcon runIcon = new ImageIcon(dimg, "run icon");
//		run.setIcon(runIcon);
		// 3667*2000
		runJava.setIcon(new ImageIcon(new ImageIcon(this.currDir+"\\fotos\\java.png").getImage().getScaledInstance(20, 36, Image.SCALE_DEFAULT)));
		runJava.addMouseListener((MouseListener) new MouseListenerImpl(){
			@Override
			public void mouseClicked(MouseEvent e){
				Function.console(console, true);}});
//		JMenuItem run1 = new JMenuItem();
//		run1.addActionListener();
//		run.addActionListener(new ActionListener() { 
//			@Implementation
//            public void actionPerformed(ActionEvent e) {
//                if(e.getSource().){
//                	System.out.print("hello menu");
//                }}});
//		run.add(new MenuAction(){
//			@Override
//			public void actionPerformed(ActionEvent e){
//				Function.console(console, true);}});
		// run Python
		JMenu runPython = new JMenu();
		// not supporting .ico
		runPython.setIcon(new ImageIcon(new ImageIcon(this.currDir+"\\fotos\\python.png").getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT)));
		runPython.addMouseListener((MouseListener) new MouseListenerImpl(){
			@Override
			public void mouseClicked(MouseEvent e){
				Function.console(console, false);}});
		// menu
		menu.setBackground(this.menuColor);
		menu.add(file);
		menu.add(windowMenu);
		menu.add(edit);
		menu.add(tools);
		menu.add(Run);
		menu.add(runJava);
		menu.add(runPython);

		window.getContentPane().add(BorderLayout.PAGE_START, menu);

		window.setVisible(true);
	}

	@Implementation
	public void run()
	{
		System.out.println("run() Gui2");
		createWindow();
	}

	public static JTextArea getConsole()
	{
		return console;
	}

	public static void setConsole(JTextArea console)
	{
		Gui2.console = console;
	}

	public static Color getConsoleBG_Color()
	{
		return consoleBG_Color;
	}

	public static void setConsoleBG_Color(Color consoleBG_Color)
	{
		Gui2.consoleBG_Color = consoleBG_Color;
	}

	public static Color getConsoleCaret_Color()
	{
		return consoleCaret_Color;
	}

	public static void setConsoleCaret_Color(Color consoleCaret_Color)
	{
		Gui2.consoleCaret_Color = consoleCaret_Color;
	}

	public static Color getConsolePanel_Color()
	{
		return consolePanel_Color;
	}

	public static void setConsolePanel_Color(Color consolePanel_Color)
	{
		Gui2.consolePanel_Color = consolePanel_Color;
	}

	public static Color getConsoleTxt_Color()
	{
		return consoleTxt_Color;
	}

	public static void setConsoleTxt_Color(Color consoleTxt_Color)
	{
		Gui2.consoleTxt_Color = consoleTxt_Color;
	}

	public static String getCurrDir()
	{
		return currDir;
	}

	public static ColorMode getGUIcolorMode()
	{
		return GUIcolorMode;
	}

	public static void setGUIcolorMode(ColorMode icolorMode)
	{
		GUIcolorMode = icolorMode;
	}

	public static String getICON_IMAGE_PATH()
	{
		return ICON_IMAGE_PATH;
	}

	public static void setICON_IMAGE_PATH(String icon_image_path)
	{
		ICON_IMAGE_PATH = icon_image_path;
	}

	public static Color getMenuColor()
	{
		return menuColor;
	}

	public static void setMenuColor(Color menuColor)
	{
		Gui2.menuColor = menuColor;
	}

	public static Color getMenuTextColor()
	{
		return menuTextColor;
	}

	public static void setMenuTextColor(Color menuTextColor)
	{
		Gui2.menuTextColor = menuTextColor;
	}

	public static JFrame getPubwindow()
	{
		return pubwindow;
	}

	public static void setPubwindow(JFrame pubwindow)
	{
		Gui2.pubwindow = pubwindow;
	}

	public static Gui2 getSingleton()
	{
		return singleton;
	}

	public static void setSingleton(Gui2 singleton)
	{
		Gui2.singleton = singleton;
	}

	public static JTextArea getTextArea()
	{
		return textArea;
	}

	public static void setTextArea(JTextArea textArea)
	{
		Gui2.textArea = textArea;
	}

	public static Color getTextBG_Color()
	{
		return textBG_Color;
	}

	public static void setTextBG_Color(Color textBG_Color)
	{
		Gui2.textBG_Color = textBG_Color;
	}

	public static Color getTextCaret_Color()
	{
		return textCaret_Color;
	}

	public static void setTextCaret_Color(Color textCaret_Color)
	{
		Gui2.textCaret_Color = textCaret_Color;
	}

	public static Color getTextPanel_Color()
	{
		return textPanel_Color;
	}

	public static void setTextPanel_Color(Color textPanel_Color)
	{
		Gui2.textPanel_Color = textPanel_Color;
	}

	public static Color getTextTxt_Color()
	{
		return textTxt_Color;
	}

	public static void setTextTxt_Color(Color textTxt_Color)
	{
		Gui2.textTxt_Color = textTxt_Color;
	}

	public String getProjectName()
	{
		return this.projectName;
	}

	public int getWindowhight()
	{
		return this.windowhight;
	}

	public void setWindowhight(int windowhight)
	{
		this.windowhight = windowhight;
	}

	public int getWindowWidth()
	{
		return this.windowWidth;
	}

	public void setWindowWidth(int windowWidth)
	{
		this.windowWidth = windowWidth;
	}

	public static JScrollPane getCodeScroll()
	{
		return Gui2.codeScroll;
	}

	public void setCodeScroll(JScrollPane codeScroll)
	{
		this.codeScroll = codeScroll;
	}

	public static JScrollPane getConsoleScroll()
	{
		return Gui2.consoleScroll;
	}

	public void setConsoleScroll(JScrollPane consoleScroll)
	{
		Gui2.consoleScroll = consoleScroll;
	}

}

