package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;
import fr.dauphine.ja.pandemiage.common.PlayerInterface;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;

public class Player implements PlayerInterface{
	//private int action;//还剩几次操作机会
	private String currentCity;//所在城市
	private List<Card> cardOnHand; //手上有的卡
	private CityStates cityStates;
	private DiseaseStates dss;
	
	public Player() throws SAXException, ParserConfigurationException, IOException {
		//action = 3;
		this.currentCity = "Atlanta";//出生地在亚特兰大
		this.cardOnHand =  new ArrayList<>();
		
		String filename = "./pandemic.graphml";
		CityLoader cl  = new CityLoader(filename);
	    cityStates = new CityStates(cl);
	    dss = new DiseaseStates();
	}
	//抽牌
	public void draw(Card c) {
		this.cardOnHand.add(c);
	}
	@Override
	//在外部判断是否能执行该函数
	public void moveTo(String cityName) throws UnauthorizedActionException {
		this.currentCity = cityName;
	}

	@Override
	public void flyTo(String cityName) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		this.currentCity = cityName;
		for(PlayerCardInterface c : cardOnHand){
			if(c.getCityName().equals(cityName)) {
				this.cardOnHand.remove(c);
			}
		}
		//action--;
	}

	@Override
	public void flyToCharter(String cityName) throws UnauthorizedActionException {
		for(PlayerCardInterface c : cardOnHand){
			if(c.getCityName().equals(this.currentCity)) {
				this.cardOnHand.remove(c);
			}
		}
		this.currentCity = cityName;
		//action--;
		
	}

	@Override
	public void skipTurn() {
		//action = 0;
		//do nothing?
	}

	@Override
	public void treatDisease(Disease d) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		for(DiseaseState ds: dss.ldise) {
			if(ds.getDisease().equals(d)) {
				if(ds.isCured) {
					for(CityState c: cityStates.statesArray) {
						if((c.getCity().getName()).equals(currentCity)) {
							c.cubeMAZ(d);
						}
					}
				}else {
					for(CityState c: cityStates.statesArray) {
						if((c.getCity().getName()).equals(currentCity)) {
							c.minusUnCube(d);
						}
					}
				}
			}
		}
		
	}

	@Override
	public void discoverCure(List<PlayerCardInterface> cardNames) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		//DiseaseState ds = new DiseaseState()
		boolean flag;
		for (int i = 0; i < cardNames.size() - 1; i++){
			for (int j = i + 1; j < cardNames.size(); j++){
				
				if(!cardNames.get(i).getDisease().equals(cardNames.get(j).getDisease())) {
					flag =false;
				}	
			}	
		}
		flag = true;
		if(flag) {
			Disease d = cardNames.get(0).getDisease();
			DiseaseState ds = new DiseaseState(d);
			ds.cured(d);
			
		}
	}

	@Override
	public String playerLocation() {
		// TODO Auto-generated method stub
		return this.currentCity;
	}

	@Override
	public List<PlayerCardInterface> playerHand() {
		// TODO Auto-generated method stub
		List<PlayerCardInterface> cardOnHand = new ArrayList<PlayerCardInterface>();
		for(Card c : this.cardOnHand){
			cardOnHand.add(c);
		}
		return cardOnHand;
	}
	
	/*public int getAction() {
		return this.action;
	}*/
	public int getNbPlayerCardsLeft() {
		return cardOnHand.size();
	}
	
	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
		// TODO Auto-generated method stub
		String filename = "./pandemic.graphml";
		CityLoader cl  = new CityLoader(filename);
		CityStates css = new CityStates(cl);
		
	}

}