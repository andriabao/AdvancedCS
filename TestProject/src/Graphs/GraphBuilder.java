package Graphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Graphs.LocationGraph.Vertex;

public class GraphBuilder {
	
	private final int WIDTH = 800, HEIGHT = 580;
	private Image img;
	private LocationGraph<String> map = new LocationGraph<String>();
	
	public GraphBuilder(){
		
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			img = ImageIO.read(new File("map.png"));
		} catch(Exception e) {
			System.out.println("picture not working");
		}
				
		JPanel paintPanel = new JPanel() {
			public void paint(Graphics g) {
				
				g.drawImage(img, 0, 0, null);
				
				g.setColor(Color.BLUE);
				
				for(Vertex v : map.vertices.values()) {
					
					g.fillOval(v.xLoc, v.yLoc, 10, 10);
					g.drawString((String) v.info, v.xLoc + 10, v.yLoc + 10);
					
				}
				
			}
		};
		
		frame.add(paintPanel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);
					    
	    		
		paintPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
			    PrintWriter pw;
				
			    try {
					pw = new PrintWriter(new FileWriter("mapData"));
					
					String name = JOptionPane.showInputDialog("Name this point");
					map.addVertex(name, e.getX(), e.getY());
					pw.println(name + "~" + e.getX() + "~" + e.getY());
					paintPanel.repaint();
					
					pw.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}				

				
			}

			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		});
		
		
	}
	
	public static void main(String[]  args) {
		new GraphBuilder();
	}
}
