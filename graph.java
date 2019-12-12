package cyber_project;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.*;
import javax.swing.JFrame;

public class graph extends Canvas
{
	public graph(){}

	public void paint(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(100, 150, 300, 200);
		g.setColor(new Color(255,200,0));
		g.fillOval(100, 150, 300, 200);
	} 
}



