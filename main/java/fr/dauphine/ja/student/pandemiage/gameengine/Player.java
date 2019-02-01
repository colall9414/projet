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
		this.currentCity = "Atlanta";//city initial => born in Atlanta
		this.cardOnHand =  new ArrayList<>();
	}
	//for the begin of each tour 
	public void begin() {
		action=4;
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
		action--;
	}

	@Override
	public void discoverCure(List<PlayerCardInterface> cardNames) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		if(cardNames.size()!=5) {
			//if there aren't 5 cards
			throw new UnauthorizedActionException();
		}
		Disease disease =  cardNames.get(0).getDisease();
		for(PlayerCardInterface pci : cardNames) {
			if(pci.getDisease()!=disease) {
				throw new UnauthorizedActionException(); //these 5 cards not same disease
			}
		}
		//if success
		//remove these cards and get cure
		for(PlayerCardInterface c : cardNames){
			Discard(c);
		}
		g.discoverCure(disease);
		action--;
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
	
	public int getAction() {
		return this.action;
	}
	public int getNbPlayerCardsLeft() {
		return cardOnHand.size();
	}
	//remove player's hand card by giving card
	public void Discard(PlayerCardInterface pci) {
		System.out.println("will discard: "+pci);
		/*for(Card c : cardOnHand){
			if(c.getCityName().equals(pci.getCityName())) {
				this.cardOnHand.remove(c);
			}
		}*/
		for(int i=0;i<cardOnHand.size()-1;i++) {
			if(cardOnHand.get(i).getCityName().equals(pci.getCityName())) {
				this.cardOnHand.remove(i);
			}
		}
	}
	public void Discard(List<PlayerCardInterface> ListPC) {
		for(PlayerCardInterface c: ListPC) {
			Discard(c);
		}
	}
	public void showPlayerHands() {
		for(Card c: this.cardOnHand) {
			System.out.println(c);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String aijar ="./target/pandemiage-1.0-SNAPSHOT-ai.jar";
		String cityGraphFile ="./pandemic.graphml";
		GameEngine ge = new GameEngine(cityGraphFile, aijar);
		Player p = new Player(ge);
		/*City c1 = new City(1,"paris");
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
		p.draw(ca4);*/
		CityLoader cl=null;
		try{
			cl = new CityLoader(cityGraphFile);
		}
		catch (Exception e ){
			e.printStackTrace();
		}
		Cards cards = new Cards(cl);
		try {
			p.moveTo("Chicago");
		} catch (UnauthorizedActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(p.currentCity);
		Card ca1 = cards.drawCityCard();
		Card ca2 = cards.drawCityCard();
		Card ca3 = cards.drawCityCard();
		Card ca4 = cards.drawCityCard();
		p.draw(ca1);
		p.draw(ca2);
		p.draw(ca3);
		p.draw(ca4);
		p.showPlayerHands();
		p.Discard(ca1);
		p.showPlayerHands();
		
		//Johannesburg
		//Bogotá
		
		
		
	}

}