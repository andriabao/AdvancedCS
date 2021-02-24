package Graphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;


public class KevinBaconGame {
	
	HashMap<String,String> actors = new HashMap<String,String>();
	HashMap<String,String> movies = new HashMap<String,String>();
	
	LabeledGraph<String, String> g = new LabeledGraph<String, String>();
	
	private final int WIDTH = 600, HEIGHT = 600, BUTTONH = 70, INPUTW = 110, INPUTH = 20, BOTTOMB= 6, SIDEB = 5;
	
	Color backColor = Color.GRAY;
	
	int innerW = (int) (WIDTH*0.95);
	
	int total = 0;
	 
	public KevinBaconGame() throws FileNotFoundException {
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		JPanel borderPanel = new JPanel();
		borderPanel.setPreferredSize(new Dimension(innerW, (int)(HEIGHT * 0.9)));
		JPanel actorPanel = new JPanel();
		actorPanel.setPreferredSize(new Dimension(innerW, BUTTONH));
		JTextArea outputPanel = new JTextArea();
		outputPanel.setPreferredSize(new Dimension(innerW, HEIGHT-2*BUTTONH));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(innerW, (int) (BUTTONH)));
		
		
		String[] options = {"Choose Color", "Close Game"};
		
		int chooser = JOptionPane.showOptionDialog(panel, "It is recommended to choose a ligher background color", "Choose a Background Color", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		
		if(chooser != 0) {
			System.exit(0);
		} 
		
		backColor = JColorChooser.showDialog(panel,"Select a color", Color.GRAY);
		
		if(backColor == null) {
			System.exit(0);
		}
		
		JLabel a1 = new JLabel("Actor 1:");
		JLabel a2 = new JLabel("Actor 2:");
		JTextArea a1Text = new JTextArea();
		JTextArea a2Text = new JTextArea();
		a1Text.setPreferredSize(new Dimension(INPUTW,INPUTH));
		a2Text.setPreferredSize(new Dimension(INPUTW,INPUTH));
		
		outputPanel.setEditable(false);
		a1Text.setEditable(false);
		a2Text.setEditable(false);
		
		outputPanel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		outputPanel.append(" Files loading... Please wait");
		
		JButton pathButton = new JButton("Shortest Path");
		JButton mutualButton = new JButton("Mutual Actors");
		JButton avgButton = new JButton("Average Connectivity");
		
		actorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, (int)(innerW*0.02), (BUTTONH - INPUTH - BOTTOMB)/2));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, (int)(innerW*0.05), (BUTTONH - INPUTH - BOTTOMB)/2));
		
		actorPanel.add(a1);
		actorPanel.add(a1Text);
		actorPanel.add(a2);
		actorPanel.add(a2Text);
		buttonPanel.add(pathButton);
		buttonPanel.add(mutualButton);
		buttonPanel.add(avgButton);
				
		BoxLayout boxlayout = new BoxLayout(borderPanel, BoxLayout.Y_AXIS);
		borderPanel.setLayout(boxlayout);
	
		borderPanel.add(actorPanel);
		borderPanel.add(outputPanel);
		borderPanel.add(buttonPanel);
		
		panel.add(borderPanel);
				
		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 5), 
				" Kevin Bacon Game ", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.BOLD, 20));
		
		borderPanel.setBorder(title);
		borderPanel.setBackground(backColor);
		
		actorPanel.setBorder(BorderFactory.createMatteBorder(SIDEB, BOTTOMB, 0, BOTTOMB, backColor));
		buttonPanel.setBorder(BorderFactory.createMatteBorder(0, BOTTOMB, SIDEB, BOTTOMB, backColor));
		outputPanel.setBorder(BorderFactory.createMatteBorder(0, BOTTOMB, 0, BOTTOMB, backColor));
		
		panel.setBackground(backColor);
		
		float[] hsbVals = new float[3];
		Color.RGBtoHSB(backColor.getRed(), backColor.getGreen(), backColor.getBlue(), hsbVals);
				
		actorPanel.setBackground(new Color(Color.HSBtoRGB(hsbVals[0], (float) (hsbVals[1]*0.6), hsbVals[2])));
		buttonPanel.setBackground(new Color(Color.HSBtoRGB(hsbVals[0], (float) (hsbVals[1]*0.6), hsbVals[2])));

		
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(panel);
		borderPanel.setFocusable(true);
		
		frame.setVisible(true);
		
		try {
			startGame();
			
			a1Text.setEditable(true);
			a2Text.setEditable(true);
			
			outputPanel.append(" \n Files loaded! Please input actor names. \n \n"
					+ " The shortest path button finds the shortest path between the two actors. \n"
					+ " The mutual actors finds the actors that both actors share a movie with. \n"
					+ " The average connectivity button finds the average distance an actor is from \n all other actors \n");
			
		} catch (FileNotFoundException e) {
			outputPanel.append(" Files not found :(. Please make sure all files are downloaded and named correctly. \n");
		}
		
		HashMap<String, String> sActors = simplify(actors);
		
		pathButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String actor1 = a1Text.getText().replaceAll(" ", "").toLowerCase();
				String actor2 = a2Text.getText().replaceAll(" ", "").toLowerCase();
								
				if(!sActors.containsKey(actor1)) {
					outputPanel.append("Please make sure there is a valid input for actor 1 \n");
				} else if (!sActors.containsKey(actor2)) {
					outputPanel.append("Please make sure there is a valid input for actor 2 \n");
				} else {
										
					ArrayList<Object> path = g.BFS(sActors.get(actor1), sActors.get(actor2));
					
					outputPanel.append("The shortest path length between these two actors is " + path.size()/2 + ": \n");
					
					for(int i = 0; i < path.size(); i+=2) {
						if(i == 0) {
							outputPanel.append(sActors.get(actor1) + " and " + path.get(i) + " are in the movie " + path.get(i+1) + "\n");
						} else {
							outputPanel.append(path.get(i-2) + " and " + path.get(i) + " are in the movie " + path.get(i+1) + "\n");
						}
					}
					
				}
			}
			
		});
		
		mutualButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String actor1 = a1Text.getText().replaceAll(" ", "").toLowerCase();
				String actor2 = a2Text.getText().replaceAll(" ", "").toLowerCase();
								
				if(!sActors.containsKey(actor1)) {
					outputPanel.append("Please make sure there is a valid input for actor 1 \n");
				} else if (!sActors.containsKey(actor2)) {
					outputPanel.append("Please make sure there is a valid input for actor 2 \n");
				} else {
					ArrayList<String> mutuals = g.findMutuals(sActors.get(actor1), sActors.get(actor2));
					
					if(mutuals.size() == 0) {
						outputPanel.append(sActors.get(actor1) + " and " + sActors.get(actor2) + " share no mutual actors \n");
					} else {
						outputPanel.append(sActors.get(actor1) + " and " + sActors.get(actor2) + " share the following mutual actors:");
						
						for(String s : mutuals) {
							outputPanel.append(s + "\n");
						}
						
					}
				}
				
			}
			
		});
		
		avgButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String actor1 = a1Text.getText().replaceAll(" ", "").toLowerCase();
				String actor2 = a2Text.getText().replaceAll(" ", "").toLowerCase();
				
				total = 0;
								
				if(!sActors.containsKey(actor1)) {
					outputPanel.append("Please make sure there is a valid input for actor 1 \n");
				} else if (!sActors.containsKey(actor2)) {
					outputPanel.append("Please make sure there is a valid input for actor 2 \n");
				} else {
					HashMap<Object, Integer> connections = g.connectivity(sActors.get(actor1));					
										
					connections.forEach((k,v) -> total= total + v);
				}
				
				double numActors = sActors.size();
				
				double avgC = total/numActors;
				
				System.out.println(avgC);
				
				outputPanel.append(Double.toString(avgC) + "\n");
				
			}
			
		});
		
	}
	
	private void startGame() throws FileNotFoundException {
		
		ArrayList<String> actorsInMovie = new ArrayList<String>();
		String lineA, lineM, lineMA;
		
		BufferedReader ar = new BufferedReader(new FileReader("actors.txt"));
		BufferedReader mr = new BufferedReader(new FileReader("movies.txt"));
		BufferedReader mar = new BufferedReader(new FileReader("movie-actors.txt"));
										
		try {
			while((lineA = ar.readLine()) != null) {
				int index = lineA.indexOf('~');
				actors.put(lineA.substring(0,index), lineA.substring(index+1));
			}
			while((lineM = mr.readLine()) != null) {
				int index = lineM.indexOf('~');
				movies.put(lineM.substring(0,index), lineM.substring(index+1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		actors.forEach((k,v) -> g.addVertex(v));
		
		try {
			lineMA = mar.readLine();
			String movieName = lineMA.substring(0,lineMA.indexOf('~'));
			actorsInMovie.add(lineMA.substring(lineMA.indexOf('~')+1));
						
			while((lineMA = mar.readLine()) != null) {
				if(lineMA.substring(0,lineMA.indexOf('~')).equals(movieName)) {
					
					for(int i = 0; i < actorsInMovie.size(); i++) {
						g.connect(actors.get(lineMA.substring(lineMA.indexOf('~')+1)), actors.get(actorsInMovie.get(i)), movies.get(movieName));
					}
					
					actorsInMovie.add(lineMA.substring(lineMA.indexOf('~')+1));
				
				} else {
					movieName = lineMA.substring(0,lineMA.indexOf('~'));
					actorsInMovie.clear();
					actorsInMovie.add(lineMA.substring(lineMA.indexOf('~')+1));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private HashMap<String,String> simplify (HashMap <String,String> actors) {
		
		HashMap<String, String> sActors = new HashMap<String,String>();
		
		actors.forEach((k,v) -> sActors.put(v.replaceAll(" ","").toLowerCase(), v));
		
		return sActors;
	}	
		
	
	
	public static void main(String[] args) throws FileNotFoundException {
		new KevinBaconGame();
	}
}
