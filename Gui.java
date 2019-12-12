/**
 * 
 */
package cyber_project;
import java.lang.Runnable;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.peer.CanvasPeer;
/**
 * @author adam l
 * @version Oct 16, 2019
 */
public class Gui implements Runnable
{
	protected int WindowWidth;
	protected int WindowHight;
    protected static Gui singleton = null; 
    protected Canvas canvas;
    protected GraphicsEnvironment enviroment; 
    protected GraphicsDevice device;
    protected CanvasPeer canvpee;
	
	private Gui()throws HeadlessException{
		System.err.println("Gui()");
		this.enviroment = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
		try{
			this.device = this.enviroment.getDefaultScreenDevice();
		}catch(HeadlessException e){
			throw e;
		}
		this.canvas = new Canvas((GraphicsConfiguration)(((GraphicsDevice)this.device).getDefaultConfiguration()));
		System.err.println(canvas);
	}
	
	public static Gui getInstance(){
		if(Gui.singleton==null)
			return new Gui();
		return Gui.singleton;
	}
	public void run()throws HeadlessException{
		System.err.println("run()");
		try{
//			this.canvas.paint(new java.awt.Button("click").getGraphics());
		}catch(HeadlessException e){
			throw e;
		}
	}
}
