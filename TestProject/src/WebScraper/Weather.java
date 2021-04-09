package WebScraper;

import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Weather {
	
	
	
	//CONSTRUCTOR
	public Weather() {
		
		System.out.println("input city: ");
		
		Scanner s = new Scanner(System.in);
		String city = s.nextLine();
		
		getInfo(city);
		
	}
	
	public void getInfo(String city) {
		
			
			
			try {
				Document doc = Jsoup.connect("https://www.bing.com/search?q=" + city + "+weather").get();	
				
				Element dayTime = doc.select(".wtr_dayTime").first();
				Element temp = doc.select(".wtr_currTemp.b_focusTextLarge").first();
				Element perci = doc.select(".wtr_currPerci").first();
				Element humi = doc.select(".wtr_currHumi").first();
				Element wind = doc.select(".wtr_currWind").first();
				
				System.out.println("Location:" + city);
				System.out.println("Time:" + dayTime.text());
				System.out.println("Temp:" + temp.text());
				System.out.println("Precipication" + perci.text());
				System.out.println("Humidity" + humi.text());
				System.out.println("Wind" + wind.text());
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
	}
	
	public static void main(String[] args) throws IOException {
		
		new Weather();
	

	}
}