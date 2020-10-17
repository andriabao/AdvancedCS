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
		width = x2-x;
		height = y2-y;
	}

}
