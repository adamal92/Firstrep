package ver_19_5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import ver_19_5.KeyListenerImpl; // change manually


/**
 * implements some helpful functions that could be written in other place, to clean out the code <br />
 * all functions here must be <b>static</b>
 * @author adam l
 * @version Mar 10, 2020
 * @see <code>Gui2</code>
 * @since ver_5
 */
public class Function
{

	public static final String currDir = System.getProperty("user.dir"); // current directury of the program
	public static String COMPILER = System.getProperty("user.dir")+"\\java\\jdk-13.0.2\\bin\\javac.exe"; // the javac compilers' location
	public static String RTE = currDir+"\\java\\jdk-13.0.2\\bin\\java.exe"; // the java JVM(JRE)s' location path on local device
	public static String RUN_FILE = currDir+"\\java\\jdk-13.0.2\\bin\\adam_prog\\Test.java"; // the file to compile & execute
	
	public static java.lang.Process prog;
	public static java.util.Scanner errSc;
	public static Scanner progSc;

	/**
	 * Compiles and runs the code that was written by the user in the textArea.  <br />
	 * In addition, handles any input/output/error that the runing program has <br />
	 * @param console the <code>javax.swing.JTextArea</code> that acts as a <b>console</b> <br />
	 * compiling file, executing, and printing to the console
	 */ /*
	public static void console(javax.swing.JTextArea console, boolean needsCompiler){
		int exitV = 0;
		int rowCount = 0;
		try{
			if(needsCompiler){
				// running compiler
				if(Function.RUN_FILE == null || Function.COMPILER == null || Function.RTE == null) throw new Exception("empty path");
				java.lang.Process proc = new ProcessBuilder(COMPILER, RUN_FILE).start();
				Scanner s2 = new Scanner(proc.getInputStream()).useDelimiter("\\A");
				String cmdret2=null;
				while(s2.hasNext()) cmdret2+=s2.next();
				System.out.println(cmdret2);
				proc.waitFor();
				System.out.println(proc.exitValue());	
			}
			// running .class file (executing)
			Process prog = new ProcessBuilder(RTE, RUN_FILE).start(); // working
			// get errors from program
			java.util.Scanner progErr = new Scanner(prog.getErrorStream()).useDelimiter("\\A");
			java.lang.String progerr="";	
			do{
				try{ // check if the program exited
					exitV = prog.exitValue(); // throws exception if program hasn't exited yet
					System.out.println(exitV);	
					while(progErr.hasNext()) progerr+=progErr.next();
					if(exitV != 0){
						javax.swing.JOptionPane.showMessageDialog(null, "couldn't execute exit code: "+exitV);
						System.err.println(String.format("failed to execute exit code: %d", exitV));
						if(progerr != null) {
							console.append(progerr);
							System.err.println(progerr);
						}
					}
					break;			
				}catch(java.lang.IllegalThreadStateException e){
//					 get errors from program
					java.util.Scanner errSc = new Scanner(prog.getErrorStream()).useDelimiter("\\A");
					java.lang.String errOut="";	
					// set output scanner from program
					java.util.Scanner progSc = new Scanner(prog.getInputStream()).useDelimiter("\\A");
					java.lang.String progOut="";	
					// send input to the program
					java.io.BufferedWriter progIn = new BufferedWriter(new OutputStreamWriter(prog.getOutputStream()));
					progIn.append(console.getText());
					progIn.newLine();
					progIn.close();
					// get output from program
					while(progSc.hasNext()) progOut+=progSc.next();
					System.out.println(progOut);
					if(progOut != null) console.append(progOut);
					// get error from program
					while(errSc.hasNext()) errOut+=errSc.next();
					System.out.println(errOut);
					if(errOut != null) console.append(errOut);
				}
			}while(true);
		}catch(Exception e1){
			javax.swing.JOptionPane.showMessageDialog(null, String.format("couldn't execute: %s", e1.toString()));
			System.err.println(String.format("failed to execute %s: ", "notship.exe")+e1);
			System.err.println(exitV);
			e1.printStackTrace();
		}
	}*/
	
	public static void console(javax.swing.JTextArea console, boolean needsCompiler){ // old
		// TODO: done (fix the okkk bug & the \\ bug)
		// Uncomment this if you wish to turn off focus
        //traversal.  The focus subsystem consumes
        //focus traversal keys, such as Tab and Shift Tab.
        //If you uncomment the following line of code, this
        //disables focus traversal and the Tab events 
        //become available to the key event listener.
		console.addKeyListener(new KeyListenerImpl());
		console.setFocusTraversalKeysEnabled(true);
		int exitV = 0;
		int rowCount = 0;
		try{
			if(needsCompiler){
				// running compiler
				if(Function.RUN_FILE == null || Function.COMPILER == null || Function.RTE == null) throw new Exception("empty path");
				java.lang.Process proc = new ProcessBuilder(COMPILER, RUN_FILE).start(); // working
				Scanner s2 = new Scanner(proc.getInputStream()).useDelimiter("\\A");
				String cmdret2=null;
				while(s2.hasNext()) cmdret2+=s2.next();
				System.out.println(cmdret2);
				proc.waitFor();
				System.out.println(proc.exitValue());	
			}
			// running .class file (executing)
			Function.prog = new ProcessBuilder(RTE, RUN_FILE).start(); // working
			// get errors from program
			java.util.Scanner progErr = new Scanner(prog.getErrorStream()).useDelimiter("\\A");
			java.lang.String progerr="";	
			do{
				Thread oeListener;
				java.util.concurrent.ExecutorService executor = Executors.newFixedThreadPool(1);
				try{ // check if the program exited
//					 send input to the program
//					java.io.BufferedWriter progIn = new BufferedWriter(new OutputStreamWriter(prog.getOutputStream()));
//					String input = JOptionPane.showInputDialog("enter input");
//					progIn.append(input); //console.getText());
//					progIn.newLine();
//					progIn.close();
					exitV = prog.exitValue(); // throws exception if program hasn't exited yet
					System.out.println(exitV);	
					while(progErr.hasNext()) progerr+=progErr.next();
					progErr.close();
//					executor.shutdownNow(); // should kill the thread
					if(exitV != 0){
						javax.swing.JOptionPane.showMessageDialog(null, "couldn't execute exit code: "+exitV);
						System.err.println(String.format("failed to execute exit code: %d", exitV));
						if(progerr != null) {
							console.append(progerr);
							System.err.println(progerr);
						}
					}
					break;			
				}catch(java.lang.IllegalThreadStateException e){
//					 get errors from program
					Function.errSc = new Scanner(prog.getErrorStream()).useDelimiter("\\A");
					java.lang.String errOut="";	
					// set output scanner from program
					Function.progSc = new Scanner(prog.getInputStream()).useDelimiter("\\A");
					java.lang.String progOut="";	
//					
//					oeListener = new Thread(new OutErrListener());
//					oeListener.run();
					executor.submit(new Thread(new OutErrListener())); // runs the thread
					
					// TODO: make this process to wait for user input into the console, and proceed when enter (\n) is invoked
//					KeyListenerImpl.sendInput(console);
					console.append("go\n");
						
/*
					// send input to the program
					java.io.BufferedWriter progIn = new BufferedWriter(new OutputStreamWriter(prog.getOutputStream()));
					progIn.append(console.getText());
					progIn.newLine();
					progIn.close();
					// get output from program
					while(progSc.hasNext()) progOut+=progSc.next();
					System.out.println(progOut);
					if(progOut != null) console.append(progOut);
					// get error from program
					while(errSc.hasNext()) errOut+=errSc.next();
					System.out.println(errOut);
					if(errOut != null) console.append(errOut); */
					
				}
			}while(false);
		}catch(Exception e1){
			javax.swing.JOptionPane.showMessageDialog(null, String.format("couldn't execute: %s", e1.toString()));
			System.err.println(String.format("failed to execute %s: ", "notship.exe")+e1);
			System.err.println(exitV);
			e1.printStackTrace();
		}
	
	}


	/**
	 * Saves the text areas' content as a file at users' choise directory
	 * @param textArea the <code>javax.swing.JTextArea</code> to get the users' code from
	 */
	public static void saveFile(javax.swing.JTextArea textArea){
		JTextArea pathWindow = new JTextArea();
		pathWindow.setEditable(true);
		JScrollPane scrollPane = new JScrollPane(pathWindow);
		scrollPane.requestFocus();
		pathWindow.requestFocusInWindow();
		scrollPane.setPreferredSize(new Dimension(800, 600));
		JOptionPane.showMessageDialog(null, scrollPane, "Paste path Info", JOptionPane.PLAIN_MESSAGE);
		String path = pathWindow.getText(); // also works with a single \
		File file = new File(path);
		try{
			java.io.PrintWriter writer = new PrintWriter(file);
//			writer.print("");
			writer.close(); // erases files' content
			Writer my_file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			my_file.append(textArea.getText());
			my_file.close();
			javax.swing.JOptionPane.showMessageDialog(null, "File saved");
			Function.RUN_FILE = path;
			textArea.append("okkk\n"); 
		}catch(Exception e1){javax.swing.JOptionPane.showMessageDialog(null, String.format("failed to save file: ")+e1);e1.printStackTrace();};
	}

	/**
	 * creates the articles window with the links
	 */
	public static void articles(){
		javax.swing.JFrame articles = new JFrame("articles");	
		java.awt.Dimension size = new Dimension(800, 600);
		try{
			final java.awt.Desktop desk = Desktop.getDesktop();
			try {
				BufferedImage projIcon = ImageIO.read(new File(Gui2.ICON_IMAGE_PATH)); // not supporting .ico
				articles.setIconImage(projIcon);
			} catch (Exception e) {
				System.err.println("failed to load icon image "+e);
				JOptionPane.showMessageDialog(null, "error: failed to load icon image: \n"+e);
			}	

			JPanel panel = new JPanel();

			final JButton ai = new JButton("Artificial Intelligence"); 
			ai.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						desk.browse(new java.net.URI("https://he.wikipedia.org/wiki/%D7%91%D7%99%D7%A0%D7%94_%D7%9E%D7%9C%D7%90%D7%9B%D7%95%D7%AA%D7%99%D7%AA"));
					}catch(Exception e1){
						e1.printStackTrace();}}});

			final JButton cs = new JButton("Computer Science"); 
			cs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						desk.browse(new URI("https://en.wikipedia.org/wiki/Computer_science#Computer_graphics_and_visualization"));
					}catch(Exception e1){
						e1.printStackTrace();}}});
			
			final JButton siw = new JButton("Secure IDE Website"); 
			siw.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						desk.browse(new URI("https://adam.harelwebs.com/")); 
					}catch(Exception e1){
						e1.printStackTrace();}}});
			
			final JButton aat = new JButton("Android App Tutorial"); 
			aat.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						desk.browse(new URI("https://www.youtube.com/watch?v=988UZFB0heA"));
					}catch(Exception e1){
						e1.printStackTrace();}}});
			panel.add(ai);
			panel.add(cs);
			panel.add(siw);
			panel.add(aat);
			articles.getContentPane().add(panel);

			articles.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // sets 'X' to close the current window
			articles.pack(); // automatically changes the size of the frame according to the size of components in it
			articles.setLocation(new java.awt.Point(900, 500)); // from top - left 
			articles.setVisible(true);
		}catch(Exception e){
			javax.swing.JOptionPane.showMessageDialog(null, "error: can't find url: \n"+e);
			System.err.println("can't find url: \n");
			e.printStackTrace();
		}	
	}
	
	/**
	 * creates the Legal Information window with the links
	 */
	public static void legalInfo(){
		javax.swing.JFrame legal = new JFrame("Legal Information");	
		java.awt.Dimension size = new Dimension(800, 600);
		try{
			final java.awt.Desktop desk = Desktop.getDesktop();
			try {
				BufferedImage projIcon = ImageIO.read(new File(Gui2.ICON_IMAGE_PATH)); // not supporting .ico
				legal.setIconImage(projIcon);
			} catch (Exception e) {
				System.err.println("failed to load icon image "+e);
				JOptionPane.showMessageDialog(null, "error: failed to load icon image: \n"+e);
			}	

			JPanel panel = new JPanel();

			final JButton hckr = new JButton("Hacker: Criminal or not?"); 
			hckr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						desk.browse(new java.net.URI("https://www.bridewellconsulting.com/when-is-hacking-illegal-and-legal"));
					}catch(Exception e1){
						e1.printStackTrace();}}});

			final JButton hl = new JButton("Hacking Laws"); 
			hl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						desk.browse(new URI("https://criminal.findlaw.com/criminal-charges/hacking-laws-and-punishments.html"));
					}catch(Exception e1){
						e1.printStackTrace();}}});
			
			final JButton lit_usa = new JButton("Laws in the USA"); 
			lit_usa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						desk.browse(new URI("https://www.ncsl.org/research/telecommunications-and-information-technology/computer-hacking-and-unauthorized-access-laws.aspx"));
					}catch(Exception e1){
						e1.printStackTrace();}}});
			panel.add(hckr);
			panel.add(hl);
			panel.add(lit_usa);
			legal.getContentPane().add(panel);

			legal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // sets 'X' to close the current window
			legal.pack(); // automatically changes the size of the frame according to the size of components in it
			legal.setLocation(new java.awt.Point(900, 500)); // from top - left 
			legal.setVisible(true);
		}catch(Exception e){
			javax.swing.JOptionPane.showMessageDialog(null, "error: can't find url: \n"+e);
			System.err.println("can't find url: \n");
			e.printStackTrace();
		}	
	}

	/**
	 * Saves the users' code in a file at entered directory
	 */
	public static void getpath(){
		javax.swing.JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			Function.RUN_FILE = file.getPath();
			javax.swing.JOptionPane.showMessageDialog(null, "JFILE set to location: "+file.getPath());
		} else {
			javax.swing.JOptionPane.showMessageDialog(null, "error: File Not Found");
			System.err.println("error: File Not Found");
		}
	}
	
	/**
	 * A function to get a path from the user as a String, with a GUI
	 * @return The chosen files' location (path)
	 * @throws FileNotFoundException
	 */
	public static String returnpath() throws FileNotFoundException{
		javax.swing.JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			javax.swing.JOptionPane.showMessageDialog(null, "JFILE set to location: "+file.getPath());
			return file.getPath();
		} else {
			javax.swing.JOptionPane.showMessageDialog(null, "error: File Not Found");
			System.err.println("error: File Not Found");
			throw new java.io.FileNotFoundException();
		}
	}
	
	/**
	 * Saves the text areas' content as a file at users' choise directory
	 * this is a copy (later version) of saveFile() that uses <code>returnpath()</code>
	 * @param textArea the <code>javax.swing.JTextArea</code> in which the user writes code
	 */
	public static void GUIsaveFile(javax.swing.JTextArea textArea){
		JOptionPane.showMessageDialog(null, "Select path Info");
		try{
			String path = Function.returnpath(); 
			File file = new File(path);
			if(file.isFile()){
				java.io.PrintWriter writer = new PrintWriter(file);
				writer.close(); // erases files' content
				Writer my_file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
				my_file.append(textArea.getText());
				my_file.close();
				javax.swing.JOptionPane.showMessageDialog(null, "File saved");
				Function.RUN_FILE = path;
			}else{throw new FileNotFoundException();}
		}catch(Exception e1){javax.swing.JOptionPane.showMessageDialog(null, String.format("failed to save file: ")+e1);e1.printStackTrace();};
	}
	
	/**
	 * Changes the size of either the console or the code area' according to user input
	 * @since ver_18
	 */
	public static void changeSize(){
		String txtarea = JOptionPane.showInputDialog(Gui2.getPubwindow(), "which one?", "console code");
		JScrollPane SP;
		if(txtarea.equals("console"))  SP = Gui2.getConsoleScroll();
		else if(txtarea.equals("code")) SP = Gui2.getCodeScroll();
		else{
			JOptionPane.showMessageDialog(Gui2.getPubwindow(), "please enter either console or code");
			changeSize();
			return;
		}
		int hight = Integer.parseInt(JOptionPane.showInputDialog("enter hight"));
		int width = Integer.parseInt(JOptionPane.showInputDialog("enter width"));
		Dimension newSize = new Dimension(width, hight);
		SP.getViewport().setSize(newSize);
		SP.setSize(newSize);
		SP.getViewport().setPreferredSize(newSize);
		SP.getViewport().setMinimumSize(newSize);
		SP.getParent().validate();
		Gui2.pubwindow.repaint(); // prevents the repited pattern effect
	}

	public static String getCOMPILER()
	{
		return COMPILER;
	}

	public static void setCOMPILER(String compiler)
	{
		COMPILER = compiler;
	}

	public static String getRUN_FILE()
	{
		return RUN_FILE;
	}

	public static void setRUN_FILE(String jfile)
	{
		RUN_FILE = jfile;
	}

	public static String getRTE()
	{
		return RTE;
	}

	public static void setRTE(String rte)
	{
		RTE = rte;
	}
}
