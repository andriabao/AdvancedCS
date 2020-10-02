import java.util.AbstractList;
import java.util.Scanner;


public class War<T> extends AbstractList<T> {
			
	private Node head;	
	
	private class Node {
		
		private Node next;
		private T info;
		
		
		public Node(T info) {
			this.info = info;
		}
		
		public Node (T info, Node next) {
			this.next = next;
			this.info = info;
		}
	}
	
	public boolean add(T info) {
		
		if (head == null) {
			head = new Node(info);
			return true;
		}
		
		Node curr = head;
		while (curr.next != null) {
			curr = curr.next;
		}
		
		curr.next = new Node(info);
		
		return true;
		
	}
	
	public void add(int index, T info) {

		if (index == 0) 
			head = new Node(info, head);
		else {
			Node curr = head;
			for (int i = 0; i < index-1; i++) {
				curr = curr.next;
			}
			curr.next = new Node(info, curr.next);
		}
	}
	
	public T get(int index) {
		Node curr = head;
		for (int i = 0; i < index; i++) {
			curr = curr.next;
		}
		return curr.info;
	}
	
	// index must be within our list
	public T remove (int index) {
		
		if (index == 0) {
			
			Node curr = head;
			
			head = head.next;
			
			return curr.info;
			
		} else {
			
			Node curr = head;
		
			for (int i = 0; i < index-1; i++) curr = curr.next;
		
			Node temp = curr.next;
			curr.next = curr.next.next;
		
			return temp.info;
			
		}
	}

	@Override
	public int size() {

		int size = 0;
		Node curr = head;
		
		while(curr.next != null) {
			curr = curr.next;
			size++;
		}
		
		return size;
	}
	
	@Override
	public String toString() {
		if (size() == 0) return "[ ]";
		String output = "[";
		Node curr = head;
		while (curr != null) {
			output += curr.info.toString() + ", ";
			curr = curr.next;
		}
		return output.substring(0,output.length()-2) +"]";
	}
	
	public War<Card> shuffle(War<Card> deck) {
		for(int i = 0; i < 52; i++) {
			int index2 = (int)(Math.random()*51);
			Card card1 = deck.get(i);
			Card card2 = deck.get(index2);
			
			deck.add(i, card2);
			deck.remove(i + 1);
			deck.add(index2, card1);
			deck.remove(index2+1);
		}
		
		return deck;
	}
	
	public void play(War<Card> deck) {
		
		War<Card> deck1 = new War<Card>();
		War<Card> deck2 = new War<Card>();
		
		boolean gameOver = false;
		boolean warning1 = false;
		boolean warning2 = false;
		
		for(int i = 0; i < 26; i++) {
			deck1.add(deck.get(i));
			deck2.add(deck.get(i+26));
		}
		
		Scanner s = new Scanner(System.in);
		
		while(!gameOver) {
			System.out.println("Player 1 has " + (deck1.size()+1) + " cards and Player 2 has " + (deck2.size()+1) + " cards. \n"
					+ "Press enter to deal cards \n");
			
			String input = s.nextLine();
			
			if(input.equals("")) {
				
				System.out.println("Player 1 played " + (deck1.get(0)).number() + " of " + (deck1.get(0)).suit());
				System.out.println("Player 2 played " + (deck2.get(0)).number() + " of " + (deck2.get(0)).suit());
				
				if((deck1.get(0)).number() > (deck2.get(0)).number()) {
					
					deck1.add(deck1.get(0));
					deck1.add(deck2.get(0));
					deck1.remove(0);
					deck2.remove(0);
					
					if(warning2) {
						gameOver = true;
						System.out.println("Player 1 wins the game of war!");
						break;
					} else System.out.println("Player 1 wins the round! \n");
					
					if(warning1) warning1 = false;
					
					
				} else if ((deck1.get(0)).number() < (deck2.get(0)).number()) {
					
					deck2.add(deck2.get(0));
					deck2.add(deck1.get(0));
					deck1.remove(0);
					deck2.remove(0);
					
					if(warning1) {
						gameOver = true;
						System.out.println("Player 2 wins the game of war!");
						break;
					} else System.out.println("Player 2 wins the round! \n");
					
					if(warning2) warning2 = false;
					
				} else {
					
					int coinFlip = -1;
					
					System.out.println("War!");
					System.out.println("Player 1 played " + (deck1.get(1)).number() + " of " + (deck1.get(1)).suit());
					System.out.println("Player 2 played " + (deck2.get(1)).number() + " of " + (deck2.get(1)).suit() + "\n");
					System.out.println("Player 1 played " + (deck1.get(2)).number() + " of " + (deck1.get(2)).suit());
					System.out.println("Player 2 played " + (deck2.get(2)).number() + " of " + (deck2.get(2)).suit() + "\n");
					
					if((deck1.get(1)).number() + (deck1.get(2)).number() == (deck2.get(1)).number() + (deck2.get(2)).number()) coinFlip = (int)(Math.random()*2);
					
					if ((deck1.get(1)).number() + (deck1.get(2)).number() > (deck2.get(1)).number() + (deck2.get(2)).number() || coinFlip == 0) {
						
						System.out.println("Player 1 wins! \n");
						
						deck1.add(deck2.get(0));
						deck1.add(deck2.get(1));
						deck1.add(deck2.get(2));
						deck1.add(deck1.get(0));
						deck1.add(deck1.get(1));
						deck1.add(deck1.get(2));
						
					} else {
						
						System.out.println("Player 2 wins! \n");
						
						deck2.add(deck2.get(0));
						deck2.add(deck2.get(1));
						deck2.add(deck2.get(2));
						deck2.add(deck1.get(0));
						deck2.add(deck1.get(1));
						deck2.add(deck1.get(2));
												
					}
					
					deck1.remove(deck1.get(0));
					deck1.remove(deck1.get(1));
					deck1.remove(deck1.get(2));
					deck2.remove(deck2.get(0));
					deck2.remove(deck2.get(1));
					deck2.remove(deck2.get(2));	
					
				}
				
				if (deck1.size() == 0) warning1 = true;		
				else if (deck2.size() == 0) warning2 = true;
			}
			
		}
	}
	
	public static void main(String[] args) {
		
		War<Card> deck = new War<Card>();
	
		for(int i = 1; i < 14; i++) {
			deck.add(new Card(i, "Spades"));
		}
		
		for(int i = 1; i < 14; i++) {
			deck.add(new Card(i, "Hearts"));
		}
		
		for(int i = 1; i < 14; i++) {
			deck.add(new Card(i, "Diamonds"));
		}
		
		for(int i = 1; i < 14; i++) {
			deck.add(new Card(i, "Clubs"));
		}

		deck = deck.shuffle(deck);
		
		LinkedList<Card> deck1 = new LinkedList<Card>();
		LinkedList<Card> deck2 = new LinkedList<Card>();
		
		for(int i = 0; i < 26; i++) {
			deck1.add(deck.get(i));
			deck2.add(deck.get(i+26));
		}
		
		deck.play(deck);
		
	}



}
