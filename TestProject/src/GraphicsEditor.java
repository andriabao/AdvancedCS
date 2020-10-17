import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
		JButton lineButton = new JButton("Line");
		JButton delButton = new JButton("Delete");
		
		
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setPreferredSize(new Dimension(WIDTH, 50));
		paint.setPreferredSize(new Dimension(WIDTH, 500));
		
		ButtonPanel.add(circButton);
		ButtonPanel.add(rectButton);
		ButtonPanel.add(lineButton);
		ButtonPanel.add(delButton);
		panel.add(ButtonPanel);
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
		
		lineButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 3;
			}
		});
		
		delButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 4;
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
			
			public void mousePressed(MouseEvent e) {
				if(mode == 1) {
					drawList.add(new Circ(e.getX(), e.getY(), 
							0, 0, Color.BLUE));
				}
				if(mode == 2) {
					drawList.add(new Rect(e.getX(), e.getY(), 
							0, 0, Color.RED));
				}
				if(mode == 3) {
					drawList.add(new Line(e.getX(), e.getY(), 0, 0, Color.GREEN));
				}
				
				panel.repaint();
			}
			
			public void mouseReleased(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if(mode == 3) {
					drawList.add(new Line(e.getX(), e.getY(), 100, 100, Color.GREEN));
				}
				if(mode == 4) {
					for(int i = 0; i < drawList.size(); i++) {
						if(drawList.get(i).isOn(e.getX(),e.getY())) {
							drawList.remove(i);
							break;
						}
					}
				}
				
				panel.repaint();
				
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		
		paint.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(mode == 1) {
					drawList.get(drawList.size()-1).resize(e.getX(), e.getY(), e.getX(), e.getY());
				}
				if(mode == 2) {
					drawList.get(drawList.size()-1).resize(e.getX(),e.getY(),e.getX(),e.getY());
				}
				if(mode == 3) {
					drawList.get(drawList.size()-1).resize(e.getX(),e.getY(),e.getX(),e.getY());
				}
				panel.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	
	public static void main(String[] args) {
		new GraphicsEditor();
	}

}