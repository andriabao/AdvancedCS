package GraphicsEditor;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class GraphicsEditor {
	
	private final int WIDTH = 600, HEIGHT = 600;
	private int initX, initY;
	private int lineW = 1, textS = 1;
	int mode = 0;
	private Color color;
	JLayeredPane panel = new JLayeredPane();

	
	ArrayList<Shape> drawList = new ArrayList<Shape>();
	ArrayList<Text> textList = new ArrayList<Text>();
	
	public GraphicsEditor() {
			
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
		JButton colButton = new JButton("Choose Color");
		JButton textButton = new JButton("Text");
		
		JTextArea lineNum = new JTextArea("1");
		lineNum.setEditable(true);
		lineNum.setPreferredSize(new Dimension(20,20));
		JLabel lineLabel = new JLabel(" Line Width: ");
		
		JTextArea textNum = new JTextArea("1");
		textNum.setEditable(true);
		textNum.setPreferredSize(new Dimension(20,20));
		JLabel textLabel = new JLabel(" Text Size: ");
			
		JPanel ButtonPanel1 = new JPanel();
		ButtonPanel1.setPreferredSize(new Dimension(WIDTH, 35));
		JPanel ButtonPanel2 = new JPanel();
		ButtonPanel2.setPreferredSize(new Dimension(WIDTH, 35));
		paint.setPreferredSize(new Dimension(WIDTH, 500));
		
		ButtonPanel1.add(circButton);
		ButtonPanel1.add(rectButton);
		ButtonPanel1.add(lineButton);
		ButtonPanel1.add(lineLabel);
		ButtonPanel1.add(lineNum);
		ButtonPanel2.add(delButton);
		ButtonPanel2.add(colButton);
		ButtonPanel2.add(textButton);
		ButtonPanel2.add(textLabel);
		ButtonPanel2.add(textNum);
		panel.add(ButtonPanel1);
		panel.add(ButtonPanel2);
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
		
		colButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(panel,"Select a color", Color.WHITE);
			}
		});
		
		textButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				mode = 5;
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
					drawList.add(new Circ(e.getX(), e.getY(), 0, 0, color));
				}
				if(mode == 2) {
					drawList.add(new Rect(e.getX(), e.getY(), 0, 0, color));
					initX = e.getX();
					initY = e.getY();
				}
				if(mode == 3) {
					try{
						lineW = Integer.parseInt(lineNum.getText());
						drawList.add(new Line(e.getX(), e.getY(), 0, 0, lineW, color));
						
					} catch (NumberFormatException a) {
						JOptionPane.showMessageDialog(panel, "Please enter in an integer for the stroke size");
						lineNum.setText(null);
					}
				}
				if(mode == 5) {
					try{
						textS = Integer.parseInt(textNum.getText());
						textList.add(new Text(e.getX(), e.getY(), 0, 0, color));
						drawList.add(textList.get(textList.size()-1));
						initX = e.getX();
						initY = e.getY();
																		
					} catch (NumberFormatException a) {
						JOptionPane.showMessageDialog(panel, "Please enter in an integer for the text size");
						lineNum.setText(null);
					}
				}
				
				panel.repaint();
			}
			
			public void mouseReleased(MouseEvent e) {
				for(int i = 0; i<textList.size(); i++) {
					textList.get(i).textBox().setEditable(true);
					panel.add(textList.get(i).textBox());
				}
			}
			public void mouseClicked(MouseEvent e) {
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
					drawList.get(drawList.size()-1).resize(initX,initY,e.getX(),e.getY());
				}
				if(mode == 3) {
					drawList.get(drawList.size()-1).resize(e.getX(), e.getY(), e.getX(),e.getY());
				}
				if(mode == 5) {
					drawList.get(drawList.size()-1).resize(initX,initY,e.getX(),e.getY());
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