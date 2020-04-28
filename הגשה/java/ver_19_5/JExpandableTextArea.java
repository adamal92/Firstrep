package ver_19_5;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

/**
 * Expandable Text Area. {@link javax.swing.JTextArea} <br />
 * A rewritten source code from: <a href="http://blue-walrus.com/2011/02/expandable-text-area-in-swing/">source</a> 
 * @author owatkins
 * @author adam l
 * @since ver_15
 * @version 02.04.2020
 */
public class JExpandableTextArea extends JTextArea implements MouseMotionListener, MouseListener, KeyListener{

	private boolean mousePressedInTriangle = false;
	private Point mousePressedPoint = null;
	private Dimension sizeAtPress = null;
	private Dimension newSize = null;
	public JTA_type type;

	//reference to the underlying scrollpane
	private JScrollPane scrollPane = null;

	//height and width.. not hypotenuse
	private int triangleSize = 20;

	//is the mouse in the triangle
	private boolean inTheTriangleZone = false;
	
	public JExpandableTextArea(){
		super();
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		}

	public JExpandableTextArea(int i, int j) {
		super(i,j);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	public JExpandableTextArea(JTA_type type){
		super();
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.type = type;
	}

	public JExpandableTextArea(int i, int j, JTA_type type) {
		super(i,j);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.type = type;
	}

	/**
	 * paint the text area
	 */
	protected void paintComponent(Graphics arg0) {

		super.paintComponent(arg0);

		Graphics2D graphics = (Graphics2D)arg0;

		if (inTheTriangleZone){
			graphics.setColor(new Color(0.5f,0.5f,0.5f,0.75f));
		}else{
			graphics.setColor(new Color(0.5f,0.5f,0.5f,0.2f));
		}
		graphics.fillPolygon(getTriangle());

	}

	private JScrollPane getScrollPane() {

		//get scrollpane, if first time calling this method then add an addjustment listener
		//to the scroll pane

		if (this.getParent() instanceof JViewport){

			if (scrollPane == null){
				JViewport p = (JViewport)this.getParent();
				scrollPane = (JScrollPane)p.getParent();
				scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
					public void adjustmentValueChanged(AdjustmentEvent e) {
						//need to repaint the triangle when scroll bar moves
						repaint();
					}
				});
			}
			return scrollPane;
		}else
			throw new RuntimeException("need a scrollpane");
	}

//	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = e.getPoint();
		Polygon polygon = getTriangle();

		if (polygon.contains(p)){
			inTheTriangleZone = true;
			this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
		}else{
			inTheTriangleZone = false;
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		this.repaint();
	}

	/**
	 *
	 * Generates a polygon like this :
	 *
	 *   /|
	 *  / |
	 * ----
	 *
	 *<br />
	 * At Code: This triangle represents the triangle for the bottom left corner of the
	 * viewport. <br />
	 * At Console: This triangle represents the triangle for the top right corner of the
	 * viewport.
	 * @return the generated triangle
	 */

	private Polygon getTriangle() {

		JViewport viewport = getScrollPane().getViewport();

		//get bounds of viewport
		Rectangle bounds = viewport.getBounds();

		//position of viewport relative to text area.
		Point viewportPosition = viewport.getViewPosition();

		int w = viewportPosition.x + bounds.width;
		int h = viewportPosition.y + bounds.height;

		int[] xs = null;
		int[] ys = null;
		
		if(this.type != null){
			if(type == JTA_type.CODE){
				int[] x = {0,0,triangleSize}; //{w,w,w-triangleSize};
				int[] y = {h-triangleSize,h,h};
				xs = x;
				ys = y;
			}else if(type == JTA_type.CONSOLE){
				int[] x = {w,w,w-triangleSize};
				int[] y = {triangleSize,0,0};
				xs = x;
				ys = y;
			}
		}else{
			int[] x = {w,w,w-triangleSize};
			int[] y = {h-triangleSize,h,h};
			xs = x;
			ys = y;
		}

		Polygon polygon = new Polygon(xs, ys, 3);
		return polygon;
	}
	
	/**
	 * Repaints the current text area according to the mouse movement
	 * @param e
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Implementation
	public void mouseDragged(MouseEvent e) {

		Point p = e.getPoint();

		if (mousePressedInTriangle){ //mouse was pressed in triangle so we can resize

			inTheTriangleZone = true;

			int xDiff = (mousePressedPoint.x - p.x);
			int yDiff = (mousePressedPoint.y - p.y);

			if(this.type!=null){
				if(this.type == JTA_type.CODE)
					this.newSize = new Dimension(sizeAtPress.width + xDiff, sizeAtPress.height - yDiff);
				else if(this.type == JTA_type.CONSOLE){
					this.newSize = new Dimension(sizeAtPress.width - xDiff, sizeAtPress.height - yDiff);
					Point conLoc = Gui2.console.getLocationOnScreen();
//					Gui2.console.setLocation(conLoc.x, this.sizeAtPress.height - yDiff);
				}else newSize = new Dimension(250, 250); //return; //
			}else return; //newSize = new Dimension(sizeAtPress.width, sizeAtPress.height);
			getScrollPane().getViewport().setSize(newSize);
			getScrollPane().getViewport().setPreferredSize(newSize);
			getScrollPane().getViewport().setMinimumSize(newSize);
			getScrollPane().getParent().validate();
			this.repaint();
			Gui2.pubwindow.repaint(); // prevents the repited pattern effect
			this.getParent().validate();
		}
	}

	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		System.out.println("mouse clicked");
		if (getTriangle().contains(p)){
			mousePressedInTriangle = true;
			mousePressedPoint = p;
			sizeAtPress = getScrollPane().getSize();
		}else{
		}
	}
	@Implementation
	public void mouseReleased(MouseEvent e) {
		mousePressedInTriangle = false;
		mousePressedPoint = null;
	}
	@Implementation
	public void mouseClicked(MouseEvent e) {
	}
	@Implementation
	public void mouseEntered(MouseEvent e) {
	}
	@Implementation
	public void mouseExited(MouseEvent e) {
		inTheTriangleZone=false;
//		repaint();
	}
	@Implementation
	public void keyPressed(KeyEvent e) {
	}
	@Implementation
	public void keyReleased(KeyEvent e) {
	}
	@Implementation
	public void keyTyped(KeyEvent e) {
	}
}
