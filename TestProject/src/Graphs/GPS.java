package Graphs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Graphs.LocationGraph.Vertex;

public class GPS<E> {
	
	LocationGraph<String> myMap = new LocationGraph<String>();
	private final int WIDTH = 800, HEIGHT = 580; //final variables for window width/height
	private final int POINTR = 10; //final int for radius of each point
	private Image img;
	LocationGraph<String>.Vertex prev = null;
	HashMap<String, Integer[]> vertices = new HashMap<String, Integer[]>(); //mapping vertices to an int array of their locations
	
	ArrayList<Object> path = new ArrayList<Object>();

	public GPS() throws FileNotFoundException {
		
		readMap(); //reads file
		
		//creates frame
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//instantiates image
		try {
			img = ImageIO.read(new File("map.png"));
		} catch(Exception e) {
			System.out.println("picture not working");
		}
		
		//create paint panel
		JPanel paintPanel = new JPanel() {
			public void paint(Graphics g) {
				
				//graphics 2d for making lines thicker
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(2)); 
				
				//drawing background image
				g.drawImage(img, 0, 0, null);
				
				//setting line color
				g.setColor(Color.BLUE);
				
				//cycling through each vertex
				for(LocationGraph<String>.Vertex v : myMap.vertices.values()) {
					
					//cycling through each vertices edges
					for(LocationGraph<String>.Edge e : v.edges) {
						g.drawLine(e.v1.xLoc, e.v1.yLoc, e.v2.xLoc, e.v2.yLoc); //drawing line between vertices
					}
					
					//draw point at location
					g.fillOval(v.xLoc-POINTR/2, v.yLoc-POINTR/2, POINTR, POINTR);
					//draw white rectangular background for text
					g.setColor(Color.WHITE);
					g.fillRect(v.xLoc + POINTR, v.yLoc-POINTR/2, g.getFontMetrics().stringWidth(v.info), POINTR);
					//draws text label for point
					g.setColor(Color.BLUE);
					g.drawString((String) v.info, v.xLoc + 10, v.yLoc + 5);
					
				}
				
				//error checking for when there is no path
				if(path.size() == 0) {
					g.drawString("No current path", 10, 10);
				}
				
				//drawing path
				if(path.size() > 0) {
					g.setColor(Color.RED);
					
					for(int i = 0; i < path.size()-1; i++) {
												
						g.fillOval(vertices.get(path.get(i))[0]-POINTR/2, vertices.get(path.get(i))[1]-POINTR/2, POINTR, POINTR);
						g.drawLine(vertices.get(path.get(i))[0], vertices.get(path.get(i))[1],
								vertices.get(path.get(i+1))[0], vertices.get(path.get(i+1))[1]);
						
					}
					
					g.fillOval(vertices.get(path.get(path.size()-1))[0]-POINTR/2, vertices.get(path.get(path.size()-1))[1]-POINTR/2, POINTR, POINTR);
				}
				
				
				
			}
		};
		
		frame.add(paintPanel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);
					    
	    //mouselistener		
		paintPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				 
				for (LocationGraph<String>.Vertex v : myMap.vertices.values()) {
				    	
				    	if(v.isOn(e.getX(), e.getY())) {
				    		
				    		if(prev == null) {
				    			prev = v;
				    		} else if (v.equals(prev)) {
				    			return;
				    		} else {
				    			path = myMap.Djikstra(prev.info, (String) v.info);
				    			prev = null;
				    		}
				    	}
				    	paintPanel.repaint();
				    }
				
			}

			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			
		});
		
		
	}
	
	private void readMap() throws FileNotFoundException {
		
		String curr;
		BufferedReader mapR = new BufferedReader(new FileReader("vertexData"));
		BufferedReader edgeR = new BufferedReader(new FileReader("vertexData"));
		
		try {
			while((curr = mapR.readLine()) != null) { //cycles through entire file
				String[] info = curr.split("~");
				
				//creates vertex with information and location
				myMap.addVertex(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]));
				vertices.put(info[0], new Integer[] {Integer.parseInt(info[1]), Integer.parseInt(info[2])});

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//connects edges after because vertices need to exist before edges are created
		try {
			while((curr = edgeR.readLine()) != null) {
				String[] info = curr.split("~");
				
				if(info.length > 2) { //check if vertex is connected to anything
					for(int i = 3; i < info.length; i++) { //connects vertices to vertices it is connected to
						myMap.connect(info[0], info[i]);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws FileNotFoundException {
		new GPS<String>();
	}

}
