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
		if(width != 0) {
			if((height/width)*(this.x-x) > (this.y-y)-lineW && (height/width)*(this.x-x) < (this.y-y)+lineW) {
				return true;
			}
		} else {
			if(x > this.x - lineW && this.x < this.x + lineW && y < this.y + height && y >this.y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		width = x2-x;
		height = y2-y;
	}

}
