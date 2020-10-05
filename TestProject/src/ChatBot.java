import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;


public class ChatBot {
	
	private final int WIDTH = 600, HEIGHT = 600, TEXTHEIGHT = 400;
	private String[] chatBot = {"I like pineapples on pizza", "The weather outside is beautiful",
			"Look at the sunshine and rainbows", "Garfield is my favorite lasagnia loving cat",
			"I hope you are having a great day", "I think you need a break", "Sorry I didn't get that. Try again"};
	
	public ChatBot() {
		
		JPanel panel = new JPanel();
		
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		
		panel.setBorder(BorderFactory.createTitledBorder("Chat Bot"));
		
		JTextArea displayarea = new JTextArea();
		displayarea.setEditable(false);
		JTextArea textArea = new JTextArea();
		textArea.setEditable(true);
		
		JScrollPane scroll = new JScrollPane (displayarea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		scroll.setPreferredSize(new Dimension(WIDTH,TEXTHEIGHT));
		panel.add(scroll);
		panel.add(textArea);
		
		JButton sendButton = new JButton("Send");
		
		sendButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(!(textArea.getText().equals("") || textArea.getText().equals(" "))) {
					displayarea.append("User: " + textArea.getText() + "\n");
					textArea.setText(null);
					displayarea.append("ChatBot: " + chatBot[(int)(Math.random()*7)] + "\n");
				}
			}
		});
		
		JPanel innerPanel = new JPanel();
		innerPanel.add(sendButton);
		panel.add(innerPanel);
		
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		panel.setFocusable(true);
		
	}
	
	public static void main(String[] args) {
		new ChatBot();
	}
}