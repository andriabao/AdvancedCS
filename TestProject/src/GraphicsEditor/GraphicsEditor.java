package GraphicsEditor;
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
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class GraphicsEditor {
	
	private final int WIDTH = 600, HEIGHT = 600, INPUTW = 20, BUTTONH = 35; //values for width and height of panels
	private int initX, initY;
	private int lineW = 1, textS = 1, penW = 1; //user input numbers
	private double zoomS = 1.1;
	public static int mode = 0;
	private int prevX = 0, currX = 0, prevY = 0, currY = 0; //keep track of previous xLoc
	private Color color; //color of shapes
	
	ArrayList<Shape> drawList = new ArrayList<Shape>(); //list of shapes to be drawn
	
	public GraphicsEditor() {
		
		JPanel panel = new JPanel();
		JPanel paint = new JPanel() { //paint panel
			public void paint(Graphics g) {
				super.paint(g);
				
				//draws each shape in drawList
				for(int i = 0; i < drawList.size(); i++) {
					(drawList.get(i)).draw(g);
				}
				
			}
		};
			
		paint.setLayout(null);
		
		//buttons
		JButton circButton = new JButton("Circle");
		JButton rectButton = new JButton("Rectangle");
		JButton lineButton = new JButton("Line");
		JButton delButton = new JButton("Delete");
		JButton colButton = new JButton("Choose Color");
		JButton textButton = new JButton("Text");
		JButton zoomButton = new JButton("Zoom");
		JButton penButton = new JButton("Pen");		
		
		JTextArea lineNum = new JTextArea("1"); //user input line width, default is 1
		lineNum.setEditable(true);
		lineNum.setPreferredSize(new Dimension(INPUTW,INPUTW));
		JLabel lineLabel = new JLabel(" Line Width: ");
		
		JTextArea textNum = new JTextArea("12"); //user input text size, default is 12
		textNum.setEditable(true);
		textNum.setPreferredSize(new Dimension(INPUTW,INPUTW));
		JLabel textLabel = new JLabel(" Text Size: ");
		
		JTextArea zoomNum = new JTextArea("1.1"); //user input zoom, default is 1.1
		zoomNum.setEditable(true);
		zoomNum.setPreferredSize(new Dimension(INPUTW,INPUTW));
		JLabel zoomLabel = new JLabel(" Zoom Scale: ");
		
		JTextArea penNum = new JTextArea("8"); //user input pen width, default is 8
		penNum.setEditable(true);
		penNum.setPreferredSize(new Dimension(INPUTW,INPUTW));
		JLabel penLabel = new JLabel(" Pen Width: ");
			
		//creating button panels for each row of buttons
		JPanel ButtonPanel1 = new JPanel(); 
		ButtonPanel1.setPreferredSize(new Dimension(WIDTH, BUTTONH));
		JPanel ButtonPanel2 = new JPanel();
		ButtonPanel2.setPreferredSize(new Dimension(WIDTH, BUTTONH));
		JPanel ButtonPanel3 = new JPanel();
		ButtonPanel3.setPreferredSize(new Dimension(WIDTH, BUTTONH));
		paint.setPreferredSize(new Dimension(WIDTH, HEIGHT-3*BUTTONH));
		
		//adding buttons to the button panels
		ButtonPanel1.add(circButton);
		ButtonPanel1.add(rectButton);
		ButtonPanel1.add(lineButton);
		ButtonPanel1.add(lineLabel);
		ButtonPanel1.add(lineNum);
		ButtonPanel2.add(textButton);
		ButtonPanel2.add(textLabel);
		ButtonPanel2.add(textNum);
		ButtonPanel2.add(zoomButton);
		ButtonPanel2.add(zoomLabel);
		ButtonPanel2.add(zoomNum);
		ButtonPanel3.add(delButton);
		ButtonPanel3.add(colButton);
		ButtonPanel3.add(penButton);
		ButtonPanel3.add(penLabel);
		ButtonPanel3.add(penNum);
		
		//adding button panels to larger panel
		panel.add(ButtonPanel1); 
		panel.add(ButtonPanel2);
		panel.add(ButtonPanel3);
		panel.add(paint);
		
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		
		panel.setBorder(BorderFactory.createTitledBorder("Graphics Editor"));
		
		//adding action listeners to all the buttons
		circButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { mode = 1; }
		});
		
		rectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { mode = 2;}
		});
		
		lineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { mode = 3;}
		});
		
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {mode = 4;}
		});
		
		colButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(panel,"Select a color", Color.WHITE); //opens JColorChooser
			}
		});
		
		textButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {mode = 5;}
		});
		
		zoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {mode = 6;}
		});
		
		penButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {mode = 7;}
		});
		
		//create JFrame
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(panel);
		panel.setFocusable(true);		
		
		//add mouse listener to paint panel
		paint.addMouseListener(new MouseListener() {
			
			public void mousePressed(MouseEvent e) { //creates corresponding shape for each mode
				if(mode == 1) {
					drawList.add(new Circ(e.getX(), e.getY(), 0, 0, color));
				}
				if(mode == 2) {
					drawList.add(new Rect(e.getX(), e.getY(), 0, 0, color));
					initX = e.getX(); //holds values for the original X and original Y
					initY = e.getY();
				}
				if(mode == 3) {
					try{ //checks if the input is a valid input
						lineW = Math.abs(Integer.parseInt(lineNum.getText()));
						drawList.add(new Line(e.getX(), e.getY(), 0, 0, lineW, color));
						
					} catch (NumberFormatException a) { //sends error message if there is no valid input
						JOptionPane.showMessageDialog(panel, "Please enter in an integer for the stroke size");
						lineNum.setText("1");
					}
				}
				if(mode == 5) {
					try{ //same as above try catch
						textS = Math.abs(Integer.parseInt(textNum.getText()));
						drawList.add(new Text(e.getX(), e.getY(), 0, 0, textS, color));
						initX = e.getX();
						initY = e.getY();								
					} catch (NumberFormatException a) {
						JOptionPane.showMessageDialog(panel, "Please enter in an integer for the text size");
						textNum.setText("12");
					}
				}
				if(mode == 7) {
					try{ //same as above try catch
						penW = Math.abs(Integer.parseInt(penNum.getText()));
						drawList.add(new Circ(e.getX(),e.getY(), penW, penW, color));						
					} catch (NumberFormatException a) {
						JOptionPane.showMessageDialog(panel, "Please enter in an integer for the pen size");
						penNum.setText("8");
					}
				}
				
				panel.repaint();
			}
			
			public void mouseReleased(MouseEvent e) {
				//creates a text area if text mode is chosen
				if(mode == 5) {
					Text currText = (Text)(drawList.get(drawList.size()-1));
					currText.createText(paint);
//					mouseClicked(e);
					paint.repaint();
				}
				
				//resets the location of the previous x and y locations
				prevX = 0;
				prevY = 0;
			}
			public void mouseClicked(MouseEvent e) {
				//delete mode
				if(mode == 4) {
					for(int i = 0; i < drawList.size(); i++) { //cycles through entire list of shapes 
						if(drawList.get(i).isOn(e.getX(),e.getY())) { //delete shape if mouse clicks on the shape
							drawList.remove(i);
							break;
						}
					}
				}
				
				if(mode == 6) {
					try{ //same try catch as other user inputs
						zoomS = Math.abs(Double.parseDouble(zoomNum.getText()));
						for(int i = 0; i < drawList.size(); i++) { //cycles through all of the shapes
							if(!(drawList.get(i) instanceof Text)) { //does not zoom in text boxes
								(drawList.get(i)).x = (int) ((drawList.get(i)).x*zoomS); //scales all factors of the shape by specified amount
								(drawList.get(i)).y = (int) ((drawList.get(i)).y*zoomS);
								(drawList.get(i)).width = (int) ((drawList.get(i)).width*zoomS);
								(drawList.get(i)).height = (int) ((drawList.get(i)).height*zoomS);
							}
						}
																		
					} catch (NumberFormatException a) {
						JOptionPane.showMessageDialog(panel, "Please enter in a number for the zoom scale");
						zoomNum.setText("1.1");
					}
				}
				
				if(mode == 7) {
					drawList.add(new Circ(e.getX(),e.getY(), 5, 5, color));
				}
				
				panel.repaint();
				
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		
		paint.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				
				//resizes shape where the mouse is dragged to
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
				if(mode == 7) {
					
					//sets value of current and previous X and Y
					currX = prevX;
					prevX = e.getX();
					currY = prevY;
					prevY = e.getY();
					
					//if previous location of mouse and current location of mouse are too far apart, tell user to draw slower
					if(currX != 0 && prevX != 0 && (Math.abs(prevX-currX) > 20 || Math.abs(prevY-currY) > 20)) {
						JOptionPane.showMessageDialog(panel, "Please draw slower");
						prevX = 0;
						prevY = 0;
					}
					
					//creates circles at every X between current X value and previous X value
					if(e.getX() < drawList.get(drawList.size()-1).x) {
						int slope = ((e.getY()-drawList.get(drawList.size()-1).y))/((e.getX()-drawList.get(drawList.size()-1).x));
						for(int i = Math.abs(e.getX()-drawList.get(drawList.size()-1).x); i>0; i--) {
							drawList.add(new Circ(e.getX()+i, e.getY()+i*slope, penW, penW, color));
						}
					} else if (e.getX() > drawList.get(drawList.size()-1).x){
						int slope = ((e.getY()-drawList.get(drawList.size()-1).y))/((e.getX()-drawList.get(drawList.size()-1).x));
						for(int i = 0; i < e.getX()-drawList.get(drawList.size()-1).x; i++) {
							drawList.add(new Circ(drawList.get(drawList.size()-1).x+i, drawList.get(drawList.size()-1).y+i*slope, penW, penW, color));
						}
					} else {
						//if X value does not change, draw circles at every Y between the two Y values
						for(int i = Math.abs(e.getY()-(drawList.get(drawList.size()-1).y)); i>0; i--) {
							if(e.getY()-(drawList.get(drawList.size()-1).y) > 0) {
								drawList.add(new Circ(e.getX(),e.getY()-i, penW, penW, color));
							} else {
								drawList.add(new Circ(e.getX(),e.getY()+i, penW, penW, color));
							}
						} 
					}
					
					//if X values are too close, draw circles at every Y between current Y value and previous Y value
					if(Math.abs(e.getY()-drawList.get(drawList.size()-1).y) < 15) {
						if(e.getY() < drawList.get(drawList.size()-1).y) {
							int slope = (e.getX()-drawList.get(drawList.size()-1).x)/(e.getY()-drawList.get(drawList.size()-1).y);
							for(int i = Math.abs(e.getY()-drawList.get(drawList.size()-1).y); i>0; i--) {
								drawList.add(new Circ(e.getX()+i*slope, e.getY()+i, penW, penW, color));
							}
						} else if (e.getY() > drawList.get(drawList.size()-1).y){
							int slope = (e.getX()-drawList.get(drawList.size()-1).x)/(e.getY()-drawList.get(drawList.size()-1).y);
							for(int i = 0; i < e.getY()-drawList.get(drawList.size()-1).y; i++) {
								drawList.add(new Circ(drawList.get(drawList.size()-1).x+i*slope, drawList.get(drawList.size()-1).y+i, penW, penW, color));
							}
						} 
					}
				}
				panel.repaint(); //repaint the panel
			}

			@Override
			public void mouseMoved(MouseEvent e) {}
			
		});
		
		
	}
	
	
	public static void main(String[] args) {
		new GraphicsEditor();
	}

}