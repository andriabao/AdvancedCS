package Graphs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Graphs.LocationGraph.Edge;
import Graphs.LocationGraph.Vertex;

public class GraphBuilder {
	
	private final int WIDTH = 800, HEIGHT = 580; //final variables for window width and height
	private final int POINTR = 10; //final int for point radius
	private Image img;
	private LocationGraph<String> map = new LocationGraph<String>(); //new map
	LocationGraph<String>.Vertex prev = null; //saves previous vertex 
	
	/* Graph Builder
	 * Click on the map to create a vertex
	 * Name the vertex
	 * If the vertex name already exists, the previous one will be overwritten
	 * Click on two vertices to create an edge
	 * You cannot create an edge with two of the same vertices
	 * Run GPS to find the shortest paths */
	
	
	public GraphBuilder(){
		
		//create frame
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//instantiate background image
		try {
			img = ImageIO.read(new File("map.png"));
		} catch(Exception e) {
			System.out.println("picture not working");
		}
		
		//create paint panel
		JPanel paintPanel = new JPanel() {
			public void paint(Graphics g) {
				
				//graphics 2d to make lines thicker
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(3)); 
				
				//drawing background image
				g.drawImage(img, 0, 0, null);
				
				g.setColor(Color.BLUE);
				
				//cycling through all of the vertices
				for(LocationGraph<String>.Vertex v : map.vertices.values()) {
					
					//cycling through all of the edges for each vertex
					for(LocationGraph<String>.Edge e : v.edges) {
						
						//drawing line between vertices
						g.drawLine(e.v1.xLoc, e.v1.yLoc, e.v2.xLoc, e.v2.yLoc);
					}
					
					//drawing point
					g.fillOval(v.xLoc-POINTR/2, v.yLoc-POINTR/2, POINTR, POINTR);
					//drawing white background for text label
					g.setColor(Color.WHITE);
					g.fillRect(v.xLoc + POINTR, v.yLoc-POINTR/2, g.getFontMetrics().stringWidth(v.info), POINTR);
					//drawing text label
					g.setColor(Color.BLUE);
					g.drawString((String) v.info, v.xLoc + POINTR, v.yLoc + POINTR/2);
					
				}
				
			}
		};
		
		//sets up frame
		frame.add(paintPanel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);
					    
		//mouse listener
		paintPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				  PrintWriter pw;
				  
				  //boolean to check if mouse is on an already created point
				  boolean isOn = false;
				    
				  	//cycles through each of the existing vertices and checks if it is on it
				    for (LocationGraph<String>.Vertex v : map.vertices.values()) {
				    	
				    	if(v.isOn(e.getX(), e.getY())) {
				    		isOn = true;
				    		
				    		//if clicked vertex is the first vertex, save it
				    		if(prev == null) {
				    			prev = v;
				    		} else if (!prev.info.equals(v.info)){ //cannot connect vertex to itself
				    			map.connect((String) prev.info, (String) v.info);
				    			prev = null;
				    		}
				    	}
				    	paintPanel.repaint();
				    }		    
				    
				    if(!isOn) { //if mouse is not on vertex, create new vertex
			    		String name = JOptionPane.showInputDialog("Name this point");
			    		map.addVertex(name, e.getX(), e.getY());	
				    }
				    
			    	try { //creates file with that saves vertices and edges
			    		pw = new PrintWriter(new FileWriter("vertexData"));
				
			    		for(LocationGraph<String>.Vertex v : map.vertices.values()) {
			    			pw.print(v.info + "~" + v.xLoc + "~" + v.yLoc);
			    			for(LocationGraph<String>.Edge e1 : v.edges) {
			    				pw.print("~" + e1.getNeighbor(v).info);
			    			}
			    			pw.print("\n");
			    		}
						pw.close();
						
						paintPanel.repaint();
					
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
