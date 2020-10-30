package GraphicsEditor;
import java.awt.Color;
import java.awt.Graphics;

public class Circ extends Shape {

	public Circ(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}

	@Override
	public Shape copy() {
		return null;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(x-width/2, y-height/2, width, height); //draws circle with (x,y) at center
	}

	@Override
	public boolean isOn(int x, int y) { //distance formula
		if((int)(Math.pow(x-(this.x),2))+(int)(Math.pow(y-(this.y), 2)) < (int)(Math.pow(width/2,2))) return true;
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		width = 2*(int) Math.sqrt(Math.pow(x2-x,2)+Math.pow(y2-y, 2));
		height = width;
	}

}
