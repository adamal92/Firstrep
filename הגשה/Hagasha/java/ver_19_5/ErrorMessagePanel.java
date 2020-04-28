package ver_19_5;

import java.awt.*;
import javax.swing.*;

/** Jenerates an error message <br />
 * This is non-production code, be careful. 
 * @author adam l
 * @version Mar 10, 2020
 * @since ver_1
 */
public class ErrorMessagePanel extends JPanel
{
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane messageScrollPane = new JScrollPane();
  JTextArea messageTextArea = new JTextArea();
  String message; // the displayed text

  /**
   * Creates an error message
 * @param message
 */
public ErrorMessagePanel(String message)
  {
    this.message = message;
    try
    {
      jbInit();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private ErrorMessagePanel()
  {
  }

  
  /**
 * @throws Exception
 */
void jbInit() throws Exception
  {
    this.setLayout(borderLayout1);
    messageScrollPane.add(messageScrollPane.createHorizontalScrollBar());
    messageTextArea.setEnabled(true);
    messageTextArea.setEditable(false);
    messageTextArea.setLineWrap(true);
    messageTextArea.setText(message);
    this.add(messageScrollPane, BorderLayout.CENTER);
    messageScrollPane.getViewport().add(messageTextArea, null);
  }

  /**
   * Changes the text that will be displayed
 * @param text
 */
public void setText(String text)
  {
    this.message = text;
  }
}
