package GraphicsEditor;
import java.awt.Color;
import java.awt.Graphics;

public class Rect extends Shape {

	public Rect(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(x, y, width, height);
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

}
