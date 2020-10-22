package GraphicsEditor;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Line extends Shape {
	
	int lineW;

	public Line(int x, int y, int w, int h, int strokeW, Color c) {
		super(x, y, w, h, c);
		lineW = strokeW;
	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(c);
		g2.setStroke(new BasicStroke(lineW));
		g2.drawLine(x, y, x+width, y+height);
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		width = x2-x;
		height = y2-y;
	}

}
