
public class QA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Where are you from?");
		sleep(500);
		System.out.println("Westford, MA");
		sleep(1000);
		
		System.out.println("Do you have siblings?");
		sleep(500);
		System.out.println("No, I am an only child.");
		sleep(1000);
		
		System.out.println("Do you have any pets");
		sleep(500);
		System.out.println("No, but I want one."
				+ "Maybe a dog or a cat, but anything, really.");
		sleep(1000);
		
		System.out.println("What do you like to do?");
		sleep(500);
		System.out.println("I run and fence. I also do competition math."
				+ "I enjoy reading, talking to friends, and watching YouTube."
				+ "I also like arts and crafts such as origami and quilling.");
		sleep(1000);
		
		System.out.println("Favorite food?");
		sleep(500);
		System.out.println("I like gummy candies and sweet things."
				+ "I also like ravioli and Italian food.");
		sleep(1000);
		
		System.out.println("Favorite part of school?");
		sleep(500);
		System.out.println("I like the bookstore and the lion's den because they both have food."
				+ "I also like hanging out with friends in the library and studying.");
		sleep(1000);
		
		System.out.println("Favorite color?");
		sleep(500);
		System.out.println("Pink.");
		sleep(200);
		System.out.println("Yes, that's it...");
		
		
	}
	
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
