package ver_19_5;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import ver_19_5.Gui2; // change manually

/**
 * creates a key logger in the console. enables the wait for user input
 * @author adam l
 * @version Mar 25, 2020
 * @since ver 13_1
 */
public class KeyListenerImpl implements java.awt.event.KeyListener {
	public KeyListenerImpl(){}
	
	@Implementation
	public void closeAll(){
		System.out.println("closeAll()");
		// progIn.close();
	}
	
	public void sendInput(Process prog) throws IOException{
		System.out.println("send Input()");
		String progOut = null;
		String errOut = null;

		 //send input to the program
		BufferedWriter progIn = new BufferedWriter(new OutputStreamWriter(prog.getOutputStream()));
		progIn.append(Gui2.console.getText().split("\n")[0]);  // if put in comment, blocking
		progIn.newLine();
		progIn.flush(); // TODO: check why is it sent as a block
//		prog.getOutputStream().flush(); // sends the input 
//		progIn.close();	
		/*
		// get error from program
		while(Function.errSc.hasNext()) errOut+=Function.errSc.next();
		System.out.println(errOut);
		if(errOut != null) Gui2.console.append(errOut+"\n");
		
//		 get output from program
		while(Function.progSc.hasNext()) progOut+=Function.progSc.next(); // stoping here -----------------------------------------
		//else throw new java.lang.Exception(){};
		System.out.println(progOut);
		if(progOut != null) Gui2.console.append(progOut+"\n");*/
	}

    /** Handle the key typed event from the text field. */
	@Implementation
    public void keyTyped(java.awt.event.KeyEvent e) {
        displayInfo(e, "KEY TYPED: ");
        System.out.println("KEY TYPED: "+e);
        
        if(e.getKeyChar() == '\n'){			
			try{
				sendInput(Function.prog);
				System.out.println("try");
			}
			catch (Exception e1){
				e1.printStackTrace();
				System.out.println("try faild");
			}
        }
    }

    /** Handle the key-pressed event from the text field. */
	@Implementation
    public void keyPressed(KeyEvent e) {
        displayInfo(e, "KEY PRESSED: ");
        System.out.println("KEY PRESSED: ");
    }

    /** Handle the key-released event from the text field. */
    @Implementation
    public void keyReleased(KeyEvent e) {
        displayInfo(e, "KEY RELEASED: ");
        System.out.println("KEY RELEASED: ");        
    }
    private void displayInfo(KeyEvent e, String keyStatus){
        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
            int keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode
                    + " ("
                    + KeyEvent.getKeyText(keyCode)
                    + ")";
        }
        
        int modifiersEx = e.getModifiersEx();
        String modString = "extended modifiers = " + modifiersEx;
        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
        if (tmpString.length() > 0) {
            modString += " (" + tmpString + ")";
        } else {
            modString += " (no extended modifiers)";
        }
        
        String actionString = "action key? ";
        if (e.isActionKey()) {
            actionString += "YES";
        } else {
            actionString += "NO";
        }
        
        String locationString = "key location: ";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
            locationString += "standard";
        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
            locationString += "left";
        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
            locationString += "right";
        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
            locationString += "numpad";
        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
            locationString += "unknown";
        }
        
        //Display information about the KeyEvent...
        
    }
}
