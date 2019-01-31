package fr.dauphine.ja.student.pandemiage.gameengine;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;


public class Card implements PlayerCardInterface{
	private String type; //type: epidemic, city, infection
	private City city;
	//private String cityName;
	private Disease disease;
	//If it is a epidemic card
	public Card() {
		this.type = "epidemic";
	};
	//If it is a city card or a infection card
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