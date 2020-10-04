
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Robot extends JPanel implements KeyListener  {
	
	private final int width = 800, height = 600;
	
	int xLoc = 100;
	int yLoc = 100;
	int xVel = 10;
	int yVel = 10;
	int rectW = 50;
	
	JFrame frame = new JFrame();

	public Robot() {
		
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		this.addKeyListener(this);
		
		this.setFocusable(true);
	}

	
	public void paint(Graphics g) {

		g.setColor(Color.RED);
		g.fillRect(xLoc, yLoc, rectW, rectW);
		
	}
	
	public static void main(String[] args) {
		new Robot(); 

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		
	        switch( keyCode ) { 
	            case KeyEvent.VK_UP:
	            	yLoc -= yVel;
	            	frame.repaint();
	                break;
	            case KeyEvent.VK_DOWN:
	                yLoc += yVel;
	                frame.repaint();
	                break;
	            case KeyEvent.VK_LEFT:
	                xLoc -= xVel;
	                frame.repaint();
	                break;
	            case KeyEvent.VK_RIGHT :
	                xLoc += xVel;
	                frame.repaint();
	                break;
	         }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		repaint();
		
	}
}
