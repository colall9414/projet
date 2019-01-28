package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.dauphine.ja.pandemiage.common.Disease;

public class CityStates {
	
	List<CityState> statesArray;
	private static GameEngine ge;
	boolean breakOut = false;
	int nbOutBreak=0;
	
	public CityStates(CityLoader cl) {
		statesArray = new ArrayList<>();
		List<City> cities = cl.getCities();
		for(City city : cities){
			CityState cs = new CityState(city);
			statesArray.add(cs);
			//City city, Disease d, int yellowInfect, int blueInfect, int redInfect, int blackInfect
		}
	}
	public CityState getState(String cityName) {
		for(CityState cs : statesArray){
			if(cs.getCity().getName().equals(cityName)) {
				return cs;
			}
		}
		return null;
	}
	public int getInfectionLevel(String cityName,Disease d) {
		for(CityState cs : statesArray){
			if(cs.getCity().getName().equals(cityName)) {
				return cs.getAffectionLevel(d);
			}
		}
		return 0;
	}
	public void addInfectionLevel(String cityName,Disease d, int level) {
		for(CityState cs : statesArray){
			if(cs.getCity().getName().equals(cityName)) {
				// explode or not?
				int nb = getInfectionLevel(cityName,d)+level;
					if(nb<=3) {
						cs.addInfectionLevel(d,level);
					}else {
						//never break out
						if(!breakOut) {
							
							breakOut=true;
							List<String> l = ge.neighbours(cityName);
							for(String s: l) {
								addInfectionLevel(s,d,1);
							
							}
							nbOutBreak++;
						}	
					}
			}
			
		}
	}
	
	//蔓延卡病毒蔓延效果
	public void epidemic(City city,Disease d, int nbOutBreaks) {
		//自己infection+1
		
		int infectRate = ge.infectionRate();
		addInfectionLevel(city.getName(),d, infectRate);
		this.nbOutBreak=nbOutBreaks;
		
		
	}
	
	
	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
		String filename = "./pandemic.graphml";
		String aijar = "./target/pandemiage-1.0-SNAPSHOT-ai.jar";
		CityLoader cl  = new CityLoader(filename);
		CityStates css = new CityStates(cl);
		ge = new GameEngine(filename,aijar);
		css.addInfectionLevel("Chicago", Disease.BLACK,1);
		System.out.println(css.getInfectionLevel("Chicago", Disease.BLACK));
		System.out.println(css.getInfectionLevel("Chicago", Disease.BLUE));
		
	}
    
}