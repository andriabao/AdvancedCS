import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GraphicsEditor {
	
	private final int WIDTH = 600, HEIGHT = 600;
	
	int mode = 0;
	
	ArrayList<Shape> drawList = new ArrayList<Shape>();
	
	public GraphicsEditor() {
		
		JPanel panel = new JPanel();
		
		JPanel paint = new JPanel() {
			public void paint(Graphics g) {
				
				for(int i = 0; i < drawList.size(); i++) {
					(drawList.get(i)).draw(g);
				}
				
			}
		};
				
		JButton circButton = new JButton("Circle");
		JButton rectButton = new JButton("Rectangle");
		
		JPanel innerPanel = new JPanel();
		innerPanel.setPreferredSize(new Dimension(WIDTH, 50));
		paint.setPreferredSize(new Dimension(WIDTH, 500));
		
		innerPanel.add(circButton);
		innerPanel.add(rectButton);
		panel.add(innerPanel);
		panel.add(paint);
		
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		
		panel.setBorder(BorderFactory.createTitledBorder("Graphics Editor"));
		
		circButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 1;
			}
		});
		
		rectButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 2;
			}
		});
		
		
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(panel);
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		panel.setFocusable(true);
		
		
		paint.addMouseListener(new MouseListener() {
			
			public void mousePressed(MouseEvent e) {}
			
			public void mouseReleased(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if(mode == 1) {
					drawList.add(new Circ(e.getX(), e.getY(), 
							100, 100, Color.BLUE));
					panel.repaint();
				}
				if(mode == 2) {
					drawList.add(new Rect(e.getX(), e.getY(), 
							100, 100, Color.RED));
					panel.repaint();
				}
				
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
	}
	
	
	public static void main(String[] args) {
		new GraphicsEditor();
	}

}