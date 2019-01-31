package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;
import fr.dauphine.ja.pandemiage.common.PlayerInterface;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;

public class Player implements PlayerInterface{
	private int action;//actions left
	private GameEngine g;
	private String currentCity;//currentCity
	private List<Card> cardOnHand; //Card on hand
	
	public Player(GameEngine g) {
		action = 4;
		this.g=g;
		this.currentCity = "Atlanta";//city initial
		this.cardOnHand =  new ArrayList<>();
	}
	//draw the card
	public void draw(Card c) {
		this.cardOnHand.add(c);
	}
	@Override
	public void moveTo(String cityName) throws UnauthorizedActionException {
		Iterator<String> iter = g.neighbours(currentCity).iterator();
		
		while(iter.hasNext()){
		    if(iter.next().equals(cityName)) {
		    	this.currentCity = cityName;
		    	
		    }
		}
		if(!this.currentCity .equals(cityName)) {
			throw new UnauthorizedActionException();
		}
		
		action--;
	}

	@Override
	public void flyTo(String cityName) throws UnauthorizedActionException {
	
		// TODO Auto-generated method stub
		
		for(PlayerCardInterface c : cardOnHand){
			if(c.getCityName().equals(cityName)) {
				this.currentCity = cityName;
				this.cardOnHand.remove(c);
			}
		}
		if(!this.currentCity .equals(cityName)) {
			throw new UnauthorizedActionException();
		}
		action--;
	}

	@Override
	public void flyToCharter(String cityName) throws UnauthorizedActionException {
		for(PlayerCardInterface c : cardOnHand){
			if(c.getCityName().equals(this.currentCity)) {
				
				this.currentCity = cityName;
				this.cardOnHand.remove(c);
			}
		}
		if(!this.currentCity .equals(cityName)) {
			throw new UnauthorizedActionException();
		}
		action--;
		
	}

	@Override
	public void skipTurn() {
		action = 0;
		//do nothing?
	}

	@Override
	public void treatDisease(Disease d) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		g.treatDisease(d);
	}

	@Override
	public void discoverCure(List<PlayerCardInterface> cardNames) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String aijar ="./target/pandemiage-1.0-SNAPSHOT-ai.jar";
		String cityGraphFile ="./pandemic.graphml";
		GameEngine ge = new GameEngine(cityGraphFile, aijar);
		Player p = new Player(ge);
		City c1 = new City(1,"paris");
		City c2 = new City(2,"beijing");
		City c3 = new City(3,"shanghai");
		City c4 = new City(4,"guilin");
		Card ca1 = new Card("city",c1,Disease.BLACK );
		Card ca2 = new Card("city",c2,Disease.BLUE);
		Card ca3 = new Card("infection",c3,Disease.BLUE);
		Card ca4 = new Card("infection",c4,Disease.RED);
		p.draw(ca1);
		p.draw(ca2);
		p.draw(ca3);
		p.draw(ca4);
		try {
			p.moveTo("Chicago");
		} catch (UnauthorizedActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}