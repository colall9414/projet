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
	boolean breakout;//该城市是否爆发过
	
	

	
	public CityState(City city) {
		this.city = city;
		this.yellowInfect = 0;
		this.blueInfect = 0;
		this.redInfect = 0;
		this.blackInfect = 0;
		this.breakout=false;
	}



	//如果这次感染造成了病毒爆发，返回true，否则返回false
	public boolean addInfectionLevel(Disease d, int level) {
		//如果已经爆发过，则直接跳过，什么都不用做返回false
		if(this.breakout==true) {
			return false;
		}
		if(Disease.BLACK.equals(d)) {
			if(blackInfect+level>3) {
				blackInfect=3;
				return true;
			}
			blackInfect+=level;
		}
		if(Disease.BLUE.equals(d)) {
			if(blueInfect+level>3) {
				blueInfect=3;
				return true;
			}
			blueInfect+=level;
		}
		if(Disease.YELLOW.equals(d)) {
			if(yellowInfect+level>3) {
				yellowInfect=3;
				return true;
			}
			yellowInfect+=level;
		}
		if(Disease.RED.equals(d)) {
			if(redInfect+level>3) {
				redInfect=3;
				return true;
			}
			redInfect+=level;
		}
		//如果其中一种病毒大于三，则爆发
		/*if(blackInfect>3||blueInfect>3||yellowInfect>3||redInfect>3) {
			this.breakout=true;
			return true;
		}*/
		//如果没有爆发，则返回false
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

	

}
