package ver_19_5;

/**
 * Listens to the output and error streams from the program
 * @author adam l
 * @version Apr 7, 2020
 * @since ver_19_3
 */
public class OutErrListener implements Runnable
{
	public OutErrListener(){}
	/**
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Implementation // Runnable
	public void run() // TODO: close the listeners' execution TODO: output at every key press
	{
		while(true){
			String errOut = null, progOut = null;
			
			// get output from program

//			Function.progSc.next(); // the first one is null
			while(Function.progSc.hasNext()){
				progOut+=Function.progSc.next(); 
			}
			//else throw new java.lang.Exception(){};
			if(progOut != null) {
				System.out.println(progOut);
				Gui2.console.append(progOut+"\n");
			}
			
//			 get error from program
			while(Function.errSc.hasNext()) errOut+=Function.errSc.next();
			if(errOut != null) {
				System.out.println(errOut);
				Gui2.console.append(errOut+"\n");
			}
			
			try
			{
				this.wait(100); // in milliseconds
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
