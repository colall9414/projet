package fr.dauphine.ja.student.pandemiage.gameengine;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;


public class Card implements PlayerCardInterface{
	private String type; //蔓延卡，城市卡，病毒卡: epidemic, city, infection
	private City city;
	//private String cityName;
	private Disease disease;
	//如果是蔓延卡 
	public Card() {
		this.type = "epidemic";
	};
	//如果是城市卡或者病毒卡
	public Card(String type, City city, Disease disease) {
		this.type = type;
		this.city=city;
		this.disease = disease;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCityName() {
		return city.getName();
	}
	public City getCity() {
		return city;
	}
	public String getType() {
		return this.type;
	}
	@Override
	public String toString() {
		if(this.type.equals("epidemic")) {
			return "Card [type=" + type + "]";
		}
		return "Card [type=" + type + ", cityId=" + city.getId() + ", cityName=" + city.getName() + ", disease=" + disease + "]";
	}
	@Override
	public Disease getDisease() {
		// TODO Auto-generated method stub
		return disease;
	}
	
}
