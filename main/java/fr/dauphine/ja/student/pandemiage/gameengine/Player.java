package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;
import fr.dauphine.ja.pandemiage.common.PlayerInterface;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;

public class Player implements PlayerInterface{
	//private int action;//还剩几次操作机会
	private GameEngine g;
	private String currentCity;//所在城市
	private List<Card> cardOnHand; //手上有的卡
	
	
	public Player(GameEngine g) {
		//action = 3;
		this.g=g;
		this.currentCity = "Atlanta";//出生地在亚特兰大
		this.cardOnHand =  new ArrayList<>();
	}
	//抽牌
	public void draw(Card c) {
		this.cardOnHand.add(c);
	}
	@Override
	public void moveTo(String cityName) throws UnauthorizedActionException {
		//判断他的邻居是不是要去的城市
		/* if(g.neib...
		 * */
		this.currentCity = cityName;
	}

	@Override
	public void flyTo(String cityName) throws UnauthorizedActionException {
		//先判断他的手牌有没有改城市
		/*if...*/
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
		g.treatDisease(d);
	}

	@Override
	public void discoverCure(List<PlayerCardInterface> cardNames) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		
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
		
	}

}
