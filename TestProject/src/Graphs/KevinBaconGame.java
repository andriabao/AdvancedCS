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
import java.text.DecimalFormat;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;


public class KevinBaconGame {
	
	//creating maps for storing actors and movies from files
	HashMap<String,String> actors = new HashMap<String,String>();
	HashMap<String,String> movies = new HashMap<String,String>();
	
	//creating graph
	LabeledGraph<String, String> g = new LabeledGraph<String, String>();
	
	//constants for sizing of window and components
	private final int WIDTH = 600, HEIGHT = 600, BUTTONH = 70, INPUTW = 150, INPUTH = 20, BOTTOMB= 6, SIDEB = 5; 
	int innerW = (int) (WIDTH*0.95);
	int innerH = (int) (HEIGHT*0.92);
	
	Color backColor = Color.GRAY; //variable for storing background color
	int totalDist = 0; //variable for storing total distance when finding connectivity
	
	//create titled border for window
	final TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 5), 
			" Kevin Bacon Game ", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.BOLD, 20));
	 
	//constructor
	public KevinBaconGame() throws FileNotFoundException {
		
		//creating jframe
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);		
		
		//creating panels
		JPanel outerPanel = new JPanel(); //outer panel that everything is stored in (also creates a border for main game)
		JPanel panel = new JPanel(); //main panel containing all components
		JPanel actorPanel = new JPanel(); //panel containing actor inputs
		JTextArea outputPanel = new JTextArea(); //panel containing outputs
		JPanel buttonPanel = new JPanel(); //panel containing buttons for user interaction
		
		//creating actors labels and inputs
		JLabel a1 = new JLabel("Actor 1:");
		JLabel a2 = new JLabel("Actor 2:");
		JTextArea a1Text = new JTextArea();
		JTextArea a2Text = new JTextArea();
		
		//creating buttons
		JButton pathButton = new JButton("Shortest Path");
		JButton mutualButton = new JButton("Mutual Actors");
		JButton avgButton = new JButton("Average Connectivity");
		
		//setting sizes of components
		outerPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setPreferredSize(new Dimension(innerW, innerH));
		actorPanel.setPreferredSize(new Dimension(innerW, BUTTONH));
		outputPanel.setPreferredSize(new Dimension(innerW, innerH-BUTTONH));
		buttonPanel.setPreferredSize(new Dimension(innerW, (int) (BUTTONH)));
		a1Text.setPreferredSize(new Dimension(INPUTW,INPUTH));
		a2Text.setPreferredSize(new Dimension(INPUTW,INPUTH));
		
		//text formatting for output text
		outputPanel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		outputPanel.setText(" \n Files loading... Please wait");
		outputPanel.setLineWrap(true); //wraps text
		outputPanel.setWrapStyleWord(true); //wraps text at spaces
		
		//no user input while program is still starting
		outputPanel.setEditable(false);
		a1Text.setEditable(false);
		a2Text.setEditable(false);
		
		//scrollbar for output text
		JScrollPane scroll = new JScrollPane (outputPanel);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(innerW-SIDEB*3,innerH-2*BUTTONH));	
		
		//formatting/layout for containers
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		actorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, (int)(innerW*0.02), (BUTTONH - INPUTH - BOTTOMB)/2));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, (int)(innerW*0.05), (BUTTONH - INPUTH - BOTTOMB)/2));
		
		//user option to choose background color
		String[] options = {"Choose Color", "Close Game"}; //user options
		int chooser = JOptionPane.showOptionDialog(outerPanel, "It is recommended to choose a ligher background color", "Choose a Background Color", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if(chooser != 0) System.exit(0); //closes program if user does not choose color
		
		backColor = JColorChooser.showDialog(outerPanel,"Select a color", Color.GRAY); //built in color chooser
		if(backColor == null) System.exit(0); //closes program if user does not choose color
		
		//creating borders for components (color is chosen by user)
		panel.setBorder(title);
		actorPanel.setBorder(BorderFactory.createMatteBorder(SIDEB, BOTTOMB, 0, BOTTOMB, backColor));
		buttonPanel.setBorder(BorderFactory.createMatteBorder(0, BOTTOMB, SIDEB, BOTTOMB, backColor));
		scroll.setBorder(BorderFactory.createMatteBorder(0, BOTTOMB, 0, BOTTOMB, backColor));
		outputPanel.setBorder(BorderFactory.createMatteBorder(0, BOTTOMB, 0, 0, Color.WHITE));
		
		//converting color from rgb to hsb to change brightness
		float[] hsbVals = new float[3];
		Color.RGBtoHSB(backColor.getRed(), backColor.getGreen(), backColor.getBlue(), hsbVals);
		
		//setting background colors (chosen by user)
		panel.setBackground(backColor);
		outerPanel.setBackground(backColor);
		actorPanel.setBackground(new Color(Color.HSBtoRGB(hsbVals[0], (float) (hsbVals[1]*0.4), hsbVals[2]))); //inner panels are a less saturated color
		buttonPanel.setBackground(new Color(Color.HSBtoRGB(hsbVals[0], (float) (hsbVals[1]*0.4), hsbVals[2])));

		//adding components to containers
		actorPanel.add(a1);
		actorPanel.add(a1Text);
		actorPanel.add(a2);
		actorPanel.add(a2Text);
		buttonPanel.add(pathButton);
		buttonPanel.add(mutualButton);
		buttonPanel.add(avgButton);	
		panel.add(actorPanel);
		panel.add(scroll);
		panel.add(buttonPanel);
		outerPanel.add(panel);
		
		frame.add(outerPanel);		
		frame.setVisible(true); //allowing everything to be visible
		
		try { //try catch if files are not found
			startGame(); //starts game
			
			a1Text.setEditable(true); //allows actors inputs to be editable
			a2Text.setEditable(true);
			
			//instructions for user
			outputPanel.setText(" \n Files loaded! Please input actor names. \n \n" 
					+ " -------------------------------------------------------------------- \n"
					+ " The shortest path button finds the shortest path between the two actors. \n"
					+ " The mutual actors button finds the actors that both actors share a movie with. \n"
					+ " The average connectivity button finds the average distance an actor is from all other actors \n"
					+ " -------------------------------------------------------------------- \n");
			
		} catch (FileNotFoundException e) { 
			outputPanel.setText(" \n Files not found :(. Please make sure all files are downloaded and named correctly. \n"); 
		}
		
		HashMap<String, String> sActors = simplify(actors); //hashmap mapping simplified actor names to actor names
		
		pathButton.addActionListener(new ActionListener() { //mouse listener for finding the shortest path

			public void actionPerformed(ActionEvent e) {
				
				//simplifies actor names
				String actor1 = a1Text.getText().replaceAll(" ", "").toLowerCase();
				String actor2 = a2Text.getText().replaceAll(" ", "").toLowerCase();
								
				//error checking
				if(!sActors.containsKey(actor1)) {
					outputPanel.setText(" \n Please make sure there is a valid input for actor 1 \n");
				} else if (!sActors.containsKey(actor2)) {
					outputPanel.setText(" \n Please make sure there is a valid input for actor 2 \n");
				} else {
										
					ArrayList<Object> path = g.BFS(sActors.get(actor1), sActors.get(actor2)); //bfs for shortest path
					
					//output
					outputPanel.setText(" \n The shortest path length between these two actors is " + path.size()/2 + ": \n");
					
					for(int i = 0; i < path.size(); i+=2) {
						if(i == 0) {
							outputPanel.append(" " + sActors.get(actor1) + " and " + path.get(i) + " are in the movie " + path.get(i+1) + "\n");
						} else {
							outputPanel.append(" " + path.get(i-2) + " and " + path.get(i) + " are in the movie " + path.get(i+1) + "\n");
						}
					}
					
				}
			}
			
		});
		
		mutualButton.addActionListener(new ActionListener() { //mouse listener for mutual actor button

			public void actionPerformed(ActionEvent e) {
				String actor1 = a1Text.getText().replaceAll(" ", "").toLowerCase();
				String actor2 = a2Text.getText().replaceAll(" ", "").toLowerCase();
								
				if(!sActors.containsKey(actor1)) {
					outputPanel.setText(" \n Please make sure there is a valid input for actor 1 \n");
				} else if (!sActors.containsKey(actor2)) {
					outputPanel.setText(" \n Please make sure there is a valid input for actor 2 \n");
				} else {
					ArrayList<String> mutuals = g.findMutuals(sActors.get(actor1), sActors.get(actor2));
					
					if(mutuals.size() == 0) {
						outputPanel.setText("\n " + sActors.get(actor1) + " and " + sActors.get(actor2) + " share no mutual actors \n");
					} else {
						outputPanel.setText("\n " + sActors.get(actor1) + " and " + sActors.get(actor2) + " share the following mutual actors: \n");
						
						for(String s : mutuals) {
							outputPanel.append(" " + s + "\n");
						}
						
					}
				}
				
			}
			
		});
		
		avgButton.addActionListener(new ActionListener() { //mouse listener for average connectivity button

			public void actionPerformed(ActionEvent e) {
				String actor1 = a1Text.getText().replaceAll(" ", "").toLowerCase();
				String actor2 = a2Text.getText().replaceAll(" ", "").toLowerCase();
				
				totalDist = 0; //resets value of total distance
								
				if(!sActors.containsKey(actor1) && !sActors.containsKey(actor2)) { //allows program to work unless both inputs are invalid
					outputPanel.setText(" \n Please make sure there is a valid input \n");
				} else if (!sActors.containsKey(actor2)) {
					
					//confirmation message
					String[] options = {"Yes", "No"};
					int chooser = JOptionPane.showOptionDialog(outerPanel, "Do you want to find connectivity for actor 1?", null, 
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					
					if(chooser == 0) {
						HashMap<Object, Integer> connections = g.connectivity(sActors.get(actor1));					
						
						connections.forEach((k,v) -> totalDist= totalDist + v);
						
						//rounding number for average
						double numActors = sActors.size();
						double avgC = totalDist/numActors;
						DecimalFormat df = new DecimalFormat("#.###");
						
						System.out.println(avgC);
						
						outputPanel.setText("\n " + sActors.get(actor1) + " has an average connectivity of: " + df.format(avgC) + "\n");
					}
					
				} else if (!sActors.containsKey(actor1)) {
					
					//confirmation message
					String[] options = {"Yes", "No"};
					int chooser = JOptionPane.showOptionDialog(outerPanel, "Do you want to find connectivity for actor 2?", null, 
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					
					if(chooser == 0) {
						HashMap<Object, Integer> connections = g.connectivity(sActors.get(actor2));					
						
						connections.forEach((k,v) -> totalDist= totalDist + v);
						
						//rounding number for average
						double numActors = sActors.size();
						double avgC = totalDist/numActors;
						DecimalFormat df = new DecimalFormat("#.###");
						
						System.out.println(avgC);
						
						outputPanel.setText("\n " + sActors.get(actor2) + " has an average connectivity of: " + df.format(avgC) + "\n");
					}
					
				} else {
					
					//choosing which actor to find connectivity out of both
					String[] options = {sActors.get(actor1), sActors.get(actor2)};
					int chooser = JOptionPane.showOptionDialog(outerPanel, "Choose which actor to find the connectivity of", "Choose an actor", 
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					
					while(chooser != 0 && chooser != 1) {
						chooser = JOptionPane.showOptionDialog(outerPanel, "Choose an option", "Please choose an actor", 
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					}
					
					HashMap<Object, Integer> connections = g.connectivity(options[chooser]);					
										
					connections.forEach((k,v) -> totalDist= totalDist + v);
					
					//rounding number for average
					double numActors = sActors.size();
					double avgC = totalDist/numActors;
					DecimalFormat df = new DecimalFormat("#.###");
					
					System.out.println(avgC);
					
					outputPanel.setText("\n " + options[chooser] + " has an average connectivity of: " + df.format(avgC) + "\n");
					
				}
				
			}
			
		});
		
	}
	
	private void startGame() throws FileNotFoundException {
		
		ArrayList<String> actorsInMovie = new ArrayList<String>(); //list of actors that are in same movie
		String lineA, lineM, lineMA; //variables for each actor 
		
		//files
		BufferedReader ar = new BufferedReader(new FileReader("actors.txt"));
		BufferedReader mr = new BufferedReader(new FileReader("movies.txt"));
		BufferedReader mar = new BufferedReader(new FileReader("movie-actors.txt"));
										
		try { //puts actors and movies in map
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
			
		//adds a vertex for each actor
		actors.forEach((k,v) -> g.addVertex(v));
		
		try {
			lineMA = mar.readLine();
			String movieName = lineMA.substring(0,lineMA.indexOf('~')); //variable for current movie being read
			actorsInMovie.add(lineMA.substring(lineMA.indexOf('~')+1));
						
			while((lineMA = mar.readLine()) != null) { //reads through entire file
				if(lineMA.substring(0,lineMA.indexOf('~')).equals(movieName)) {
					
					//connects actor to all other actors in movie
					for(int i = 0; i < actorsInMovie.size(); i++) {
						g.connect(actors.get(lineMA.substring(lineMA.indexOf('~')+1)), actors.get(actorsInMovie.get(i)), movies.get(movieName));
					}
					
					//adds actor to list of actors in the movie
					actorsInMovie.add(lineMA.substring(lineMA.indexOf('~')+1));
				
				} else {
					movieName = lineMA.substring(0,lineMA.indexOf('~')); //updates current movie
					actorsInMovie.clear(); //updates current actors in the movie
					actorsInMovie.add(lineMA.substring(lineMA.indexOf('~')+1));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private HashMap<String,String> simplify (HashMap <String,String> actors) {
		
		/* simplifies actors names for easy user interface
		 * removes all spaces in actor names
		 * makes all characters lowercase
		 * ex. sam WoRthIng tOn  would be a valid input
		*/
		
		HashMap<String, String> sActors = new HashMap<String,String>(); //maps simplified actor names to actor names

		actors.forEach((k,v) -> sActors.put(v.replaceAll(" ","").toLowerCase(), v)); //cycles through all actors
		
		return sActors;
	}	
	
	
	//main
	public static void main(String[] args) throws FileNotFoundException {
		new KevinBaconGame();
	}
}
