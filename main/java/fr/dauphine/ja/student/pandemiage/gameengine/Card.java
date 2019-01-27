package fr.dauphine.ja.student.pandemiage.gameengine;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;

public class Card implements PlayerCardInterface{
	private String type; //蔓延卡，城市卡，病毒卡: epidemic, city, infection
	private int cityId;
	private String cityName;
	private Disease disease;
	//如果是蔓延卡 
	public Card() {
		this.type = "extend";
	};
	//如果是城市卡或者病毒卡
	public Card(String type, int cityId, String cityName, Disease disease) {
		this.type = type;
		this.cityId = cityId;
		this.cityName = cityName;
		this.disease = disease;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCityName() {
		return this.cityName;
	}
	@Override
	public String toString() {
		return "Card [type=" + type + ", cityId=" + cityId + ", cityName=" + cityName + ", disease=" + disease + "]";
	}
	@Override
	public Disease getDisease() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
