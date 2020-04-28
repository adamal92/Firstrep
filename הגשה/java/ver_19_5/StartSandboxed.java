/**
 * 
 */
package ver_19_5;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The main executable of the program<br />
 * starts the <code>Gui2</code> of the project in the sandbox <br />
 * needs to be under <b>Sandboxie</b> directory
 * @author adam l
 * @version Mar 14, 2020
 * @since ver 8
 */
public class StartSandboxed
{
	public static String currDir = System.getProperty("user.dir"); // current directury of the program
	public static String GUIEXE = currDir+"\\SecureIDE.exe"; // directory of executable
	public static int windowWidth = 50;
	public static int windowhight = 50;
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println(GUIEXE);
		Process pro;
		try{
			pro = Runtime.getRuntime().exec("Sandboxie\\Start.exe "+GUIEXE);
			System.out.println("exit value: 0");
		}catch(Exception e){
			javax.swing.JOptionPane.showMessageDialog(null, "couldn't execute");
			javax.swing.JOptionPane.showMessageDialog(null, "error: \n"+e);
			System.err.println("couldn't execute: \n");
			e.printStackTrace();
		}
	}
}
