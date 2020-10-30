package GraphicsEditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Text extends Shape {
	
	JTextArea area;
	int textS;
	
	public Text(int x, int y, int w, int h, int textSize, Color c) {
		super(x, y, w, h, c);
		textS = textSize;
	}

	@Override
	public Shape copy() {
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
	
	public void createText(JPanel panel) {
		area = new JTextArea(" Double click in delete mode to delete the text box");
		area.setEditable(true);
		area.setFocusable(true);
		area.setSize(width-1, height-1);
		area.setLocation(x+1, y+1);
		area.setFont(new Font("Verdana", Font.PLAIN, textS));
		panel.add(area);
		
		area.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(GraphicsEditor.mode == 4) {
					panel.remove(area);
				}
			}

			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		});
	}

}
