package WebScraper;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

public class Wikipedia {
	
	private final int WIDTH = 500, HEIGHT = 500;
	String outputText;
	
	public Wikipedia() {
		
		JFrame frame = new JFrame("Welcome to Wikipedia Search! Please enter in a search");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setFocusable(true);
		
		JPanel inputPanel = new JPanel();
		
		JLabel input = new JLabel("Enter in a topic:");
		
		JTextArea inputText = new JTextArea();
		inputText.setPreferredSize(new Dimension(100, 20));
		
		JLabel space = new JLabel("                  ");
		
		JButton search = new JButton("Search");
		
		inputPanel.add(input);
		inputPanel.add(inputText);
		inputPanel.add(space);
		inputPanel.add(search);
		
		JTextArea displayArea = new JTextArea(outputText);
		displayArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(displayArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT*0.8)));
		
		panel.add(inputPanel);
		panel.add(scroll);
		
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String text = inputText.getText().toLowerCase();
				
				if(text != null && text != "") {
					getSite(text);
				}
				
				displayArea.setText(outputText);
				
			}
			
		});
		
	}
	
	public void getSite(String site) {
		
		outputText = "";
		
		System.out.println(site);
		
		try {
			Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + site).get();
			
			Elements para = doc.select("p");
			
			String s = para.first().text().toLowerCase().trim();
			
			System.out.println(s);
			
			if(s.equals(site + " may refer to:") || s.equals(site + " may also refer to:")) {
				outputText = "Please enter something more specific and try again";
				System.out.println(outputText);
			} else {
				for(Element e : para) {
					outputText += e.text() + "\n";
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			outputText = "The website name does not exist. Please try something else and try again";
			e.printStackTrace();
		}

	}
	
	public static void main (String[] args) {
		new Wikipedia();
	}

}
