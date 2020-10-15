import java.awt.Color;
import java.awt.Graphics;

public class Circ extends Shape {

	public Circ(int x, int y, int w, int h, Color c) {
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
		g.fillOval(x, y, width, height);
	}

	@Override
	public boolean isOn(int x, int y) {
		if((int)(Math.pow(x-(this.x+width/2),2))+(int)(Math.pow(y-(this.y+width/2), 2)) < (int)(Math.pow(width/2,2))) return true;
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}

}
