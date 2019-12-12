/**
 * the main thread/class of my cyber project
 */
package cyber_project;
import java.lang.Thread;
/**
 * @author adam l
 * @version Oct 16, 2019
 */
public class ProjectMain
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Thread gui = new Thread(Gui2.getInstance(), "gui2");
		gui.run();
	}

}
