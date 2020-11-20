package IndividualProject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board {
	
	private final int width = 400;
	private Number[][] board = new Number[4][4]; 
	private Number[][] oldBoard = new Number[4][4];
	private Font myFont = new Font("Arial", Font.BOLD, 30);
	
	public Board() {
		
		HashMap<Integer, Color> color = new HashMap<Integer, Color>();
		color.put(2, new Color(255, 224, 233));
		color.put(4, new Color(255, 194, 212));
		color.put(8, new Color(255, 158, 187));
		color.put(16, new Color(255, 122, 162));
		color.put(32, new Color(224, 87, 128));
		color.put(64, new Color(185, 55, 94));
		color.put(128, new Color(138, 40, 70));
		color.put(256, new Color(138, 40, 70));
		color.put(512, new Color(138, 40, 70));
		color.put(1024, new Color(96, 36, 55));
		color.put(2048, new Color(82, 46, 56));
		
		newTile();
		newTile();
		
		JPanel paint = new JPanel() {
						
			public void paint(Graphics g) {
						
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(6));
				g2.setColor(Color.GRAY);
			
				for(int i = 0; i < 4 ; i++) {
					for(int j = 0; j < 4 ; j++) {
						if(board[i][j] != null) {
							g.setColor(color.get(board[i][j].num));
							g.fillRect(j*100, i*100, 100, 100);
							
							g.setColor(Color.GRAY);
							if(board[i][j].num > 100) g.setColor(Color.WHITE);
							g.setFont(myFont); 
						    FontMetrics metrics = g.getFontMetrics(myFont);
						    int x = j*100 + (100 - metrics.stringWidth(String.valueOf(board[i][j].num))) / 2;
						    int y = i*100 + ((100 - metrics.getHeight())/2) + metrics.getAscent();
							g2.drawString(String.valueOf(board[i][j].num), x, y);
							g.setColor(Color.GRAY);
						} else {
							g.setColor(Color.WHITE);
							g.fillRect(j*100, i*100, 100, 100);
							g.setColor(Color.GRAY);
						}
						g2.drawRoundRect(j*100, i*100, 100, 100, 10, 10);
					}
				}
			}
			
		};
		
		JFrame frame = new JFrame();
		frame.setSize(width+15, width+38);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(paint);
		paint.setFocusable(true);
	
		
		paint.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				
				for(int i = 0; i < 4; i++) {
					for(int j = 0; j < 4; j++) {
						oldBoard[i][j]=board[i][j];
					}
				}
				
				switch(e.getKeyCode()) { 
	            case KeyEvent.VK_UP:
	            	for(int i = 0; i < 4; i++) {
						for(int j = 0; j < 4; j++) {
							if(board[i][j] != null) {
								moveTile(i, j, -1.5, 0);
							}
						}
					}
	            	combine(0, 0, 0, 1);
	            	break;
	            case KeyEvent.VK_DOWN:
	            	for(int i = 3; i >= 0; i--) {
						for(int j = 3; j >= 0; j--) {
							if(board[i][j] != null) {
								moveTile(i, j, 1.5, 0);
							}
						}
					}
	            	combine(0, 0, 1, 0);
	                break;
	            case KeyEvent.VK_LEFT:
	            	for(int i = 0; i < 4; i++) {
						for(int j = 0; j < 4; j++) {
							if(board[i][j] != null) {
								moveTile(i, j, 0, -1.5);
							}
						}
					}
	            	combine(1, 0, 0, 0);
	                break;
	            case KeyEvent.VK_RIGHT :
	            	for(int i = 3; i >= 0; i--) {
						for(int j = 3; j >= 0; j--) {
							if(board[i][j] != null) {
								moveTile(i, j, 0, 1.5);
							}
						}
					}
	            	combine(0, 1, 0, 0);	
	                break;
				}
				
				paint.repaint();
				
				for(int i = 0; i < 4; i++) {
					for(int j = 0; j < 4; j++) {
						if(board[i][j] != null) {
							if(board[i][j].num == 2048) {
								int options = JOptionPane.showOptionDialog(frame, "Do you want to continue", "You Won!", 0, JOptionPane.INFORMATION_MESSAGE, null, new String[] {"Continue", "Restart", "Leave"} , null);
								if(options == 1) {
									for(int a = 0; a < 4; a++) {
										for(int b = 0; b < 4; b++) {
											board[a][b] = null;
										}
									}
									newTile();
									newTile();
								} else if (options == 2) {
									System.exit(0);
								}
							}
						}
					}
				}
				
				if(!(Arrays.deepEquals(board, oldBoard))) newTile();
				else {
					if(didWin()) {
						int options = JOptionPane.showOptionDialog(frame, "Do you want to restart?", "Game Over", 0, JOptionPane.INFORMATION_MESSAGE, null, new String[] {"Leave", "Restart"} , null);
						if(options == 0) {
							System.exit(0);
						} else {
							for(int i = 0; i < 4; i++) {
								for(int j = 0; j < 4; j++) {
									board[i][j] = null;
								}
							}
							newTile();
							newTile();
						}
					}
				}
				
				
				paint.repaint();
				
			}
			
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			
		});
		
		
		
	}
	
	public void newTile() {
		int newTile = (int) (16*Math.random());
		int num = (int) (5*Math.random());
		if(num != 4) num = 2;
			
		while(board[newTile/4][newTile%4] != null) {
			newTile = (int) (16*Math.random());
		}
		board[newTile/4][newTile%4] = new Number(num, newTile/4, newTile%4);
	}
	public void moveTile(int row, int col, double dirR, double dirC) {
		for(double i = -1.5; i <= 1.5; i++) {
			if(board[(int) (Math.abs(row*(int)dirC) + (int)(dirR)*(dirR-i))][(int) (Math.abs(col*(int)dirR) + (int)(dirC)*(dirC-i))] == null) {
				board[(int)(Math.abs(row*(int)dirC) + (int)(dirR)*(dirR-i))][(int) (Math.abs(col*(int)dirR) + (int)(dirC)*(dirC-i))] = board[row][col];
				board[row][col] = null;
				return;
			} else if(board[(int) (Math.abs(row*(int)dirC) + (int)(dirR)*(dirR-i))][(int) (Math.abs(col*(int)dirR) + (int)(dirC)*(dirC-i))] == board[row][col]) {
				return;
			}
		}
	}
	public void combine(int l, int r, int d, int u) {
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 3; j++) {
				int x1 = l*i + r*i + + u*j + d*(3-j);
				int y1 = l*j + r*(3-j) + u*i + d*i;
				int x2 = l*i + r*i + u*(j+1) + d*(2-j);
				int y2 = l*(j+1) + r*(2-j) + u*i + d*i;
				if(board[x1][y1] != null 
						& board[x2][y2] != null) {
					if(board[x1][y1].num == board[x2][y2].num) {
						board[x1][y1] = new Number(board[x1][y1].num*2,x1,y1);
						board[x2][y2] = null;
					}
				}
			}
		}
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(board[i][j] != null) moveTile(i, j, d*1.5 + u*-1.5, r*1.5 + l*-1.5);
			}
		}
	}
	
	public boolean didWin() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(board[i][j] == null) {
					return false;
				}
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j<3; j++) {
				if(board[i][j].num == board[i][j+1].num) {
					return false;
				}
			}
		}
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 3; i++) {
				if(board[i][j].num == board[i+1][j].num) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		new Board();
	}

}
