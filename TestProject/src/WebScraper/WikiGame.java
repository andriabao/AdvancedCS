package WebScraper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiGame {
	
	private final int WIDTH = 500, HEIGHT = 500, BUTTONH = 45; //final variables for window size
	int innerW = (int) (WIDTH*0.95);
	int innerH = (int) (HEIGHT*0.9);
	long time = 0; //variable to store time to run program
	
	String outputText = ""; //variable to store output text
	
	public WikiGame() throws IOException {
		
		//create JFrame
		JFrame frame = new JFrame("Welcome to Wikipedia Game!");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create panels
		JPanel outerPanel = new JPanel(); //outer panel that panel is contained in
		JPanel panel = new JPanel(); //panel containing smaller panels
		JPanel inputPanel = new JPanel(); //panel containing user inputs
		JPanel searchPanel = new JPanel(); //panel containing search and instruction buttons
		JTextArea displayArea = new JTextArea(); //output area
		
		//create border for window
		final TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.PINK, 5), 
				" Wikipedia Game ", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Helvetica", Font.BOLD, 20));

		//create components inside panels
		JLabel input1 = new JLabel("Topic 1:"); //labels
		JLabel input2 = new JLabel("Topic 2:");
		JTextArea inputText1 = new JTextArea(); //input texts
		JTextArea inputText2 = new JTextArea();
		JLabel space = new JLabel("                  ");
		JButton search = new JButton("Search"); //buttons
		JButton instructions = new JButton("Instructions");
		
		//set sizes of components
		inputText1.setPreferredSize(new Dimension(100, 20));
		inputText2.setPreferredSize(new Dimension(100, 20));
		
		//set sizes of panels
		outerPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setPreferredSize(new Dimension(innerW, innerH));
		searchPanel.setPreferredSize(new Dimension(innerW, BUTTONH));
		inputPanel.setPreferredSize(new Dimension(innerW, BUTTONH));
		
		//set options for panel
		panel.setBorder(title);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setFocusable(true);
		
		//set options for display area
		displayArea.setEditable(false);
		displayArea.setPreferredSize(new Dimension((int) (innerW*0.9), innerH- 2*BUTTONH));
		displayArea.setLineWrap(true); //wraps text
		displayArea.setWrapStyleWord(true); //wraps text at spaces
		
		//create scroll bar
		JScrollPane scroll = new JScrollPane(displayArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(innerW, (int) (HEIGHT*0.8)));
		
		//adding panels to other panels
		inputPanel.add(input1);
		inputPanel.add(inputText1);
		inputPanel.add(space);
		inputPanel.add(input2);
		inputPanel.add(inputText2);
		searchPanel.add(search); //adding buttons to search panel
		searchPanel.add(instructions);
		panel.add(inputPanel); //adding smaller panels to bigger panel
		panel.add(searchPanel);
		panel.add(scroll);
		outerPanel.add(panel); //putting panel inside of outer panel
		frame.add(outerPanel); //putting panel inside frame
		
		//finishing setting up frame
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		//action listener for search button
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//saving input strings
				String start = inputText1.getText().toLowerCase();
				String end = inputText2.getText().toLowerCase();
				
				//error checking
				if(start.length() == 0) { //if first input is empty
					JOptionPane.showMessageDialog(frame, "Please make sure there is an input for the first item");
				} else if (end.length() == 0) { //if second input is empty
					JOptionPane.showMessageDialog(frame, "Please make sure there is an input for the second item");
				} else if(start.equals(end)) { //if inputs are the same
					JOptionPane.showMessageDialog(frame, "The inputs are the same!");
				} else { 
					try { //tries connecting to documents
						Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + start).get();
						Document docE = Jsoup.connect("https://en.wikipedia.org/wiki/" + end).get();
						
						//searches through wikipedia
						ArrayList<String> path = search(start, end);
						
						if(path == null) { //no path exists
							displayArea.setText("No path exists");
						} else {
							if(path.size() == 1) { //when end is located on same page as start
								displayArea.setText(end.toUpperCase() + " can be found on the same site as " + start.toUpperCase());
							} else {
								displayArea.setText("To go from " + start.toUpperCase() + " to " + end.toUpperCase() + " go through the following sites. \n");
								for(int i = 0; i < path.size(); i++) { //outputs path
									displayArea.append(path.get(i).toUpperCase() + "-");
								}
								displayArea.append(end.toUpperCase() + "\n \n");
								displayArea.append("This path went through " + (path.size() + 1) + " sites \n \n"); //length of path
								displayArea.append("This took about " + time + " seconds to run"); //time to run
							}
						}
						
					} catch (IOException e1) { //catch to make sure the website exists
						JOptionPane.showMessageDialog(frame, "Please make sure the website exists on wikipedia.");
					}
				}
			}
			
		});
		
		//action listener for instructions button
		instructions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { //shows message dialog for instructions
				JOptionPane.showMessageDialog(frame, "Welcome to Wiki Game \n"
						+ "Wiki Game outputs the path between the two pages by \n"
						+ "navigating through the links located within each wikipedia page \n"
						+ "Input two Wikipedia pages to get started");
			}
			
		});
				
	}
	
	//search method
	public ArrayList<String> search(String start, String target) {
		
		long sStartTime = System.currentTimeMillis(); //start time

		ArrayList<String> toVisit = new ArrayList<String>(); //array list of things to visit
		toVisit.add(start);
		
		ArrayList<String> visited = new ArrayList<String>(); //array list of already visited sites
		
		HashMap<String,String> leadsTo = new HashMap<String,String>(); //hashmap to store which sites lead to which
		
		while(!toVisit.isEmpty()) { //goes until there is nothing to visit
			
			String site = toVisit.remove(0); //takes first site on list
			visited.add(site); //adds currently searching site to visited
						
			try {
				Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + site).get(); //connects to document			
				Elements links = doc.select("p").select("a"); //list of links
				
				for(Element e : links) { //goes through each link
					
					String s = e.toString().substring(2).toLowerCase(); //formatting for link
					s = s.substring(s.indexOf('>')+1,s.indexOf('<')); //only save substring for title of link
															
					if(!e.attr("abs:href").contains("#cite")) { //checks if link is citation
								
						if(s.equals(target)) { //checks if link is target site
																					
							if(site.equals(start)) { //returns original site if the site is the same as the start
								ArrayList<String> arr = new ArrayList<String>();
								arr.add(start);
								
								long sEndTime = System.currentTimeMillis(); //end time to run
								time = (sEndTime-sStartTime)/1000;
								
								return arr;
							}
							
							long sEndTime = System.currentTimeMillis(); //end time to run
							time = (sEndTime-sStartTime)/1000;
							
							return backTrace(site, leadsTo); //back trace
							
						} else { //if site is not equal to target
							if(!visited.contains(s)) { //if site has not been visited before
								toVisit.add(s); //adds site to tovisit
								leadsTo.put(s, site); //saves s leads to site
							}
						}
					}
									
				}
				
			} catch (IOException e1) {
				continue;
			}
			
		}
		
		long sEndTime = System.currentTimeMillis(); //end time to run
		time = (sEndTime-sStartTime)/1000;

		return null;
		
	}
	
	//back trace
	public ArrayList<String> backTrace(String target, HashMap<String,String> leadsTo) {
		
		String curr = target; //current site
		ArrayList<String> path = new ArrayList<String>(); //saving path
		
		while (curr != null) { //backtracing until there is nothing there
			path.add(0, curr); //adding tracing to path
			curr = leadsTo.get(curr); //gets where current site leads to
		}
		
		return path;
	}
	
	//main
	public static void main (String[] args) throws IOException {
		new WikiGame();
	}

}
