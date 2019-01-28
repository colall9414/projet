package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Cards {
	private List<Card> cityCardArray; // 城市卡和蔓延卡是放在一起
	private List<Card> infectionArray; //病毒卡
	private List<Card> epidemicArray; //蔓延卡
	
	
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
		//打乱顺序
		Collections.shuffle(cityCardArray);
		Collections.shuffle(infectionArray);
		//创建四张蔓延卡
		for(int i=0;i<4;i++) {
			//把城市卡分成4等分; 每一堆放一张蔓延卡(前五张是城市卡)
			Card c = new Card();
			int min=i*10;//每一等分有(48-5)/4=10.5张
			int max=(i+1)*10;
			//添加在随机min和max之间的位置
			cityCardArray.add(min+(int)(Math.random() * (max-min+1))+5,c);//+5是因为前五张是城市卡
		}
	}
	public Card drawCityCard() {
		//抽一张城市卡 (蔓延卡也放在这里)
		Card c = cityCardArray.get(cityCardArray.size()-1);
		cityCardArray.remove(cityCardArray.size()-1);
		return c;
	}
	public Card drawInfectionCard() {
		//抽一张病毒卡
		Card c = infectionArray.get(infectionArray.size()-1);
		infectionArray.remove(infectionArray.size()-1);
		return c;
	}
	public List<Card> getCityCards(){
		//获得所有城市卡(蔓延卡也在这里)
		return cityCardArray;
	}
	public List<Card> getInfectionCards(){
		return infectionArray;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("debut");
			
			String filename = "./pandemic.graphml";
			CityLoader parseur  = new CityLoader(filename);
			
			List<String> allCityNames=parseur.getAllCityNames();

			
			Cards cards = new Cards(parseur);
			int i=0;
			for(Card c : cards.getCityCards()){
				System.out.println(i+": "+c);
				i++;
			}
			
			System.out.println("fin");
		
		}
		catch (Exception e ){
			e.printStackTrace();
		}
	}
}
