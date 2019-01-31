package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class CityState {
	
	City city;
	int yellowInfect;
	int blueInfect;
	int redInfect;
	int blackInfect;
	boolean breakout;//Has the city erupted?
	
	

	
	public CityState(City city) {
		this.city = city;
		this.yellowInfect = 0;
		this.blueInfect = 0;
		this.redInfect = 0;
		this.blackInfect = 0;
		this.breakout=false;
	}



	//Return true if this infection caused a virus outbreak, otherwise return false
	public boolean addInfectionLevel(Disease d, int level) {
		//If it has already erupted, skip it directly and do nothing, return false.
		if(this.breakout==true) {
			return false;
		}
		if(Disease.BLACK.equals(d)) {
			if(blackInfect+level>3) {
				blackInfect=3;
				this.breakout=true;
				return true;
			}
			blackInfect+=level;
		}
		if(Disease.BLUE.equals(d)) {
			if(blueInfect+level>3) {
				blueInfect=3;
				this.breakout=true;
				return true;
			}
			blueInfect+=level;
		}
		if(Disease.YELLOW.equals(d)) {
			if(yellowInfect+level>3) {
				yellowInfect=3;
				this.breakout=true;
				return true;
			}
			yellowInfect+=level;
		}
		if(Disease.RED.equals(d)) {
			if(redInfect+level>3) {
				redInfect=3;
				this.breakout=true;
				return true;
			}
			redInfect+=level;
		}
		
		//Return false if there is no outbreak
		return false;
		
		
	}
	@Override
	public String toString() {
		return "CityState [city=" + city  + ", yellowInfect=" + yellowInfect + ", blueInfect=" + blueInfect
				+ ", redInfect=" + redInfect + ", blackInfect=" + blackInfect + ", breakout=" + breakout + "]";
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
	//Change the number of viruses of the corresponding color to 0
	public void cubeToZero(Disease d) {
		if(Disease.BLACK.equals(d)) {
			 blackInfect=0;
		}
		if(Disease.BLUE.equals(d)) {
			 blueInfect=0;
		}
		if(Disease.YELLOW.equals(d)) {
			yellowInfect=0;
		}
		if(Disease.RED.equals(d)) {
			redInfect=0;
		}
	}
	//Subtract a virus of the corresponding color
	public void minusUnCube(Disease d) {
		if(Disease.BLACK.equals(d)) {
			 blackInfect--;
		}
		if(Disease.BLUE.equals(d)) {
			 blueInfect--;
		}
		if(Disease.YELLOW.equals(d)) {
			yellowInfect--;
		}
		if(Disease.RED.equals(d)) {
			redInfect--;
		}
	}

	

}