package ver_19_5;
/**
 * the main thread/class of my cyber project
 */
import java.awt.Color;
import java.lang.Thread;

import javax.swing.JTextArea;
/**
 * @author adam l
 * @version Oct 16, 2019
 * @since ver_1
 */
public class ProjectMain
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Gui2.GUIcolorMode = ColorMode.BRIGHT; // defauld color mode
		Thread gui = new Thread(Gui2.getInstance(), "gui2");
		gui.run();
	}
	
	/**
	 * changes the color mode of the gui from DARK to BRIGHT and vice versa
	 */
	public static void changeColor(){
		if(Gui2.GUIcolorMode == ColorMode.BRIGHT){
			Gui2.GUIcolorMode = ColorMode.DARK;
			Gui2 instance = Gui2.getInstance();
			instance.reload();
			Thread gui = new Thread(Gui2.getInstance(), "gui2");
			gui.run();
		}
		else if(Gui2.GUIcolorMode == ColorMode.DARK){
			Gui2.GUIcolorMode = ColorMode.BRIGHT;
			Gui2 instance = Gui2.getInstance();
			instance.reload();
			Thread gui = new Thread(Gui2.getInstance(), "gui2");
			gui.run();
		}
		System.out.println(Gui2.GUIcolorMode);
	}

}
