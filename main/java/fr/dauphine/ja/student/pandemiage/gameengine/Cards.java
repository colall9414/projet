package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Cards {
	private List<Card> cityCardArray; // city card and epidemic card will put together
	private List<Card> infectionArray; //infection card
	//private List<Card> epidemicArray;
	
	
	public Cards(CityLoader cl) {
		
		this.cityCardArray = new ArrayList<Card>();
		try {
			cityCardArray=cl.getCityCards();
			infectionArray=cl.getInfectionCards();
		}
		catch (Exception e ){
			e.printStackTrace();
		}
		//mess up the order
		Collections.shuffle(cityCardArray);
		Collections.shuffle(infectionArray);
		//Create four epidemic cards
		for(int i=0;i<4;i++) {
			//Divide the city card into 4 equal parts; put a epidemic card for each stack (the first five are city cards)
			Card c = new Card();
			int min=i*11;
			int max=(i+1)*11;

			//Add on a fixed quarter
			//cityCardArray.add(min+5,c);//+5 Because the first five are city cards.
			//Add a position between random min and max
			cityCardArray.add(min+(int)(Math.random() * (max-min+1))+5,c);//+5 because the first 5 cards are city cards
		}
	}
	public Card drawCityCard() {
		//Take a city card (epidemic card is also here)
		int pos=cityCardArray.size()-1;
		if(pos<=0) {
			//there's no more card
			System.out.println("no more card!"+cityCardArray.size());
			return null;
		}
		Card c = cityCardArray.get(cityCardArray.size()-1);
		cityCardArray.remove(cityCardArray.size()-1);
		return c;
	}
	public Card drawInfectionCard() {
		//Draw a infection card
		int pos=infectionArray.size()-1;
		if(pos<=0) {
			//there's no more card
			System.out.println("no more card!"+cityCardArray.size());
			return null;
		}
		Card c = infectionArray.get(infectionArray.size()-1);
		infectionArray.remove(infectionArray.size()-1);
		return c;
	}
	public List<Card> getCityCards(){
		//Get all city cards (epidemic card is also here)
		return cityCardArray;
	}
	public List<Card> getInfectionCards(){
		return infectionArray;
	}
	//if the librery of city card is empty
	public boolean emptyCityCard() {
		if(cityCardArray.size()>1) {
			return false;
		}
		return true;
	}
	public boolean emptyInfectionCard() {
		if(infectionArray.size()>1) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("debut");
			
			String filename = "./pandemic.graphml";
			CityLoader parseur  = new CityLoader(filename);
			
			List<String> allCityNames=parseur.getAllCityNames();

			
			Cards cards = new Cards(parseur);
			int i=0;
			for(Card c : cards.getInfectionCards()){
				System.out.println(i+": "+c);
				i++;
			}
			for(i=0;i<1;i++) {
				Card c = cards.drawInfectionCard();	
				System.out.println("card: "+c);
			}

			
			System.out.println("fin");
		
		}
		catch (Exception e ){
			e.printStackTrace();
		}
	}
}