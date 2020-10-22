package GraphicsEditor;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JTextArea;

public class Text extends Shape {
			
	public Text(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.drawRect(x, y, width, height);
	}

	@Override
	public boolean isOn(int x, int y) {
		if(x<this.x+width && x>this.x && y<this.y+height && y>this.y) {
			return true;
		}
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		
		width = Math.abs(x2-x1);
		height = Math.abs(y2-y1);
		x = Math.min(x1, x2);
		y = Math.min(y1, y2);
		
	}
	
	public JTextArea textBox() {
		JTextArea area = new JTextArea(" Click to edit text");
		area.setEditable(true);
		area.setFocusable(true);
		area.setBounds(x+6, y+89, width-1, height-1);
		return area;
	}

}
