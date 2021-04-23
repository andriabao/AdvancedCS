package WebScraper;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiGame {
	
	private final int WIDTH = 500, HEIGHT = 500;
	String outputText = "";
	
	public WikiGame() throws IOException {
		
		JFrame frame = new JFrame("Wikipedia Game");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setFocusable(true);
		
		JPanel inputPanel = new JPanel();
		JPanel searchPanel = new JPanel();
		
		JLabel input1 = new JLabel("Topic 1:");
		JLabel input2 = new JLabel("Topic 2:");
		
		JTextArea inputText1 = new JTextArea();
		JTextArea inputText2 = new JTextArea();
		inputText1.setPreferredSize(new Dimension(100, 20));
		inputText2.setPreferredSize(new Dimension(100, 20));
		
		JLabel space = new JLabel("                  ");
		
		JButton search = new JButton("Search");
		
		inputPanel.add(input1);
		inputPanel.add(inputText1);
		inputPanel.add(space);
		inputPanel.add(input2);
		inputPanel.add(inputText2);
		
		searchPanel.add(search);
		
		JTextArea displayArea = new JTextArea();
		displayArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(displayArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT*0.8)));
		
		panel.add(inputPanel);
		panel.add(searchPanel);
		panel.add(scroll);
		
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String start = inputText1.getText().toLowerCase();
				String end = inputText2.getText().toLowerCase();
				
				if(start.length() == 0) {
					displayArea.setText("Please make sure there is an input for the first item");
				} else if (end.length() == 0) {
					displayArea.setText("Please make sure there is an input for the second item");
				} else if(start.equals(end)) {
					displayArea.setText("The inputs are the same!");
				} else {
					try {
						Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + start).get();

						ArrayList<String> path = search(start, end);
						
						if(path == null) {
							displayArea.setText("No path exists");
						} else {
							displayArea.setText(path.toString());
						}
						
					} catch (IOException e1) {
						displayArea.setText("Please make sure the website exists on wikipedia.");
					}
				}
			}
			
		});
		
//		System.out.println(search("dog", "fire,"));
		
	}
	
	public ArrayList<String> search(String start, String target) {
		
		ArrayList<String> toVisit = new ArrayList<String>();
		toVisit.add(start);
		
		HashMap<String,String> leadsTo = new HashMap<String,String>();
		
		while(!toVisit.isEmpty()) {
			
			String site = toVisit.remove(0);			
			Document doc;
			
			try {
				doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + site).get();
								
				Elements para = doc.select("p");
				Elements links = para.select("a");
				
				for(Element e : links) {
					
					String s = e.toString().substring(2).toLowerCase();
					s = s.substring(s.indexOf('>')+1,s.indexOf('<'));
										
					String cap;
					
					if(start.length() == 1) {
						cap = start.toUpperCase();
					} else {
						cap = Character.toString(start.charAt(0)).toUpperCase() + start.substring(1);
					}
										
					
					if(!e.attr("abs:href").contains("#cite")) {
																		
						if(s.equals(target)) {
														
							if(site.equals(start)) {
								ArrayList<String> arr = new ArrayList<String>();
								arr.add(start);
								return arr;
							}
							return backTrace(site, leadsTo);
							
						} else {
							toVisit.add(s);
							leadsTo.put(s, site);
						}
					}
									
				}
				
			} catch (IOException e1) {
				continue;
			}
			
		}
		return null;
		
	}
	
	public ArrayList<String> backTrace(String target, HashMap<String,String> leadsTo) {
		
		String curr = target;
		ArrayList<String> path = new ArrayList<String>();
		
		while (curr != null) {
			path.add(0, curr);
			curr = leadsTo.get(curr);
		}
		return path;
	}
	
	public static void main (String[] args) throws IOException {
		new WikiGame();
	}

}
