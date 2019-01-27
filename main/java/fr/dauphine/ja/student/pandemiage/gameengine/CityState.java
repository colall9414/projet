package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class CityState {
	
	City city;
	Disease d;
	int yellowInfect;
	int blueInfect;
	int redInfect;
	int blackInfect;

	

	
	public CityState(City city) {
		this.city = city;
		this.d = null;
		this.yellowInfect = 0;
		this.blueInfect = 0;
		this.redInfect = 0;
		this.blackInfect = 0;
		
	}



	public void addInfectionLevel(Disease d, int level) {
		
		if(Disease.BLACK.equals(d)) {
			blackInfect+=level;
		}
		if(Disease.BLUE.equals(d)) {
			blueInfect+=level;
		}
		if(Disease.YELLOW.equals(d)) {
			yellowInfect+=level;
		}
		if(Disease.RED.equals(d)) {
			redInfect+=level;
		}
		
		
	}
	public City getCity() {
		return this.city;
	}
	public int getAffectionLevel(Disease d) {
		if(Disease.BLACK.equals(d)) {
			return blackInfect;
		}
		if(Disease.BLUE.equals(d)) {
			return blueInfect;
		}
		if(Disease.YELLOW.equals(d)) {
			return yellowInfect;
		}
		if(Disease.RED.equals(d)) {
			return redInfect;
		}
		return 0;
	}

	

}
