import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimpleGraphics extends JPanel  {
	
	private final int width = 230, height = 260;
 
	public SimpleGraphics() {
		
		JFrame frame = new JFrame();
		
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		this.setFocusable(true);
		
		run();
	}

	public void run() {

	}
	
	public void paint(Graphics g) {
		
		Color lime = new Color(20,255,63);
		int headX = 15; //values for the head
		int headY = 40;
		int headW = 100;
		
		int bodyY = 95; //values for the body
		int bodyH = 205;
		
		int RarcX = 85; //values for the right body arc
		int RarcW = 30;
		int RarcSA = 57;
		int RarcEA = -75;
		
		int LarcX = 15; //values for the left body arc
		int LarcW = 90;
		int LarcSA = 134; //angles
		int LarcEA = 64;
		
		int eyeW = 5; //values for the eyes
		int LeyeX = 32;
		int LeyeY = 80;
		int ReyeX= 82;
		int ReyeY = 73;
		
		int smileX = 10; //values for the smile
		int smileY = 45;
		int smileW = 100;
		int smileH = 70;
		int smileSA = 240;
		int smileEA = 80;
		
		int fillY = 150; //values to fill in the body
		int fillH = 40;
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(lime); //set background color
		g2.fillRect(0, 0, width, width);
		
		g2.setColor(Color.WHITE);
		g2.fillOval(headX, headY, headW, (int)(headW*0.95)); //fill in head
		g2.fillArc(RarcX, bodyY, RarcW, bodyH, RarcSA, RarcEA); //fill in part of body
		g2.fillArc(LarcX, bodyY, LarcW, bodyH, LarcSA, LarcEA);
		g2.fillRect((int)(headX*1.2), fillY, (int)(headW*0.95), fillH*2); //fill in top part of body
		g2.fillRect((int)(headX*1.8), headX+headW, (int)(headW*0.85), fillH); //fill in bottom part of body
		
		
        g2.setStroke(new BasicStroke(4)); //stroke width for lines
		g2.setColor(Color.BLACK);
		g2.drawOval(headX, headY, headW, (int)(headW*0.95)); //draw head
		g2.drawArc(RarcX, bodyY, RarcW, bodyH, RarcSA, RarcEA); //draw right body line
		g2.drawArc(LarcX, bodyY, LarcW, bodyH, LarcSA, LarcEA); //draw left body line
		g2.fillOval(LeyeX, LeyeY, eyeW, eyeW); //left eye
		g2.fillOval(ReyeX, ReyeY, eyeW, eyeW); //right eye
		g2.drawArc(smileX, smileY, smileW, smileH, smileSA, smileEA); //smile
		
	}

	public static void main(String[] args) {
		new SimpleGraphics(); 
	}
}
