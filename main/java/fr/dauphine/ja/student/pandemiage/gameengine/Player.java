package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;
import fr.dauphine.ja.pandemiage.common.PlayerInterface;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;

public class Player implements PlayerInterface{
	private int action;//还剩几次操作机会
	private String currentCity;//所在城市
	private List<Card> cardOnHand; //手上有的卡
	
	
	public Player() {
		action = 3;
		this.currentCity = "Atlanta";//出生地在亚特兰大
		this.cardOnHand =  new ArrayList<>();
	}
	//抽牌
	public void draw(Card c) {
		//如果疾病已经根除需要弃卡
		
		
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
		action--;
	}

	@Override
	public void flyToCharter(String cityName) throws UnauthorizedActionException {
		for(PlayerCardInterface c : cardOnHand){
			if(c.getCityName().equals(this.currentCity)) {
				this.cardOnHand.remove(c);
			}
		}
		this.currentCity = cityName;
		action--;
		
	}

	@Override
	public void skipTurn() {
		action = 0;
	}

	@Override
	public void treatDisease(Disease d) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		
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
	
	public int getAction() {
		return this.action;
	}
	public int getNbPlayerCardsLeft() {
		return cardOnHand.size();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
