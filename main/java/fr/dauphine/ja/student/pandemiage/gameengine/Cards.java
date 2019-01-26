package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Cards {
	private List<Card> cityCardArray; // 城市卡和蔓延卡是放在一起
	private List<Card> infectionArray; //病毒卡
	
	
	public Cards(CityLoader cl) {
		//蔓延卡7张
		//病毒卡和城市卡一样多
		this.cityCardArray = new ArrayList<Card>();
		try {
			cityCardArray=cl.getCityCards();
			infectionArray=cl.getInfectionCards();
		}
		catch (Exception e ){
			e.printStackTrace();
		}
	}
	public List<Card> getCityCards(){
		return cityCardArray;
	}
	public List<Card> getInfectionCards(){
		return infectionArray;
	}
	//把城市卡分成4等分; 每一堆放一张蔓延卡, 
	
	public static void main(String[] args) {
		try {
			System.out.println("debut");
			
			String filename = "./pandemic.graphml";
			CityLoader parseur  = new CityLoader(filename);
			
			List<String> allCityNames=parseur.getAllCityNames();

			
			Cards cards = new Cards(parseur);
			for(Card c : cards.getInfectionCards()){
				System.out.println(c);
			}
			
			System.out.println("fin");
		
		}
		catch (Exception e ){
			e.printStackTrace();
		}
	}
}
